package com.novus.database_utils.Location;

import com.novus.database_utils.Common.Methods;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LocationDao<T> {

    private final MongoTemplate mongoTemplate;
    private final Methods methods;

    private static final String LOCATION_COLLECTION = "LOCATIONS";

    public T findById(String id, Class<T> entityClass) {
        return methods.findById(id, entityClass, LOCATION_COLLECTION);
    }

    public void save(T entity) {
        methods.save(entity, LOCATION_COLLECTION);
    }

    public void delete(T entity) {
        methods.delete(entity, LOCATION_COLLECTION);
    }

}
