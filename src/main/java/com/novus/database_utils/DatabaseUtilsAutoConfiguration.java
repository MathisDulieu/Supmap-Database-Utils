package com.novus.database_utils;

import com.novus.database_utils.Alert.AlertDao;
import com.novus.database_utils.Location.LocationDao;
import com.novus.database_utils.Newsletter.NewsletterDao;
import com.novus.database_utils.NewsletterSubscription.NewsletterSubscriptionDao;
import com.novus.database_utils.User.UserDao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class DatabaseUtilsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public <T> UserDao<T> userDao(MongoTemplate mongoTemplate) {
        return new UserDao<>(mongoTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public <T> AlertDao<T> alertDao(MongoTemplate mongoTemplate) {
        return new AlertDao<>(mongoTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public <T> LocationDao<T> locationDao(MongoTemplate mongoTemplate) {
        return new LocationDao<>(mongoTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public <T> NewsletterDao<T> newsletterDao(MongoTemplate mongoTemplate) {
        return new NewsletterDao<>(mongoTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public <T> NewsletterSubscriptionDao<T> newsletterSubscriptionDao(MongoTemplate mongoTemplate) {
        return new NewsletterSubscriptionDao<>(mongoTemplate);
    }

}