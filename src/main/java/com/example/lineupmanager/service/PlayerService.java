package com.example.lineupmanager.service;

import com.example.lineupmanager.domain.Player;
import com.example.lineupmanager.domain.Team;
import com.example.lineupmanager.persistance.PlayerRepository;
import com.example.lineupmanager.persistance.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class PlayerService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public List<PlayerDTO> findAll(){
        List<Player> player = StreamSupport.stream(playerRepository.findAll().spliterator(), false).toList();
        List<PlayerDTO> pdto = new ArrayList<>();
        for (Player p : player){
            pdto.add(PlayerDTO.fromEntity(p));
        }
        return pdto.stream().toList();
    }
    public PlayerDTO findByShortname(String shortname){
        if(shortname == null) throw new IllegalArgumentException("String must not be null!");
        return PlayerDTO.fromEntity(playerRepository.findByShortname(shortname));
    }

    public Optional<PlayerDTO> insert(PlayerDTO player) {
        if(playerRepository.findByShortname(player.toEntity().getShortname()) == null){
            Team existingTeam = teamRepository.findByName(player.toEntity().getTeam().getName());
            if(existingTeam == null) {
                existingTeam = player.toEntity().getTeam();
            }
            Player p = Player.builder()
                    .shortname(player.toEntity().getShortname())
                    .surname(player.toEntity().getSurname())
                    .lastname(player.toEntity().getLastname())
                    .position(player.toEntity().getPosition())
                    .number(player.toEntity().getNumber())
                    .team(existingTeam)
                    .build();
            playerRepository.save(p);
            return Optional.ofNullable(PlayerDTO.fromEntity(p));
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<PlayerDTO> update(String shortname, PlayerDTO player){
        if(playerRepository.findByShortname(player.toEntity().getShortname()) != null){
            Player p = playerRepository.findByShortname(shortname);
            Player p1 = player.toEntity();
            Team existingTeam = teamRepository.findByName(p1.getTeam().getName());

            p.setShortname(p1.getShortname());
            p.setSurname(p1.getSurname());
            p.setLastname(p1.getLastname());
            p.setNumber(p1.getNumber());
            p.setPosition(p1.getPosition());
            if(existingTeam != null) {
                p.setTeam(existingTeam);
            }
            else {
                p.setTeam(p1.getTeam());
            }
            return Optional.ofNullable(p)
                    .map(entity -> playerRepository.save(entity))
                    .map(entity -> PlayerDTO.fromEntity(entity));
        }
        else{
            return Optional.empty();
        }
    }
}
