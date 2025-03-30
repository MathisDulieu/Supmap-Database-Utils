package com.novus.database_utils.User;

import com.novus.shared_models.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final MongoTemplate mongoTemplate;

    private static final String USER_COLLECTION = "USERS";

    public User findById(String userId) {
        return mongoTemplate.findById(userId, User.class, USER_COLLECTION);
    }

    public void save(User user) {
        mongoTemplate.save(user, USER_COLLECTION);
    }

}
