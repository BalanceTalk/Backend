package com.cnusw.balancetalk.domain.game.repository;

import com.cnusw.balancetalk.domain.game.entity.Game;
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

    Game findGameById(Long id);

    List<Game> findByTitleContaining(String keyword);

    List<Game> findAllByActivationTrue(Sort sort);
}
