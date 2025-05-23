package com.novus.database_utils.Location;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocationDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String LOCATION_COLLECTION = "LOCATIONS";

    public Optional<T> findById(String id, Class<T> entityClass) {
        return Optional.of(mongoTemplate.findById(id, entityClass, LOCATION_COLLECTION));
    }

    public void save(T entity) {
        mongoTemplate.save(entity, LOCATION_COLLECTION);
    }

    public void delete(T entity) {
        mongoTemplate.remove(entity, LOCATION_COLLECTION);
    }

    public List<T> findByIds(List<String> locationIds, Class<T> entityClass) {
        Query query = new Query(Criteria.where("_id").in(locationIds));
        return mongoTemplate.find(query, entityClass, LOCATION_COLLECTION);
    }

}
