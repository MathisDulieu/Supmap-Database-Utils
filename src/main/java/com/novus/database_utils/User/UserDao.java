package com.novus.database_utils.User;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String USER_COLLECTION = "USERS";

    public T findById(String id, Class<T> entityClass) {
        return mongoTemplate.findById(id, entityClass, USER_COLLECTION);
    }

    public void save(T entity) {
        mongoTemplate.save(entity, USER_COLLECTION);
    }

    public void delete(T entity) {
        mongoTemplate.remove(entity, USER_COLLECTION);
    }

}
