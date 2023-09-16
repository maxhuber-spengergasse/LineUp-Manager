package com.example.lineupmanager.persistance;

import com.example.lineupmanager.domain.Coach;
import com.example.lineupmanager.domain.League;
import com.example.lineupmanager.domain.Team;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;

    @Test
    void verifyInsert(){
        Team c = Team.builder()
                .name("Manchester City")
                .country("England")
                .city("Manchester")
                .foundingYear(1900)
                .league(League.builder().maxNumberTeams(20).division(1).country("England").name("Premier League").build())
                .coach(Coach.builder().shortname("PG").surname("Pep").lastname("Guardiola").build())
                .build();
        teamRepository.save(c);
        assertThat(teamRepository.findAll()).isNotEmpty();
    }

    @Test
    void verifyUpdate(){
        Team c = Team.builder()
                .name("Manchester City")
                .country("England")
                .city("Manchester")
                .foundingYear(1900)
                .league(League.builder().maxNumberTeams(20).division(1).country("England").name("Premier League").build())
                .coach(Coach.builder().shortname("PG").surname("Pep").lastname("Guardiola").build())
                .build();
        teamRepository.save(c);
        assertThat(teamRepository.findAll()).isNotEmpty();
        c.setName("Manchester United");
        teamRepository.save(c);
        assertThat(teamRepository.findById(c.getId()).get().getName()).isEqualTo("Manchester United");
    }

    @Test
    void verifyDelete(){
        Team c = Team.builder()
                .name("Manchester City")
                .country("England")
                .city("Manchester")
                .foundingYear(1900)
                .league(League.builder().maxNumberTeams(20).division(1).country("England").name("Premier League").build())
                .coach(Coach.builder().shortname("PG").surname("Pep").lastname("Guardiola").build())
                .build();
        teamRepository.save(c);
        assertThat(teamRepository.findAll()).isNotEmpty();
        teamRepository.delete(c);
        assertThat(teamRepository.findAll()).isEmpty();
    }

    @Test
    void verifyFindByName(){
        Team c = Team.builder()
                .name("Manchester City")
                .country("England")
                .city("Manchester")
                .foundingYear(1900)
                .league(League.builder().maxNumberTeams(20).division(1).country("England").name("Premier League").build())
                .coach(Coach.builder().shortname("PG").surname("Pep").lastname("Guardiola").build())
                .build();
        teamRepository.save(c);
        assertThat(teamRepository.findAll()).isNotEmpty();
        assertThat(teamRepository.findByName("Manchester City")).isNotNull();
    }

    @Test
    void verifyFindByNameWithUnknownName(){
        Team c = Team.builder()
                .name("Manchester City")
                .country("England")
                .city("Manchester")
                .foundingYear(1900)
                .league(League.builder().maxNumberTeams(20).division(1).country("England").name("Premier League").build())
                .coach(Coach.builder().shortname("PG").surname("Pep").lastname("Guardiola").build())
                .build();
        teamRepository.save(c);
        assertThat(teamRepository.findAll()).isNotEmpty();
        assertThat(teamRepository.findByName("Manchester United")).isNull();
    }
}