package com.novus.database_utils.NewsletterSubscription;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsletterSubscriptionDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String NEWSLETTER_SUBSCRIPTION_COLLECTION = "NEWSLETTER_SUBSCRIPTIONS";

    public Optional<T> findById(String id, Class<T> entityClass) {
        return Optional.of(mongoTemplate.findById(id, entityClass, NEWSLETTER_SUBSCRIPTION_COLLECTION));
    }

    public void save(T entity) {
        mongoTemplate.save(entity, NEWSLETTER_SUBSCRIPTION_COLLECTION);
    }

    public void delete(T entity) {
        mongoTemplate.remove(entity, NEWSLETTER_SUBSCRIPTION_COLLECTION);
    }

    public boolean isEmailAlreadySubscribed(String email) {
        return mongoTemplate.exists(new Query(Criteria.where("email").is(email)), NEWSLETTER_SUBSCRIPTION_COLLECTION);
    }

}
