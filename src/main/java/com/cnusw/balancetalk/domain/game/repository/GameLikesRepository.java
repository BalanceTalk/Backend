package com.cnusw.balancetalk.domain.game.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cnusw.balancetalk.domain.comment.entity.Comment;
import com.cnusw.balancetalk.domain.comment.entity.CommentLikes;
import com.cnusw.balancetalk.domain.game.entity.Game;
import com.cnusw.balancetalk.domain.game.entity.GameLikes;
import com.cnusw.balancetalk.domain.member.entity.Member;

public interface GameLikesRepository extends JpaRepository<GameLikes, Long> {

    @Query("select gl from GameLikes gl where gl.member = :member and gl.game = :game")
    Optional<GameLikes> findByMemberAndGame(Member member, Game game);
}
