package com.novus.database_utils.NewsletterSubscription;

import com.novus.database_utils.Common.Methods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterSubscriptionDao<T> {

    private final MongoTemplate mongoTemplate;
    private final Methods methods;

    private static final String NEWSLETTER_SUBSCRIPTION_COLLECTION = "NEWSLETTER_SUBSCRIPTIONS";

    public T findById(String id, Class<T> entityClass) {
        return methods.findById(id, entityClass, NEWSLETTER_SUBSCRIPTION_COLLECTION);
    }

    public void save(T entity) {
        methods.save(entity, NEWSLETTER_SUBSCRIPTION_COLLECTION);
    }

    public void delete(T entity) {
        methods.delete(entity, NEWSLETTER_SUBSCRIPTION_COLLECTION);
    }

}
