//package com.example.lineupmanager.restapi;
//
//import com.example.lineupmanager.coach.domain.Coach;
//import com.example.lineupmanager.domain.*;
//import com.example.lineupmanager.coach.repository.CoachRepository;
//import com.example.lineupmanager.player.domain.Player;
//import com.example.lineupmanager.player.domain.Position;
//import com.example.lineupmanager.player.repository.PlayerRepository;
//import com.example.lineupmanager.coach.service.CoachDTO;
//import com.example.lineupmanager.player.service.PlayerDTO;
//import com.example.lineupmanager.team.domain.League;
//import com.example.lineupmanager.team.domain.Team;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//@Transactional
//class GeneratorControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    PlayerRepository playerRepository;
//
//    @Autowired
//    CoachRepository coachRepository;
//
//    @Test
//    void verifyGenerateLineUp() throws Exception {
//        Team t = Team.builder()
//                .name("FKAustriaWien")
//                .country("Austria")
//                .city("Vienna")
//                .foundingYear(1911)
//                .league(League.builder().name("Ö Bundesliga").country("Austria").maxNumberTeams(12).division(1).build())
//                .build();
//        Coach coach = Coach.builder()
//                .shortname("MW")
//                .surname("Michael")
//                .team(t)
//                .lastname("Wimmer")
//                .build();
//        Player player = Player.builder()
//                .shortname("HT25")
//                .surname("Haris")
//                .lastname("Tabakovic")
//                .number(25)
//                .team(t)
//                .position(Position.builder().fieldPosition("Attacker").detail("ST").build())
//                .build();
//        mockMvc.perform(post("/coach")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(CoachDTO.fromEntity(coach)))
//                ).andDo(print())
//                .andExpect(status().isCreated());
//        mockMvc.perform(post("/player")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(PlayerDTO.fromEntity(player)))
//                ).andDo(print())
//                .andExpect(status().isCreated());
//        mockMvc.perform(get("/generator/FKAustriaWien")).andDo(print()).andExpect(status().isOk());
//    }
//}