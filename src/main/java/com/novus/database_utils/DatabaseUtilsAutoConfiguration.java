package com.novus.database_utils;

import com.novus.database_utils.Alert.AlertDao;
import com.novus.database_utils.Common.Methods;
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
    public <T> UserDao<T> userDao(MongoTemplate mongoTemplate, Methods methods) {
        return new UserDao<>(mongoTemplate, methods);
    }

    @Bean
    @ConditionalOnMissingBean
    public <T> AlertDao<T> alertDao(MongoTemplate mongoTemplate, Methods methods) {
        return new AlertDao<>(mongoTemplate, methods);
    }

    @Bean
    @ConditionalOnMissingBean
    public <T> LocationDao<T> locationDao(MongoTemplate mongoTemplate, Methods methods) {
        return new LocationDao<>(mongoTemplate, methods);
    }

    @Bean
    @ConditionalOnMissingBean
    public <T> NewsletterDao<T> newsletterDao(MongoTemplate mongoTemplate, Methods methods) {
        return new NewsletterDao<>(mongoTemplate, methods);
    }

    @Bean
    @ConditionalOnMissingBean
    public <T> NewsletterSubscriptionDao<T> newsletterSubscriptionDao(MongoTemplate mongoTemplate, Methods methods) {
        return new NewsletterSubscriptionDao<>(mongoTemplate, methods);
    }
}