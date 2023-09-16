package com.example.lineupmanager.service;

import com.example.lineupmanager.domain.LineUp;
import com.example.lineupmanager.domain.Player;
import com.example.lineupmanager.persistance.CoachRepository;
import com.example.lineupmanager.persistance.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class GeneratorService {

    private final CoachRepository coachRepository;
    private final PlayerRepository playerRepository;

    public Optional<LineUp> generateLineUp(String name){
        CoachDTO coach = CoachDTO.fromEntity(coachRepository.findByTeamName(name));
        List<PlayerDTO> players = new ArrayList<>();
        if(playerRepository.findAllByTeamName(name) != null){
            for(Player p : playerRepository.findAllByTeamName(name)){
                players.add(PlayerDTO.fromEntity(p));
            }
        }
        else{
            throw new IllegalArgumentException("There are no players from: " + name);
        }
        if(coach != null){
            return Optional.of(LineUp.builder()
                .coach(coach)
                .players(players)
                .build());
        }
        else{
            throw new IllegalArgumentException("There is no coach from: " + name);
        }
    }
}
