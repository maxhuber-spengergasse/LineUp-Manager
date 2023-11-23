package com.example.lineupmanager.persistance;

import com.example.lineupmanager.coach.domain.Coach;
import com.example.lineupmanager.coach.repository.CoachRepository;
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

    @Test
    void verifyInsert(){
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team("FKA")
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
                .team("FKA")
                .build();
        coachRepository.save(c);
        assertThat(coachRepository.findAll()).isNotEmpty();
        c.setLastname("Schmid");
        c.setSurname("Manfred");
        coachRepository.save(c);
        assertThat(coachRepository.findByShortname("MW").getSurname()).isEqualTo("Manfred");
    }

    @Test
    void verifyDelete(){
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team("FKA")
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
                .team("FKA")
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
                .team("FKA")
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
                .team("FKA")
                .build();
        coachRepository.save(c);
        assertThat(coachRepository.findAll()).isNotEmpty();
        assertThat(coachRepository.findByTeam("FKA")).isNotNull();
    }

    @Test
    void verifyFindByTeamNameWithUnknownName(){
        Coach c = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .lastname("Wimmer")
                .team("FKA")
                .build();
        coachRepository.save(c);
        assertThat(coachRepository.findAll()).isNotEmpty();
        assertThat(coachRepository.findByTeam("SK Rapid Wien")).isNull();
    }
}