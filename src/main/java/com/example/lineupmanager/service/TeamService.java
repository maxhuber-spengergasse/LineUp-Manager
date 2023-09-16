package com.example.lineupmanager.service;

import com.example.lineupmanager.domain.Team;
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
public class TeamService {

    private final TeamRepository teamRepository;

    public List<TeamDTO> findAll(){
        List<Team> team = StreamSupport.stream(teamRepository.findAll().spliterator(), false).toList();
        List<TeamDTO> tdto = new ArrayList<>();
        for (Team t : team){
            tdto.add(TeamDTO.fromEntity(t));
        }
        return tdto.stream().toList();
    }

    public TeamDTO findByName(String name){
        if(name == null) throw new IllegalArgumentException("String must not be null!");
        return TeamDTO.fromEntity(teamRepository.findByName(name));
    }

    public Optional<TeamDTO> insert(TeamDTO team) {
        if(teamRepository.findByName(team.toEntity().getName()) == null){
            return Optional.ofNullable(team)
                    .map(dto -> dto.toEntity())
                    .map(entity -> teamRepository.save(entity))
                    .map(entity -> TeamDTO.fromEntity(entity));
        }
        else {
            return Optional.empty();
        }
    }

    public Optional<TeamDTO> update(String name, TeamDTO team){
       if(teamRepository.findByName(team.toEntity().getName()) != null){
           Team t = teamRepository.findByName(name);
           Team t1 = team.toEntity();

           t.setName(t1.getName());
           t.setCity(t1.getCity());
           t.setCountry(t1.getCountry());
           t.setLeague(t1.getLeague());
           t.setPlayers(t1.getPlayers());
           t.setFoundingYear(t1.getFoundingYear());

           return Optional.ofNullable(t)
                   .map(entity -> teamRepository.save(entity))
                   .map(entity -> TeamDTO.fromEntity(entity));
       }
       else {
           return Optional.empty();
       }
    }
}

