package com.example.lineupmanager.persistance;

import com.example.lineupmanager.domain.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamRepository extends CrudRepository<Team, Integer> {

    List<Team> findAll();

    Team findByName(String name);
}
