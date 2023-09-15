package com.cnusw.balancetalk.domain.game.repository;

import com.cnusw.balancetalk.domain.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<List<Game>> findAllByGameId(Long game_id);
}
