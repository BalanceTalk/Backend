package com.cnusw.balancetalk.domain.option.repository;


import com.cnusw.balancetalk.domain.option.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
}