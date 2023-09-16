package com.example.lineupmanager.restapi;

import com.example.lineupmanager.service.TeamDTO;
import com.example.lineupmanager.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping(path = "/team")
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamDTO>> findAll(){
        return ResponseEntity.ok(teamService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<TeamDTO> findByName(@PathVariable String name){
        return ResponseEntity.ok(teamService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<TeamDTO> insert(@RequestBody TeamDTO team){
        Optional<TeamDTO> insertedTeam = teamService.insert(team);
        if (insertedTeam.isPresent()) {
            return ResponseEntity.created(URI.create("/api/team/" + insertedTeam.get().toEntity().getName())).body(insertedTeam.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{name}")
    public ResponseEntity<TeamDTO> update(@PathVariable String name, @RequestBody TeamDTO team){
        Optional<TeamDTO> insertedTeam = teamService.update(name, team);
        if (insertedTeam.isPresent()) {
            return ResponseEntity.of(teamService.update(name, team));
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
