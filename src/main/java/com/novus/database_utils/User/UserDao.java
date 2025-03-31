package com.novus.database_utils.User;

import com.novus.database_utils.Common.Methods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao<T> {

    private final MongoTemplate mongoTemplate;
    private final Methods methods;

    private static final String USER_COLLECTION = "USERS";

    public T findById(String id, Class<T> entityClass) {
        return methods.findById(id, entityClass, USER_COLLECTION);
    }

    public void save(T entity) {
        methods.save(entity, USER_COLLECTION);
    }

    public void delete(T entity) {
        methods.delete(entity, USER_COLLECTION);
    }

}
