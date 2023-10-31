package com.cnusw.balancetalk.domain.game.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cnusw.balancetalk.domain.game.controller.request.GameRequest;
import com.cnusw.balancetalk.domain.game.controller.response.CategoryGamesResponse;
import com.cnusw.balancetalk.domain.game.controller.response.GameResponse;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.entity.GameLikes;
import com.cnusw.balancetalk.domain.game.enums.Category;
import com.cnusw.balancetalk.domain.game.repository.GameLikesRepository;
import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import com.cnusw.balancetalk.domain.member.entity.Member;
import com.cnusw.balancetalk.domain.member.repository.MemberRepository;
import com.cnusw.balancetalk.domain.member.service.MemberService;
import com.cnusw.balancetalk.domain.option.entity.Option;
import com.cnusw.balancetalk.domain.option.repository.OptionRepository;
import com.cnusw.balancetalk.global.jwt.JwtUtil;
import com.cnusw.balancetalk.domain.vote.repository.VoteRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {

    private static final String TOKEN_PREFIX = "Bearer ";

    private final GameRepository gameRepository;
    private final GameLikesRepository gameLikesRepository;
    private final OptionRepository optionRepository;
    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    private final MemberService memberService;
    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    //게임 제작
    public Long createGame(GameRequest gameRequest, HttpServletRequest servletRequest) {
        String bearerToken = servletRequest.getHeader("Authorization");

        String token = "";
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            token = bearerToken.substring(TOKEN_PREFIX.length());
        }

        String email = jwtUtil.extractSubject(token);
        Member member = memberRepository.findByEmail(email).orElseThrow();

        Option option1 = Option.builder()
                .title(gameRequest.getOptionTitle1())
                .description(gameRequest.getOptionDescription1())
                .imgUrl(gameRequest.getOptionImgUrl1())
                .build();

        Option option2 = Option.builder()
                .title(gameRequest.getOptionTitle2())
                .description(gameRequest.getOptionDescription2())
                .imgUrl(gameRequest.getOptionImgUrl2())
                .build();

        List<Option> options = new ArrayList<>();
        options.add(option1);
        options.add(option2);

        Game game = Game.builder()
                .title(gameRequest.getTitle())
                .deadline(gameRequest.getDeadline())
                .options(options)
                .member(member)
                .build();

        gameRepository.save(game);

        options.forEach(option -> option.addGame(game));

        optionRepository.save(option1);
        optionRepository.save(option2);

        // Increase 50 EXP for gameCreator
        boolean levelUpdated = memberService.updateMemberLevel(member.getId(), 50);
        if(levelUpdated) {
            // If level increased, player can earn 100 credit
            member.setCredit(member.getCredit() + 100);
            log.info("Member {} leveled up to level {}", member.getId(), member.getLevel());
            log.info("Member {} earned credit {}", member.getId(), 100);
        }
        return game.getId();
    }

    public GameResponse findById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow();
        return GameResponse.from(game);
    }

    // 인기순으로 게임 정렬 리스트 반환
    public List<GameResponse> getGamesSortedByPopularity() {
        Sort sort = Sort.by(Sort.Order.desc("likes"));
        List<Game> games = gameRepository.findAllByActivationTrue(sort);
        return convertToGameResponseList(games);
    }

    // 조회수순으로 게임 정렬 리스트 반환
    public List<GameResponse> getGamesSortedByViews() {
        Sort sort = Sort.by(Sort.Order.desc("playerCount"));
        List<Game> games = gameRepository.findAllByActivationTrue(sort);
        return convertToGameResponseList(games);
    }

    // 최신순으로 게임 정렬 리스트 반환
    public List<GameResponse> getGamesSortedByLatest() {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        List<Game> games = gameRepository.findAllByActivationTrue(sort);
        return convertToGameResponseList(games);
    }

    public void updateGoldBalance(Game game) {
        List<Option> options = game.getOptions();
        if (options.size() == 2) {

            Option option1 = options.get(0);
            Option option2 = options.get(1);

            int firstOptionVoteCount = voteRepository.findVotesByOption(option1.getId()).size();
            int secondOptionVoteCount = voteRepository.findVotesByOption(option2.getId()).size();

            double percentage = ((double) firstOptionVoteCount / (firstOptionVoteCount + secondOptionVoteCount)) * 100.0;

            game.setGoldBalance(Math.abs(percentage - 50.0) < 5.0);
        }
    }

    // getmapping('/')
    // 메인페이지.
    // 여러 카테고리의 게임리스트를 하나의 리스트로 전달.
    public List<CategoryGamesResponse> getCategoryGamesList() {
        List<CategoryGamesResponse> categoryGamesResponses = new ArrayList<>();
        categoryGamesResponses.add(getCategoryGamesOfGoldenBalance());
        categoryGamesResponses.add(getCategoryGamesOfPopularity());
        categoryGamesResponses.add(getCategoryGamesOfViews());
        categoryGamesResponses.add(getCategoryGamesOfLatest());
        return categoryGamesResponses;
    }

    // 황금밸런스 카테고리
    // GameResponse 리스트와 카테고리를 CategoryGamesResponse Dto로 변환 후 반환
    private CategoryGamesResponse getCategoryGamesOfGoldenBalance() {
        List<Game> games = gameRepository.findAll();
        List<GameResponse> allGameResponses = convertToGameResponseList(games);
        List<GameResponse> goldenBalanceGameResponses = new ArrayList<>();

        for (GameResponse gameResponse : allGameResponses) {
            Option option1 = optionRepository.findOptionById(gameResponse.getOptionId1());
            Option option2 = optionRepository.findOptionById(gameResponse.getOptionId2());
            int firstOptionVoteCount = voteRepository.findVotesByOption(option1.getId()).size();
            int secondOptionVoteCount = voteRepository.findVotesByOption(option2.getId()).size();
            if (firstOptionVoteCount == 0 && secondOptionVoteCount == 0) {
                return null;
            }
            if (gameResponse.isGoldBalance()) { goldenBalanceGameResponses.add(gameResponse); }
        }

        return CategoryGamesResponse.from(goldenBalanceGameResponses, Category.GOLDENBALANCE);
    }

    // 인기순 카테고리
    // GameResponse 리스트와 카테고리를 CategoryGamesResponse Dto로 변환 후 반환
    private CategoryGamesResponse getCategoryGamesOfPopularity() {
        List<GameResponse> gameResponses = getGamesSortedByPopularity();
        return CategoryGamesResponse.from(gameResponses, Category.POPULARITY);
    }

    // 조회수순 카테고리
    // GameResponse 리스트와 카테고리를 CategoryGamesResponse Dto로 변환 후 반환
    private CategoryGamesResponse getCategoryGamesOfViews() {
        List<GameResponse> gameResponses = getGamesSortedByViews();
        return CategoryGamesResponse.from(gameResponses, Category.VIEWS);
    }

    // 최신순 카테고리
    // GameResponse 리스트와 카테고리를 CategoryGamesResponse Dto로 변환 후 반환
    private CategoryGamesResponse getCategoryGamesOfLatest() {
        List<GameResponse> gameResponses = getGamesSortedByViews();
        return CategoryGamesResponse.from(gameResponses, Category.LATEST);
    }

    // Game 엔티티를 GameResponse로 변환하여 리스트로 반환
    private List<GameResponse> convertToGameResponseList(List<Game> games) {
        List<GameResponse> gameResponses = new ArrayList<>();
        for (Game game : games) {
            gameResponses.add(GameResponse.from(game));
        }
        return gameResponses;
    }

    public void reportGame(Long id) {
        Game game = gameRepository.findById(id).orElseThrow();
        long currentReports = game.getReports();
        game.setReports(currentReports + 1);
        if (currentReports + 1 >= 5) {
            game.setActivation(false);
        }
    }

    public void likeGame(Long gameId, HttpServletRequest request) {
        String memberEmail = extractEmailFromToken(extractToken(request));
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow();

        Game game = gameRepository.findById(gameId)
                .orElseThrow();

        Optional<GameLikes> byMember = gameLikesRepository.findByMemberAndGame(member, game);
        if (byMember.isPresent()) {
            return;
        }

        GameLikes gameLikes = GameLikes.builder()
                .member(member)
                .game(game)
                .build();
        gameLikesRepository.save(gameLikes);
    }

    public long getLikesCount(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow()
                .getLikes()
                .size();
    }

    // 요청 헤더에서 토큰 추출
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }

        return null;
    }

    // 토큰에서 이메일 정보 추출
    private String extractEmailFromToken(String token) {
        return jwtUtil.extractSubject(token);
    }
}

