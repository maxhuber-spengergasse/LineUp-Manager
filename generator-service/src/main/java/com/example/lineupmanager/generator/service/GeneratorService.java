package com.example.lineupmanager.generator.service;

import com.example.lineupmanager.coachclient.CoachClient;
import com.example.lineupmanager.generator.domain.LineUp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class GeneratorService {

    private final CoachClient coachClient;

//    public Optional<LineUp> generateLineUp(String name){
//
//        ResponseEntity<CoachDTO> coachDTO = coachClient.findByName(name);
//        return Optional.empty();
//        CoachDTO coach = CoachDTO.fromEntity(coachRepository.findByTeamName(name));
//        List<PlayerDTO> players = new ArrayList<>();
//        if(playerRepository.findAllByTeamName(name) != null){
//            for(Player p : playerRepository.findAllByTeamName(name)){
//                players.add(PlayerDTO.fromEntity(p));
//            }
//        }
//        else{
//            throw new IllegalArgumentException("There are no players from: " + name);
//        }
//        if(coach != null){
//            return Optional.of(LineUp.builder()
//                .coach(coach)
//                .players(players)
//                .build());
//        }
//        else{
//            throw new IllegalArgumentException("There is no coach from: " + name);
//        }
//    }
}
