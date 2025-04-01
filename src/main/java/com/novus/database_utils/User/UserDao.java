package com.novus.database_utils.User;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String USER_COLLECTION = "USERS";

    public Optional<T> findById(String id, Class<T> entityClass) {
        return Optional.of(mongoTemplate.findById(id, entityClass, USER_COLLECTION));
    }

    public void save(T entity) {
        mongoTemplate.save(entity, USER_COLLECTION);
    }

    public void delete(T entity) {
        mongoTemplate.remove(entity, USER_COLLECTION);
    }

    public boolean isUsernameAlreadyUsed(String username) {
        return mongoTemplate.exists(new Query(Criteria.where("username").is(username)), USER_COLLECTION);
    }

    public boolean isEmailAlreadyUsed(String email) {
        return mongoTemplate.exists(new Query(Criteria.where("email").is(email)), USER_COLLECTION);
    }

    public Optional<T> findByEmail(String email, Class<T> entityClass) {
        return Optional.of(mongoTemplate.findOne(new Query(Criteria.where("email").is(email)), entityClass, USER_COLLECTION));
    }

}
