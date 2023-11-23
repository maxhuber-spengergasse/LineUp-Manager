package com.example.lineupmanager.coachclient;

import com.example.lineupmanager.generator.service.CoachDTO;
import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

public class CoachClientFallback{

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public ResponseEntity<List<CoachDTO>> findAll(Exception e) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("coach-client");

        if (serviceInstance != null) {
            String serviceUrl = serviceInstance.getUri().toString();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<List<CoachDTO>> responseEntity = restTemplate.exchange(
                    serviceUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CoachDTO>>() {}
            );
            return responseEntity;
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    public ResponseEntity<CoachDTO> findByName(String shortname) {
        return null;
    }

    public ResponseEntity<CoachDTO> insert(CoachDTO coach) {
        return null;
    }

    public ResponseEntity<CoachDTO> update(String shortname, CoachDTO coach) {
        return null;
    }
}
