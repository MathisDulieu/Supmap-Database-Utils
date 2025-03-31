package com.novus.database_utils.Alert;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlertDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String ALERT_COLLECTION = "ALERTS";

    public T findById(String id, Class<T> entityClass) {
        return mongoTemplate.findById(id, entityClass, ALERT_COLLECTION);
    }

    public void save(T entity) {
        mongoTemplate.save(entity, ALERT_COLLECTION);
    }

    public void delete(T entity) {
        mongoTemplate.remove(entity, ALERT_COLLECTION);
    }

}
