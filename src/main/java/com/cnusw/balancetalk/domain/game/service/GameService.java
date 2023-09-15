package com.cnusw.balancetalk.domain.game.service;


import com.cnusw.balancetalk.domain.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=false)
public class GameService {
    private final GameRepository gameRepository;
    

}
