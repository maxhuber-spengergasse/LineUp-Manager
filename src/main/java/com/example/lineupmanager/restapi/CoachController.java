package com.example.lineupmanager.restapi;

import com.example.lineupmanager.service.CoachDTO;
import com.example.lineupmanager.service.CoachService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping(path = "/coach")
public class CoachController {

    private final CoachService coachService;

    @GetMapping
    public ResponseEntity<List<CoachDTO>> findAll(){
        return ResponseEntity.ok(coachService.findAll());
    }

    @GetMapping("/{shortname}")
    public ResponseEntity<CoachDTO> findByName(@PathVariable String shortname){
        return ResponseEntity.ok(coachService.findByShortname(shortname));
    }

    @PostMapping
    public ResponseEntity<CoachDTO> insert(@RequestBody @Valid CoachDTO coach){
        Optional<CoachDTO> insertedCoach = coachService.insert(coach);
        if (insertedCoach.isPresent()) {
            return ResponseEntity.created(URI.create("/api/coach/" + insertedCoach.get().toEntity().getShortname())).body(insertedCoach.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{shortname}")
    public ResponseEntity<CoachDTO> update(@PathVariable String shortname, @RequestBody @Valid CoachDTO coach){
        Optional<CoachDTO> updatedCoach = coachService.update(shortname, coach);
        if (updatedCoach.isPresent()) {
            return ResponseEntity.of(coachService.update(shortname, coach));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
