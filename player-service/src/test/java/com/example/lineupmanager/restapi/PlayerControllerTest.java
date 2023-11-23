package com.example.lineupmanager.restapi;

import com.example.lineupmanager.player.domain.Player;
import com.example.lineupmanager.player.domain.Position;
import com.example.lineupmanager.player.repository.PlayerRepository;
import com.example.lineupmanager.player.service.PlayerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PlayerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PlayerRepository playerRepository;

    @Test
    void verifyFindAllWithEmptyList() throws Exception {
        MvcResult mvc = mockMvc.perform(get("/player")).andExpect(status().isOk()).andReturn();
        assertThat(mvc.getResponse().getContentAsString()).isEqualTo("[]");
    }

    @Test
    void verifyFindWithUnknownURL() throws Exception {
        mockMvc.perform(get("/player/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void verifyFindAllWithFilledList() throws Exception {
        Player player = Player.builder()
                .shortname("HT25")
                .surname("Haris")
                .lastname("Tabakovic")
                .number(25)
                .team("FAK")
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .build();
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlayerDTO.fromEntity(player)))
                ).andDo(print())
                .andExpect(status().isCreated());
        MvcResult mvc = mockMvc.perform(get("/player")).andExpect(status().isOk()).andReturn();
        String jsonResponse = mvc.getResponse().getContentAsString();
        String coachName = "";
        try {
            coachName = JsonPath.read(jsonResponse, "$[0].shortname");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(coachName).isEqualTo("HT25");
    }

    @Test
    void verifyFindByName() throws Exception {
        Player player = Player.builder()
                .shortname("HT25")
                .surname("Haris")
                .lastname("Tabakovic")
                .number(25)
                .team("FAK")
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .build();
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlayerDTO.fromEntity(player)))
                ).andDo(print())
                .andExpect(status().isCreated());
        MvcResult mvc = mockMvc.perform(get("/player/HT25")).andExpect(status().isOk()).andReturn();
        String jsonResponse = mvc.getResponse().getContentAsString();
        String coachName = "";
        try {
            coachName = JsonPath.read(jsonResponse, "$.shortname");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(coachName).isEqualTo("HT25");
    }

    @Test
    void verifyInsert() throws Exception {
        Player player = Player.builder()
                        .shortname("HT25")
                        .surname("Haris")
                        .lastname("Tabakovic")
                        .number(25)
                        .team("FAK")
                        .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                        .build();
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlayerDTO.fromEntity(player)))
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void verifyUpdate() throws Exception {
        Player player = Player.builder()
                .shortname("HT25")
                .surname("Haris")
                .lastname("Tabakovic")
                .number(25)
                .team("FAK")
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .build();
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlayerDTO.fromEntity(player)))
                ).andDo(print())
                .andExpect(status().isCreated());
        player.setSurname("Michi");
        mockMvc.perform(patch("/player/HT25")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlayerDTO.fromEntity(player)))
                ).andDo(print())
                .andExpect(status().isOk());
        assertThat(playerRepository.findByShortname("HT25").getSurname()).isEqualTo("Michi");

    }

    @Test
    void verifyInValidInsert() throws Exception {
        Player player = Player.builder()
                .shortname("HT25")
                .surname("Haris")
                .lastname("Tabakovic")
                .number(25)
                .team("FAK")
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .build();
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlayerDTO.fromEntity(player)))
                ).andDo(print())
                .andExpect(status().isCreated());
        Player p = Player.builder()
                .shortname("HT25")
                .surname("Haris")
                .lastname("Tabakovic")
                .number(25)
                .team("FAK")
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .build();
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlayerDTO.fromEntity(p)))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void verifyInValidUpdate() throws Exception {
        Player player = Player.builder()
                .shortname("HT25")
                .surname("Haris")
                .lastname("Tabakovic")
                .number(25)
                .team("FAK")
                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
                .build();
        mockMvc.perform(patch("/player/HT25")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PlayerDTO.fromEntity(player)))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
}