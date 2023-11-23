package com.example.lineupmanager.player.repository;

import com.example.lineupmanager.player.domain.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    List<Player> findAll();

    Player findByShortname(String shortname);

    List<Player> findAllByTeam(String team);
}
