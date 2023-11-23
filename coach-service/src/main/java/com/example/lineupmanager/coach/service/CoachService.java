package com.example.lineupmanager.coach.service;

import com.example.lineupmanager.coach.repository.CoachRepository;
import com.example.lineupmanager.coach.domain.Coach;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class CoachService {

    private final CoachRepository coachRepository;

    public List<CoachDTO> findAll(){
        List<Coach> coach = StreamSupport.stream(coachRepository.findAll().spliterator(), false).toList();
        if(coach.isEmpty()) throw new UnsupportedOperationException();
        List<CoachDTO> cdto = new ArrayList<>();
        for (Coach c : coach){
            cdto.add(CoachDTO.fromEntity(c));
        }
        return cdto.stream().toList();
    }

    public CoachDTO findByShortname(String shortname){
        if(shortname == null || coachRepository.findByShortname(shortname) == null ) throw new UnsupportedOperationException();
        return CoachDTO.fromEntity(coachRepository.findByShortname(shortname));
    }

    public Optional<CoachDTO> insert(CoachDTO coach) {
        if(coachRepository.findByShortname(coach.toEntity().getShortname()) == null){
            Coach c = Coach.builder()
                    .shortname(coach.toEntity().getShortname())
                    .surname(coach.toEntity().getSurname())
                    .lastname(coach.toEntity().getLastname())
                    .team(coach.toEntity().getTeam())
                    .build();
            coachRepository.save(c);
            return Optional.of(CoachDTO.fromEntity(c));
        }
        else{
            return Optional.empty();
        }
    }

    public Optional<CoachDTO> update(String shortname, CoachDTO coach){
        if(coachRepository.findByShortname(coach.toEntity().getShortname()) != null){
            Coach c = coachRepository.findByShortname(shortname);
            Coach c1 = coach.toEntity();
            c.setShortname(c1.getShortname());
            c.setSurname(c1.getSurname());
            c.setLastname(c1.getLastname());
            c.setTeam(c1.getTeam());
            return Optional.ofNullable(c)
                    .map(entity -> coachRepository.save(entity))
                    .map(entity -> CoachDTO.fromEntity(entity));
        }
        else{
            return Optional.empty();
        }
    }
}
