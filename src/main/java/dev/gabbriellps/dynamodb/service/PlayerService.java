package dev.gabbriellps.dynamodb.service;

import dev.gabbriellps.dynamodb.dto.ScoreDTO;
import dev.gabbriellps.dynamodb.entity.PlayerHistoryEntity;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Service;

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

}
