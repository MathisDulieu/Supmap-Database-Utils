package com.novus.database_utils.Newsletter;

import com.novus.database_utils.Common.Methods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterDao<T> {

    private final MongoTemplate mongoTemplate;
    private final Methods methods;

    private static final String NEWSLETTER_COLLECTION = "NEWSLETTERS";

    public T findById(String id, Class<T> entityClass) {
        return methods.findById(id, entityClass, NEWSLETTER_COLLECTION);
    }

    public void save(T entity) {
        methods.save(entity, NEWSLETTER_COLLECTION);
    }

    public void delete(T entity) {
        methods.delete(entity, NEWSLETTER_COLLECTION);
    }

}
