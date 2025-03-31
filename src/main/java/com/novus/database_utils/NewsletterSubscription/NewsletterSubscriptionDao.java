package com.novus.database_utils.NewsletterSubscription;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterSubscriptionDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String NEWSLETTER_SUBSCRIPTION_COLLECTION = "NEWSLETTER_SUBSCRIPTIONS";

    public T findById(String id, Class<T> entityClass) {
        return mongoTemplate.findById(id, entityClass, NEWSLETTER_SUBSCRIPTION_COLLECTION);
    }

    public void save(T entity) {
        mongoTemplate.save(entity, NEWSLETTER_SUBSCRIPTION_COLLECTION);
    }

    public void delete(T entity) {
        mongoTemplate.remove(entity, NEWSLETTER_SUBSCRIPTION_COLLECTION);
    }

}
