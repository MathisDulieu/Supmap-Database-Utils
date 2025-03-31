package com.novus.database_utils.Location;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LocationDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String LOCATION_COLLECTION = "LOCATIONS";

    public T findById(String id, Class<T> entityClass) {
        return mongoTemplate.findById(id, entityClass, LOCATION_COLLECTION);
    }

    public void save(T entity) {
        mongoTemplate.save(entity, LOCATION_COLLECTION);
    }

    public void delete(T entity) {
        mongoTemplate.remove(entity, LOCATION_COLLECTION);
    }

}
