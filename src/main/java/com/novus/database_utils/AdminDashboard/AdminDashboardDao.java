package com.novus.database_utils.AdminDashboard;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
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
        return Optional.ofNullable(mongoTemplate.findOne(new Query().limit(1), entityClass, ADMIN_DASHBOARD_COLLECTION));
    }

    public void save(T adminDashboard) {
        mongoTemplate.save(adminDashboard, ADMIN_DASHBOARD_COLLECTION);
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

    public <U> List<U> findTopContributors(Class<U> userClass) {
        List<Document> pipeline = createTopContributorsPipeline();

        return mongoTemplate.aggregate(
                Aggregation.newAggregation(
                        context -> new Document("$facet", new Document("results", pipeline)),
                        Aggregation.unwind("results"),
                        Aggregation.replaceRoot("results")
                ),
                mongoTemplate.getCollectionName(userClass),
                userClass
        ).getMappedResults();
    }

    private List<Document> createTopContributorsPipeline() {
        return Arrays.asList(
                new Document("$match", new Document("userStats", new Document("$exists", true))),
                new Document("$addFields",
                        new Document("totalContributions",
                                new Document("$add", Arrays.asList(
                                        "$userStats.totalReportsSubmitted",
                                        "$userStats.validatedReports",
                                        "$userStats.invalidatedReports"
                                ))
                        )
                ),
                new Document("$sort", new Document("totalContributions", -1)),
                new Document("$limit", 5)
        );
    }

}
