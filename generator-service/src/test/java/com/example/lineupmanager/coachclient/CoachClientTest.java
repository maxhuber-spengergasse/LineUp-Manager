//package com.example.lineupmanager.coachclient;
//
//import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
//import io.github.resilience4j.circuitbreaker.CircuitBreaker;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
//import org.awaitility.Awaitility;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
//import org.springframework.context.annotation.Configuration;
//
//import com.example.lineupmanager.generator.service.CoachDTO;
//import com.github.tomakehurst.wiremock.WireMockServer;
//import com.github.tomakehurst.wiremock.client.WireMock;
//import com.github.tomakehurst.wiremock.http.Body;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
//import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.retry.RetryOperations;
//import static org.mockito.Mockito.verify;
//import org.springframework.retry.support.RetryTemplate;
//
//import java.time.Duration;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.*;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.when;
//
//@Slf4j
//@SpringBootTest
//@AutoConfigureWireMock
//class CoachClientTest {
//
//    @Autowired
//    Environment environment;
//
//    @Autowired
//    CoachClient coachClient;
//
//    @Autowired
//    private CircuitBreakerRegistry circuitBreakerRegistry;
//
//    @Test
//    void verifyCoachFindByNameRestApiCall(){
//        stubFor(WireMock.get(urlEqualTo("/coach-service/MW"))
//                .withHeader("Content-Type", equalTo("application/json"))
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "application/json")
//                        .withStatus(200)
//                        .withResponseBody(
//                            new Body("""
//                                    {"shortname" : "MW"}
//                                    """
//                            )
//                        )
//                )
//        );
//        ResponseEntity<CoachDTO> coachDTO = coachClient.findByName("MW");
//
//        assertThat(coachDTO).isNotNull();
//    }
//
//    @Test
//    public void testRetryAttemptsOfFindByName() {
//        stubFor(get(urlEqualTo("/coach-service/MW"))
//                .willReturn(aResponse()
//                        .withStatus(500)
//                        .withFixedDelay(1000)
//                )
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "application/json")
//                        .withStatus(200)
//                        .withResponseBody(
//                                new Body("""
//                                    {"shortname" : "MW"}
//                                    """
//                                )
//                        )
//                )
//        );
//
//        ResponseEntity<CoachDTO> response = coachClient.findByName("MW");
//        assertEquals(200, response.getStatusCodeValue());
//    }
//
////    @Test
////    public void testMaxRetryAttemptsOfFindByName() {
////        stubFor(get(urlEqualTo("/coach-service"))
////                .willReturn(aResponse()
////                        .withStatus(500)
////                )
////        );
////
////        RetryOperations retryOperations = new RetryTemplate();
////        assertE(Collections.emptyList(), () -> retryOperations.execute(context -> coachClient.findAll()));
////    }
//
//    @Test
//    public void testCircuitBreakerBehavior() {
//        stubFor(WireMock.get(urlEqualTo("/coach-service/MW"))
//                .withHeader("Content-Type", equalTo("application/json"))
//                .willReturn(aResponse()
//                        .withHeader("Content-Type", "application/json")
//                        .withStatus(200)
//                        .withResponseBody(
//                                new Body("""
//                                    {"shortname" : "MW"}
//                                    """
//                                )
//                        )
//                )
//        );
//        circuitBreakerRegistry.circuitBreaker("CircuitBreakerShortname")
//                .transitionToOpenState();
//        try {
//            ResponseEntity<CoachDTO> coachDTO = coachClient.findByName("MW");
//            assertThat(coachDTO).isNull();
//        } catch (Exception e) {
//            assertEquals(CallNotPermittedException.class, e.getClass());
//        }
//    }
//}