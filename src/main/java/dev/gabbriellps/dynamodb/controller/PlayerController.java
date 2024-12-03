package dev.gabbriellps.dynamodb.controller;

import dev.gabbriellps.dynamodb.dto.ScoreDTO;
import dev.gabbriellps.dynamodb.entity.PlayerHistoryEntity;
import dev.gabbriellps.dynamodb.service.PlayerService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("/{username}/games")
    ResponseEntity<List<PlayerHistoryEntity>> listByUsername(@PathVariable("username") String username) {

        return ResponseEntity.ok(playerService.listByUsername(username));
    }

    @GetMapping("/{username}/games/{gameId}")
    ResponseEntity<PlayerHistoryEntity> findById(@PathVariable("username") String username,
                                                 @PathVariable("gameId") String gameId) {
        PlayerHistoryEntity response = playerService.findById(username, gameId);

        if(Objects.isNull(response)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{username}/games/{gameId}")
    ResponseEntity<?> update(@PathVariable("username") String username,
                                                 @PathVariable("gameId") String gameId,
                                               @RequestBody ScoreDTO requestDTO) {
        PlayerHistoryEntity response = playerService.updatePlayerHistory(username, gameId, requestDTO);

        if(Objects.isNull(response)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok().build();
    }


}
