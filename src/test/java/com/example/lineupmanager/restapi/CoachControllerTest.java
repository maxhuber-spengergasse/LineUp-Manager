package com.example.lineupmanager.restapi;

import com.example.lineupmanager.domain.Coach;
import com.example.lineupmanager.domain.League;
import com.example.lineupmanager.domain.Team;
import com.example.lineupmanager.persistance.CoachRepository;
import com.example.lineupmanager.persistance.TeamRepository;
import com.example.lineupmanager.service.CoachDTO;
import com.example.lineupmanager.service.CoachService;
import com.example.lineupmanager.service.TeamDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import jakarta.servlet.ServletException;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class CoachControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CoachRepository coachRepository;

    @Test
    void verifyFindAllWithEmptyList() throws Exception {
        assertThatThrownBy(() -> mockMvc.perform(get("/coach")))
                .isInstanceOf(ServletException.class);
    }

    @Test
    void verifyFindAllWithFilledList() throws Exception {
        Team t = Team.builder()
                .name("FKAustriaWien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .players(null)
                .build();
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team(t)
                .lastname("Wimmer")
                .build();
        mockMvc.perform(post("/coach")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isCreated());
        mockMvc.perform(get("/coach")).andExpect(status().isOk());
        MvcResult mvc = mockMvc.perform(get("/coach")).andExpect(status().isOk()).andReturn();
        String jsonResponse = mvc.getResponse().getContentAsString();
        String coachName = "";
        try {
            coachName = JsonPath.read(jsonResponse, "$[0].shortname");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(coachName).isEqualTo("MW");
    }

    @Test
    void verifyFindWithUnknownURL() throws Exception {
        mockMvc.perform(get("/coach/"))
                .andExpect(status().isNotFound());
    }

    @Test
    void verifyFindByNameWithFilledList() throws Exception {
        Team t = Team.builder()
                .name("FKAustriaWien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .players(null)
                .build();
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team(t)
                .lastname("Wimmer")
                .build();
        mockMvc.perform(post("/coach")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isCreated());
        MvcResult mvc = mockMvc.perform(get("/coach/MW")).andExpect(status().isOk()).andReturn();
        String jsonResponse = mvc.getResponse().getContentAsString();
        String coachName = "";
        try {
            coachName = JsonPath.read(jsonResponse, "$.shortname");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(coachName).isEqualTo("MW");
    }

    @Test
    void verifyInsert() throws Exception {
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
        mockMvc.perform(post("/coach")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void verifyInValidCoachInsert() throws Exception {
        Team t = Team.builder()
                .name("FK Austria Wien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .build();
        Coach coach = Coach.builder()
                .surname("Michael")
                .shortname(null)
                .team(t)
                .lastname("Wimmer")
                .build();
        mockMvc.perform(post("/coach")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void verifyInValidInsert() throws Exception {
        Team t = Team.builder()
                .name("FK Austria Wien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .build();
        Coach coach = Coach.builder()
                .surname("Michael")
                .shortname(null)
                .team(t)
                .lastname("Wimmer")
                .build();
        mockMvc.perform(post("/coach")
                        .contentType(MediaType.APPLICATION_JSON)
                        //.content(c);
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void verifyUpdate() throws Exception {
        Team t = Team.builder()
                .name("FK Austria Wien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .players(null)
                .build();
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team(t)
                .lastname("Wimmer")
                .build();
        mockMvc.perform(post("/coach")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isCreated());
        coach.setSurname("Michi");
        mockMvc.perform(patch("/coach/MW")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isOk());
        assertThat(coachRepository.findByShortname("MW").getSurname()).isEqualTo("Michi");
    }

    @Test
    void verifyUpdateTeam() throws Exception {
        Team t = Team.builder()
                .name("FK Austria Wien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .players(null)
                .build();
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team(t)
                .lastname("Wimmer")
                .build();
        mockMvc.perform(post("/coach")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isCreated());
        coach.setSurname("Michi");
        coach.getTeam().setCity("VIE");
        mockMvc.perform(patch("/coach/MW")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isOk());
        assertThat(coachRepository.findByShortname("MW").getSurname()).isEqualTo("Michi");
        assertThat(coachRepository.findByShortname("MW").getTeam().getCity()).isEqualTo("VIE");
    }

    @Test
    void verifyInValidUpdate() throws Exception {
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
        mockMvc.perform(post("/coach")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isCreated());
        coach.setSurname(null);
        mockMvc.perform(patch("/coach/MW")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void verifyInValidShortNameInsert() throws Exception {
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
        mockMvc.perform(post("/coach")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isCreated());
        Coach c = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team(t)
                .lastname("Wimmer")
                .build();
        mockMvc.perform(post("/coach")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(c)))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void verifyInValidShortNameUpdate() throws Exception {
        Team t = Team.builder()
                .name("FK Austria Wien")
                .country("Austria")
                .city("Vienna")
                .foundingYear(1911)
                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
                .players(null)
                .build();
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team(t)
                .lastname("Wimmer")
                .build();
        mockMvc.perform(patch("/coach/MW")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
}