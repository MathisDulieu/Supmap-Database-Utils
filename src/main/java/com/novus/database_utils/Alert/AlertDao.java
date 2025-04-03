package com.novus.database_utils.Alert;

import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class AlertDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String ALERT_COLLECTION = "ALERTS";

    public Optional<T> findById(String id, Class<T> entityClass) {
        return Optional.of(mongoTemplate.findById(id, entityClass, ALERT_COLLECTION));
    }

    public void save(T entity) {
        mongoTemplate.save(entity, ALERT_COLLECTION);
    }

    public void delete(T entity) {
        mongoTemplate.remove(entity, ALERT_COLLECTION);
    }

    public List<T> findAlertsByPosition(double latitude, double longitude, Class<T> entityClass) {
        Point center = new Point(longitude, latitude);

        Distance distance = new Distance(0.5, Metrics.KILOMETERS);

        Circle circle = new Circle(center, distance);

        Query query = new Query(
                Criteria.where("location").withinSphere(circle)
        );

        return mongoTemplate.find(query, entityClass, ALERT_COLLECTION);
    }

    public <G, E> List<E> findAlertsByRoute(List<G> geoPoints, Class<E> entityClass) {
        Set<E> uniqueAlerts = new HashSet<>();

        Distance distance = new Distance(0.5, Metrics.KILOMETERS);

        for (G point : geoPoints) {
            try {
                Method getLatMethod = point.getClass().getMethod("getLatitude");
                Method getLngMethod = point.getClass().getMethod("getLongitude");

                Double latitude = (Double) getLatMethod.invoke(point);
                Double longitude = (Double) getLngMethod.invoke(point);

                Point center = new Point(longitude, latitude);

                Circle circle = new Circle(center, distance);

                Query query = new Query(
                        Criteria.where("location").withinSphere(circle)
                );

                List<E> alertsNearPoint = mongoTemplate.find(query, entityClass, ALERT_COLLECTION);
                uniqueAlerts.addAll(alertsNearPoint);

            } catch (Exception e) {
                throw new RuntimeException("Impossible d'accéder aux coordonnées géographiques de l'objet", e);
            }
        }

        return new ArrayList<>(uniqueAlerts);
    }

}
