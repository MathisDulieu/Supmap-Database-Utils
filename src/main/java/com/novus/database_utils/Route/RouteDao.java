package com.novus.database_utils.Route;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RouteDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String ROUTE_COLLECTION = "ROUTES";

    public void save(T entity) {
        mongoTemplate.save(entity, ROUTE_COLLECTION);
    }

    public List<T> findByIds(List<String> routesIds, Class<T> entityClass) {
        Query query = new Query(Criteria.where("_id").in(routesIds));
        return mongoTemplate.find(query, entityClass, ROUTE_COLLECTION);
    }

}
