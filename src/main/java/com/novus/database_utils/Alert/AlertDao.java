package com.novus.database_utils.Alert;

import com.novus.database_utils.Common.Methods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlertDao<T> {

    private final MongoTemplate mongoTemplate;
    private final Methods methods;

    private static final String ALERT_COLLECTION = "ALERTS";

    public T findById(String id, Class<T> entityClass) {
        return methods.findById(id, entityClass, ALERT_COLLECTION);
    }

    public void save(T entity) {
        methods.save(entity, ALERT_COLLECTION);
    }

    public void delete(T entity) {
        methods.delete(entity, ALERT_COLLECTION);
    }

}
