package com.example.lineupmanager.persistance;

import com.example.lineupmanager.domain.Player;
import com.example.lineupmanager.domain.Position;
import com.example.lineupmanager.domain.Team;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PlayerRepositoryTest {

    @Autowired
    PlayerRepository playerRepository;

    @Test
    void verifyInsert(){
        Player p = Player.builder()
                .shortname("CR7")
                .surname("Cristiano")
                .lastname("Ronaldo")
                .number(7)
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").players(null).foundingYear(1911).build())
                .build();
        playerRepository.save(p);
        assertThat(playerRepository.findAll()).isNotEmpty();
    }

    @Test
    void verifyUpdate(){
        Player p = Player.builder()
                .shortname("CR7")
                .surname("Cristiano")
                .lastname("Ronaldo")
                .number(7)
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").players(null).foundingYear(1911).build())
                .build();
        playerRepository.save(p);
        assertThat(playerRepository.findAll()).isNotEmpty();
        p.setLastname("Schmid");
        p.setSurname("Manfred");
        playerRepository.save(p);
        assertThat(playerRepository.findById(p.getId()).get().getLastname()).isEqualTo("Schmid");
    }

    @Test
    void verifyDelete(){
        Player p = Player.builder()
                .shortname("CR7")
                .surname("Cristiano")
                .lastname("Ronaldo")
                .number(7)
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").players(null).foundingYear(1911).build())
                .build();
        playerRepository.save(p);
        assertThat(playerRepository.findAll()).isNotEmpty();
        playerRepository.delete(p);
        assertThat(playerRepository.findAll()).isEmpty();
    }

    @Test
    void verifyFindByShortname(){
        Player p = Player.builder()
                .shortname("CR7")
                .surname("Cristiano")
                .lastname("Ronaldo")
                .number(7)
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").players(null).foundingYear(1911).build())
                .build();
        playerRepository.save(p);
        assertThat(playerRepository.findAll()).isNotEmpty();
        assertThat(playerRepository.findByShortname("CR7")).isNotNull();
    }

    @Test
    void verifyFindByShortnameWithUnknownName(){
        Player p = Player.builder()
                .shortname("CR7")
                .surname("Cristiano")
                .lastname("Ronaldo")
                .number(7)
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").players(null).foundingYear(1911).build())
                .build();
        playerRepository.save(p);
        assertThat(playerRepository.findAll()).isNotEmpty();
        assertThat(playerRepository.findByShortname("CR8")).isNull();
    }

    @Test
    void verifyFindByTeamName(){
        Player p = Player.builder()
                .shortname("CR7")
                .surname("Cristiano")
                .lastname("Ronaldo")
                .number(7)
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").players(null).foundingYear(1911).build())
                .build();
        playerRepository.save(p);
        assertThat(playerRepository.findAll()).isNotEmpty();
        assertThat(playerRepository.findAllByTeamName("FK Austria Wien")).isNotNull();
    }

    @Test
    void verifyFindByTeamNameWithUnknownName(){
        Player p = Player.builder()
                .shortname("CR7")
                .surname("Cristiano")
                .lastname("Ronaldo")
                .number(7)
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").players(null).foundingYear(1911).build())
                .build();
        playerRepository.save(p);
        assertThat(playerRepository.findAll()).isNotEmpty();
        assertThat(playerRepository.findAllByTeamName("SK Rapid Wien")).isNotNull();
    }
}