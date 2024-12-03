package dev.gabbriellps.dynamodb.controller;

import dev.gabbriellps.dynamodb.dto.ScoreDTO;
import dev.gabbriellps.dynamodb.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/players")
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/{username}/games")
    ResponseEntity<?> save(@PathVariable("username") String username,
                           @RequestBody ScoreDTO requestDTO) {

        playerService.savePlayerHistory(username, requestDTO);
        return ResponseEntity.ok().build();
    }

}
