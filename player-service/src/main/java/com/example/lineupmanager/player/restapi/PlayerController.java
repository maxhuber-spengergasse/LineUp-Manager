package com.example.lineupmanager.player.restapi;

import com.example.lineupmanager.player.service.PlayerDTO;
import com.example.lineupmanager.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping(path = "/player")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> findAll(){
        return ResponseEntity.ok(playerService.findAll());
    }

    @GetMapping("/{shortname}")
    public ResponseEntity<PlayerDTO> findByName(@PathVariable String shortname){
        return ResponseEntity.ok(playerService.findByShortname(shortname));
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> insert(@RequestBody PlayerDTO player){
        Optional<PlayerDTO> insertedPlayer = playerService.insert(player);
        if (insertedPlayer.isPresent()) {
            return ResponseEntity.created(URI.create("/api/player/" + insertedPlayer.get().toEntity().getShortname())).body(insertedPlayer.get());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{shortname}")
    public ResponseEntity<PlayerDTO> update(@PathVariable String shortname, @RequestBody PlayerDTO player){
        Optional<PlayerDTO> insertedPlayer = playerService.update(shortname, player);
        if (insertedPlayer.isPresent()) {
            return ResponseEntity.of(playerService.update(shortname, player));
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
