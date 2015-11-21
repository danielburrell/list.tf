package uk.co.solong.listtf.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

@Configuration
public class MongoTemplateConfig {

    @Inject
    private MongoDbFactory mongoDbFactory;
    @Inject
    private MongoConverter mongoConverter;

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, mongoConverter);
        return mongoTemplate;
    }
}
