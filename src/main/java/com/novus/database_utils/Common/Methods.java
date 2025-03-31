package com.novus.database_utils.Common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class Methods {

    private final MongoTemplate mongoTemplate;

    public <T> void save(T entity, String collection) {
        mongoTemplate.save(entity, collection);
    }

    public <T> T findById(String id, Class<T> entityClass, String collection) {
        return mongoTemplate.findById(id, entityClass, collection);
    }

    public <T> void delete(T entity, String collection) {
        mongoTemplate.remove(entity, collection);
    }

}
