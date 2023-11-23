package com.example.lineupmanager.coachclient;

import com.example.lineupmanager.generator.service.CoachDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "COACH-CLIENT", fallback = CoachClientFallback.class)
public interface CoachClient {

    @CircuitBreaker(name = "CircuitBreaker")
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping
    ResponseEntity<List<CoachDTO>> findAll();

    @CircuitBreaker(name = "CircuitBreakerShortname")
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @GetMapping(value = "/{shortname}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CoachDTO> findByName(@PathVariable String shortname);

    @CircuitBreaker(name = "CircuitBreaker")
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @PostMapping
    ResponseEntity<CoachDTO> insert(@RequestBody @Valid CoachDTO coach);

    @CircuitBreaker(name = "CircuitBreaker")
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    @PatchMapping("/{shortname}")
    ResponseEntity<CoachDTO> update(@PathVariable String shortname, @RequestBody @Valid CoachDTO coach);
}
