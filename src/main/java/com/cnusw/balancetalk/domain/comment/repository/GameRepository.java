package com.cnusw.balancetalk.domain.comment.repository;

import com.cnusw.balancetalk.domain.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findGameById(Long id);
}
