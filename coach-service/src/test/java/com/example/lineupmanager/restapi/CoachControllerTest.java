package com.example.lineupmanager.restapi;

import com.example.lineupmanager.coach.domain.Coach;
import com.example.lineupmanager.coach.repository.CoachRepository;
import com.example.lineupmanager.coach.service.CoachDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import jakarta.servlet.ServletException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.assertj.core.api.Assertions.*;
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
    CoachRepository coachRepository;

    @Test
    void verifyFindAllWithEmptyList() throws Exception {
        assertThatThrownBy(() -> mockMvc.perform(get("/coach")))
                .isInstanceOf(ServletException.class);
    }

    @Test
    void verifyFindAllWithFilledList() throws Exception {
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team("FKA")
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
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team("FKA")
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
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team("FKA")
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
        Coach coach = Coach.builder()
                .surname("Michael")
                .shortname(null)
                .team("FKA")
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
        Coach coach = Coach.builder()
                .surname("Michael")
                .shortname(null)
                .team("FKA")
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
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team("FKA")
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
    void verifyInValidUpdate() throws Exception {
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team("FKA")
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
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team("FKA")
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
                .team("FKA")
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
        Coach coach = Coach.builder()
                .shortname("MW")
                .surname("Michael")
                .team("FKA")
                .lastname("Wimmer")
                .build();
        mockMvc.perform(patch("/coach/MW")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
}