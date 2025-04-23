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

    public List<T> findAlertsByPositionAndExpiration(double latitude, double longitude, Date currentDate, Class<T> entityClass) {
        double radiusInDegrees = 500 / 111000.0;

        double radiusLongitudeDegrees = radiusInDegrees / Math.cos(Math.toRadians(latitude));

        double minLatitude = latitude - radiusInDegrees;
        double maxLatitude = latitude + radiusInDegrees;
        double minLongitude = longitude - radiusLongitudeDegrees;
        double maxLongitude = longitude + radiusLongitudeDegrees;

        Query query = new Query(
                new Criteria().andOperator(
                        Criteria.where("location.latitude").gte(minLatitude).lte(maxLatitude),
                        Criteria.where("location.longitude").gte(minLongitude).lte(maxLongitude),
                        new Criteria().orOperator(
                                Criteria.where("expiresAt").gt(currentDate),
                                Criteria.where("expiresAt").isNull()
                        )
                )
        );

        return mongoTemplate.find(query, entityClass, ALERT_COLLECTION);
    }

    public <G, E> List<E> findAlertsByRoute(List<G> geoPoints, Date currentDate, Class<E> entityClass) {
        Set<E> uniqueAlerts = new HashSet<>();

        for (G point : geoPoints) {
            try {
                Method getLatMethod = point.getClass().getMethod("getLatitude");
                Method getLngMethod = point.getClass().getMethod("getLongitude");

                Double latitude = (Double) getLatMethod.invoke(point);
                Double longitude = (Double) getLngMethod.invoke(point);

                double radiusInDegrees = 500 / 111000.0;

                double radiusLongitudeDegrees = radiusInDegrees / Math.cos(Math.toRadians(latitude));

                double minLatitude = latitude - radiusInDegrees;
                double maxLatitude = latitude + radiusInDegrees;
                double minLongitude = longitude - radiusLongitudeDegrees;
                double maxLongitude = longitude + radiusLongitudeDegrees;

                Query query = new Query(
                        new Criteria().andOperator(
                                Criteria.where("location.latitude").gte(minLatitude).lte(maxLatitude),
                                Criteria.where("location.longitude").gte(minLongitude).lte(maxLongitude),
                                new Criteria().orOperator(
                                        Criteria.where("expiresAt").gt(currentDate),
                                        Criteria.where("expiresAt").isNull()
                                )
                        )
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
