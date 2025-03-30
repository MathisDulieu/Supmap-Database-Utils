package com.novus.database_utils.Location;

import com.novus.shared_models.Location.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LocationDao {

    private final MongoTemplate mongoTemplate;

    private static final String LOCATION_COLLECTION = "LOCATIONS";

    public Location findById(String locationId) {
        return mongoTemplate.findById(locationId, Location.class, LOCATION_COLLECTION);
    }

    public void save(Location location) {
        mongoTemplate.save(location, LOCATION_COLLECTION);
    }

}
