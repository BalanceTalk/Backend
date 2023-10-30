package com.cnusw.balancetalk.domain.game.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.entity.GameLikes;
import com.cnusw.balancetalk.domain.member.entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLikesRepository extends JpaRepository<GameLikes, Long> {
    @Query("select gl from GameLikes gl where gl.member = :member and gl.game = :game")
    Optional<GameLikes> findByMemberAndGame(Member member, Game game);

    @Query("select gl from GameLikes gl where gl.member = :member")
    List<GameLikes> findAllById(Member member);

}
