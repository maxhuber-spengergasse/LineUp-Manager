package com.example.lineupmanager.persistance;

import com.example.lineupmanager.player.domain.Player;
import com.example.lineupmanager.player.domain.Position;
import com.example.lineupmanager.player.repository.PlayerRepository;
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
                .team("FAK")
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
                .team("FAK")
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
                .team("FAK")
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
                .team("FAK")
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
                .team("FAK")
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
                .team("FAK")
                .build();
        playerRepository.save(p);
        assertThat(playerRepository.findAll()).isNotEmpty();
        assertThat(playerRepository.findAllByTeam("FAK")).isNotNull();
    }

    @Test
    void verifyFindByTeamNameWithUnknownName(){
        Player p = Player.builder()
                .shortname("CR7")
                .surname("Cristiano")
                .lastname("Ronaldo")
                .number(7)
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .team("FAK")
                .build();
        playerRepository.save(p);
        assertThat(playerRepository.findAll()).isNotEmpty();
        assertThat(playerRepository.findAllByTeam("SK Rapid Wien")).isNotNull();
    }
}