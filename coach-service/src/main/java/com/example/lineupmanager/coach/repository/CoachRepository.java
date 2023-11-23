package com.example.lineupmanager.coach.repository;

import com.example.lineupmanager.coach.domain.Coach;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CoachRepository extends CrudRepository<Coach, Integer> {

    List<Coach> findAll();

    Coach findByShortname(String shortname);

    Coach findByTeam(String team);
}
