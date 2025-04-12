package com.novus.database_utils.Log;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LogDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String LOG_COLLECTION = "LOGS";

    public void save(T entity) {
        mongoTemplate.save(entity, LOG_COLLECTION);
    }

}
