package com.novus.database_utils.Alert;

import com.novus.shared_models.Alert.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlertDao {

    private final MongoTemplate mongoTemplate;

    private static final String ALERT_COLLECTION = "ALERTS";

    public Alert findById(String alertId) {
        return mongoTemplate.findById(alertId, Alert.class, ALERT_COLLECTION);
    }

    public void save(Alert alert) {
        mongoTemplate.save(alert, ALERT_COLLECTION);
    }

}
