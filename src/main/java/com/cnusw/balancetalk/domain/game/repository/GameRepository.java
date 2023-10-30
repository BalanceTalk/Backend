package com.cnusw.balancetalk.domain.game.repository;

import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    public List<Game> findAllById(Long game_id);

    public Game findGameById(Long id);

    //member_id를 통해 로그인한 회원의 게시물을 모두 받아오는 메소드
    public List<Game> findByMember(Member member);

    List<Game> findAllByActivationTrue(Sort sort);
}
