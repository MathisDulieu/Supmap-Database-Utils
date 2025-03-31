package com.novus.database_utils.Newsletter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String NEWSLETTER_COLLECTION = "NEWSLETTERS";

    public T findById(String id, Class<T> entityClass) {
        return mongoTemplate.findById(id, entityClass, NEWSLETTER_COLLECTION);
    }

    public void save(T entity) {
        mongoTemplate.save(entity, NEWSLETTER_COLLECTION);
    }

    public void delete(T entity) {
        mongoTemplate.remove(entity, NEWSLETTER_COLLECTION);
    }

}
