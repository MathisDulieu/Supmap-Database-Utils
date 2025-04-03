package com.novus.database_utils.User;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public List<T> searchUsersByUsernamePrefix(String keyword, int page, Class<T> entityClass) {
        Aggregation aggregation = buildUserSearchAggregation(keyword, page);
        return mongoTemplate.aggregate(aggregation, USER_COLLECTION, entityClass).getMappedResults();
    }

    private Aggregation buildUserSearchAggregation(String keyword, int page) {
        int offset = page * 10;

        return Aggregation.newAggregation(
                Aggregation.match(Criteria.where("username").regex("(?i)^" + keyword)),
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "username")),
                Aggregation.skip(offset),
                Aggregation.limit(10)
        );
    }

    public List<T> findNearByUsers(double latitude, double longitude, Class<T> entityClass) {
        Point center = new Point(longitude, latitude);

        Distance distance = new Distance(0.5, Metrics.KILOMETERS);

        Circle circle = new Circle(center, distance);

        Query query = new Query(
                Criteria.where("lastKnownLocation").withinSphere(circle)
        );

        return mongoTemplate.find(query, entityClass, USER_COLLECTION);
    }

}
