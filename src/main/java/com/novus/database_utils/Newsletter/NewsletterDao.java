package com.novus.database_utils.Newsletter;

import com.novus.shared_models.Newsletter.Newsletter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterDao {

    private final MongoTemplate mongoTemplate;

    private static final String NEWSLETTER_COLLECTION = "NEWSLETTERS";

    public Newsletter findById(String newsletterId) {
        return mongoTemplate.findById(newsletterId, Newsletter.class, NEWSLETTER_COLLECTION);
    }

    public void save(Newsletter newsletter) {
        mongoTemplate.save(newsletter, NEWSLETTER_COLLECTION);
    }

}
