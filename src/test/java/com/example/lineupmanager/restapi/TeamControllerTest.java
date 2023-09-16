package com.example.lineupmanager.restapi;

import com.example.lineupmanager.domain.League;
import com.example.lineupmanager.domain.Team;
import com.example.lineupmanager.persistance.TeamRepository;
import com.example.lineupmanager.service.TeamDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class TeamControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TeamRepository teamRepository;

    @Test
    void verifyFindAllWithEmptyList() throws Exception {
        MvcResult mvc = mockMvc.perform(get("/team")).andExpect(status().isOk()).andReturn();
        assertThat(mvc.getResponse().getContentAsString()).isEqualTo("[]");
    }

    @Test
    void verifyFindWithUnknownURL() throws Exception {
        mockMvc.perform(get("/team/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void verifyFindAllWithFilledList() throws Exception {
        Team team = Team.builder()
                .name("FKAustriaWien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .build();
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TeamDTO.fromEntity(team)))
                ).andDo(print())
                .andExpect(status().isCreated());
        MvcResult mvc = mockMvc.perform(get("/team")).andExpect(status().isOk()).andReturn();
        String jsonResponse = mvc.getResponse().getContentAsString();
        String coachName = "";
        try {
            coachName = JsonPath.read(jsonResponse, "$[0].name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(coachName).isEqualTo("FKAustriaWien");
    }

    @Test
    void verifyFindByName() throws Exception {
        Team team = Team.builder()
                .name("FKAustriaWien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .build();
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TeamDTO.fromEntity(team)))
                ).andDo(print())
                .andExpect(status().isCreated());
        MvcResult mvc = mockMvc.perform(get("/team/FKAustriaWien")).andExpect(status().isOk()).andReturn();
        String jsonResponse = mvc.getResponse().getContentAsString();
        String coachName = "";
        try {
            coachName = JsonPath.read(jsonResponse, "$.name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(coachName).isEqualTo("FKAustriaWien");
    }

    @Test
    void verifyInsert() throws Exception {
        Team team = Team.builder()
                .name("FKAustriaWien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .build();
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TeamDTO.fromEntity(team)))
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void verifyUpdate() throws Exception {
        Team team = Team.builder()
                .name("FKAustriaWien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .build();
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TeamDTO.fromEntity(team)))
                ).andDo(print())
                .andExpect(status().isCreated());
        team.setCity("VIE");
        mockMvc.perform(patch("/team/FKAustriaWien")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TeamDTO.fromEntity(team)))
                ).andDo(print())
                .andExpect(status().isOk());
        assertThat(teamRepository.findByName("FKAustriaWien").getCity()).isEqualTo("VIE");

    }

    @Test
    void verifyInValidInsert() throws Exception {
        Team team = Team.builder()
                .name("FKAustriaWien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .build();
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TeamDTO.fromEntity(team)))
                ).andDo(print())
                .andExpect(status().isCreated());
        Team t = Team.builder()
                .name("FKAustriaWien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .build();
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TeamDTO.fromEntity(t)))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void verifyInValidUpdate() throws Exception {
        Team team = Team.builder()
                .name("FKAustriaWien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .build();
        mockMvc.perform(patch("/team/FKAustriaWien")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TeamDTO.fromEntity(team)))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
}