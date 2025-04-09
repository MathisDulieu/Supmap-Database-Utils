package com.novus.database_utils.AdminDashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class AdminDashboardDao<T> {

    private final MongoTemplate mongoTemplate;

    private static final String ADMIN_DASHBOARD_COLLECTION = "ADMIN_DASHBOARD";

    public Optional<T> findMe(Class<T> entityClass) {
        return Optional.of(mongoTemplate.findOne(new Query().limit(1), entityClass, ADMIN_DASHBOARD_COLLECTION));
    }

    public <T1, T2, T3, T4> void upsert(String id, Map<Integer, Double> appRatingByNumberOfRate,
                                        List<T1> topContributors,
                                        List<T2> userGrowthStats,
                                        T3 userActivityMetrics,
                                        List<T4> routeRecalculations,
                                        Double incidentConfirmationRate, Map<String, Integer> incidentsByType,
                                        int totalRoutesProposed) {

        mongoTemplate.upsert(new Query(where("_id").is(id)),
                new Update()
                        .setOnInsert("_id", id)
                        .set("appRatingByNumberOfRate", appRatingByNumberOfRate)
                        .set("topContributors", topContributors)
                        .set("userGrowthStats", userGrowthStats)
                        .set("userActivityMetrics", userActivityMetrics)
                        .set("routeRecalculations", routeRecalculations)
                        .set("incidentConfirmationRate", incidentConfirmationRate)
                        .set("incidentsByType", incidentsByType)
                        .set("totalRoutesProposed", totalRoutesProposed)
                , ADMIN_DASHBOARD_COLLECTION );
    }

}
