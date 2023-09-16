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
class CoachRepositoryTest {

    @Autowired
    CoachRepository coachRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    void verifyInsert(){
        Team t = Team.builder()
                .name("FK Austria Wien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .build();
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team(t)
                .lastname("Wimmer")
                .build();
        coachRepository.save(coach);
        assertThat(coachRepository.findAll()).isNotEmpty();
    }

    @Test
    void verifyUpdate(){
        Coach c = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .lastname("Wimmer")
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").foundingYear(1911).build())
                .build();
        coachRepository.save(c);
        assertThat(coachRepository.findAll()).isNotEmpty();
        c.setLastname("Schmid");
        c.setSurname("Manfred");
        c.getTeam().setCity("VIE");
        coachRepository.save(c);
        assertThat(coachRepository.findByShortname("MW").getSurname()).isEqualTo("Manfred");
        assertThat(coachRepository.findByShortname("MW").getTeam().getCity()).isEqualTo("VIE");
    }

    @Test
    void verifyDelete(){
        Team t = Team.builder()
                .name("FK Austria Wien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .build();
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team(t)
                .lastname("Wimmer")
                .build();
        coachRepository.save(coach);
        assertThat(coachRepository.findAll()).isNotEmpty();
        coachRepository.delete(coach);
        assertThat(coachRepository.findAll()).isEmpty();
    }

    @Test
    void verifyFindByShortname(){
        Coach c = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .lastname("Wimmer")
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").players(null).foundingYear(1911).build())
                .build();
        coachRepository.save(c);
        assertThat(coachRepository.findAll()).isNotEmpty();
        assertThat(coachRepository.findByShortname("MW")).isNotNull();
    }

    @Test
    void verifyFindByShortnameWithUnknownName(){
        Coach c = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .lastname("Wimmer")
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").players(null).foundingYear(1911).build())
                .build();
        coachRepository.save(c);
        assertThat(coachRepository.findAll()).isNotEmpty();
        assertThat(coachRepository.findByShortname("MM")).isNull();
    }

    @Test
    void verifyFindByTeamName(){
        Coach c = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .lastname("Wimmer")
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").players(null).foundingYear(1911).build())
                .build();
        coachRepository.save(c);
        assertThat(coachRepository.findAll()).isNotEmpty();
        assertThat(coachRepository.findByTeamName("FK Austria Wien")).isNotNull();
    }

    @Test
    void verifyFindByTeamNameWithUnknownName(){
        Coach c = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .lastname("Wimmer")
                .team(Team.builder().name("FK Austria Wien").country("Austria").city("Vienna").players(null).foundingYear(1911).build())
                .build();
        coachRepository.save(c);
        assertThat(coachRepository.findAll()).isNotEmpty();
        assertThat(coachRepository.findByTeamName("SK Rapid Wien")).isNull();
    }
}