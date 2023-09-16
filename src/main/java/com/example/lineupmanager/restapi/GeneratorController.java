package com.example.lineupmanager.restapi;

import com.example.lineupmanager.domain.LineUp;
import com.example.lineupmanager.service.CoachDTO;
import com.example.lineupmanager.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping(path = "/generator")
public class GeneratorController {

    private final GeneratorService generatorService;

    @GetMapping("/{name}")
    public ResponseEntity<Optional<LineUp>> findByName(@PathVariable String name){
        return ResponseEntity.ok(generatorService.generateLineUp(name));
    }
}
