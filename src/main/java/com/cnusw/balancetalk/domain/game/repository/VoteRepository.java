package com.cnusw.balancetalk.domain.game.repository;

import com.cnusw.balancetalk.domain.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

}
