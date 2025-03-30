package com.novus.database_utils.NewsletterSubscription;

import com.novus.shared_models.NewsletterSubscription.NewsletterSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsletterSubscriptionDao {

    private final MongoTemplate mongoTemplate;

    private static final String NEWSLETTER_SUBSCRIPTION_COLLECTION = "NEWSLETTER_SUBSCRIPTIONS";

    public NewsletterSubscription findById(String newsletterSubscriptionId) {
        return mongoTemplate.findById(newsletterSubscriptionId, NewsletterSubscription.class, NEWSLETTER_SUBSCRIPTION_COLLECTION);
    }

    public void save(NewsletterSubscription newsletterSubscription) {
        mongoTemplate.save(newsletterSubscription, NEWSLETTER_SUBSCRIPTION_COLLECTION);
    }

}
