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
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
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

    public void updateUserActivityStatus() {
        Calendar todayThreshold = getTodayThreshold();
        Calendar monthThreshold = getMonthThreshold();

        markInactiveUsers(todayThreshold, monthThreshold);
        reactivateUsers(todayThreshold, monthThreshold);
    }

    private Calendar getTodayThreshold() {
        Calendar todayThreshold = Calendar.getInstance();
        todayThreshold.add(Calendar.DAY_OF_MONTH, -1);
        return todayThreshold;
    }

    private Calendar getMonthThreshold() {
        Calendar monthThreshold = Calendar.getInstance();
        monthThreshold.add(Calendar.MONTH, -1);
        return monthThreshold;
    }

    private void markInactiveUsers(Calendar todayThreshold, Calendar monthThreshold) {
        markDailyInactiveUsers(todayThreshold);
        markMonthlyInactiveUsers(monthThreshold);
    }

    private void markDailyInactiveUsers(Calendar todayThreshold) {
        Update todayUpdate = new Update().set("todayStatus", "INACTIVE");
        Query todayQuery = new Query(Criteria.where("lastActivityDate").lt(todayThreshold.getTime())
                .and("todayStatus").is("ACTIVE"));
        mongoTemplate.updateMulti(todayQuery, todayUpdate, USER_COLLECTION);
    }

    private void markMonthlyInactiveUsers(Calendar monthThreshold) {
        Update monthUpdate = new Update().set("monthStatus", "INACTIVE");
        Query monthQuery = new Query(Criteria.where("lastActivityDate").lt(monthThreshold.getTime())
                .and("monthStatus").is("ACTIVE"));
        mongoTemplate.updateMulti(monthQuery, monthUpdate, USER_COLLECTION);
    }

    private void reactivateUsers(Calendar todayThreshold, Calendar monthThreshold) {
        reactivateDailyUsers(todayThreshold);
        reactivateMonthlyUsers(monthThreshold);
    }

    private void reactivateDailyUsers(Calendar todayThreshold) {
        Update reactivateTodayUpdate = new Update().set("todayStatus", "ACTIVE");
        Query reactivateTodayQuery = new Query(Criteria.where("lastActivityDate").gte(todayThreshold.getTime())
                .and("todayStatus").is("INACTIVE"));
        mongoTemplate.updateMulti(reactivateTodayQuery, reactivateTodayUpdate, USER_COLLECTION);
    }

    private void reactivateMonthlyUsers(Calendar monthThreshold) {
        Update reactivateMonthUpdate = new Update().set("monthStatus", "ACTIVE");
        Query reactivateMonthQuery = new Query(Criteria.where("lastActivityDate").gte(monthThreshold.getTime())
                .and("monthStatus").is("INACTIVE"));
        mongoTemplate.updateMulti(reactivateMonthQuery, reactivateMonthUpdate, USER_COLLECTION);
    }

    public <Q> int countDailyActiveUsers(Class<Q> entityClass) {
        Query query = new Query(Criteria.where("todayStatus").is("ACTIVE"));
        return (int) mongoTemplate.count(query, entityClass, USER_COLLECTION);
    }

    public <Q> int countMonthlyActiveUsers(Class<Q> entityClass) {
        Query query = new Query(Criteria.where("monthStatus").is("ACTIVE"));
        return (int) mongoTemplate.count(query, entityClass, USER_COLLECTION);
    }

    public <Q> double calculateRetentionRate(Class<Q> entityClass) {
        Calendar lastMonth = Calendar.getInstance();
        lastMonth.add(Calendar.MONTH, -1);
        Date oneMonthAgo = lastMonth.getTime();

        Query oldUsersQuery = new Query(Criteria.where("createdAt").lt(oneMonthAgo));
        long totalOldUsers = mongoTemplate.count(oldUsersQuery, entityClass, USER_COLLECTION);

        if (totalOldUsers == 0) {
            return 0.0;
        }

        Query activeOldUsersQuery = new Query(
                Criteria.where("createdAt").lt(oneMonthAgo)
                        .and("monthStatus").is("ACTIVE")
        );
        long activeOldUsers = mongoTemplate.count(activeOldUsersQuery, entityClass, USER_COLLECTION);

        return (double) activeOldUsers / totalOldUsers;
    }

}
