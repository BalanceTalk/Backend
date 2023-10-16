package com.cnusw.balancetalk.domain.vote.repository;

import com.cnusw.balancetalk.domain.option.entity.Option;
import com.cnusw.balancetalk.domain.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(value = "SELECT * FROM Vote b WHERE option_id = :option_id", nativeQuery = true)
    List<Vote> findVotesByOption(Option option);
}
