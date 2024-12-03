package dev.gabbriellps.dynamodb.service;

import dev.gabbriellps.dynamodb.dto.ScoreDTO;
import dev.gabbriellps.dynamodb.entity.PlayerHistoryEntity;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private DynamoDbTemplate dynamoDbTemplate;

    public PlayerService(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }


    public void savePlayerHistory(String username, ScoreDTO requestDTO) {
        PlayerHistoryEntity entity = PlayerHistoryEntity.fromScore(username, requestDTO);

        dynamoDbTemplate.save(entity);

    }

    public List<PlayerHistoryEntity> listByUsername(String username) {
        Key key = Key.builder().partitionValue(username).build();

        QueryConditional conditional = QueryConditional.keyEqualTo(key);

        QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
                .queryConditional(conditional)
                .build();

        PageIterable<PlayerHistoryEntity> response = dynamoDbTemplate.query(queryRequest, PlayerHistoryEntity.class);

        return response.items().stream().collect(Collectors.toList());
    }

    public PlayerHistoryEntity findById(String username, String gameId) {
        Key key = Key.builder()
                .partitionValue(username)
                .sortValue(gameId)
                .build();

        return dynamoDbTemplate.load(key, PlayerHistoryEntity.class);
    }

    public PlayerHistoryEntity updatePlayerHistory(String username, String gameId, ScoreDTO requestDTO) {
        Key key = Key.builder()
                .partitionValue(username)
                .sortValue(gameId)
                .build();

        PlayerHistoryEntity player = dynamoDbTemplate.load(key, PlayerHistoryEntity.class);

        if(Objects.isNull(player)) {
            return null;
        }

        player.setScore(requestDTO.score());

        return dynamoDbTemplate.save(player);
    }

}
