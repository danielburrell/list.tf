package uk.co.solong.listtf.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import uk.co.solong.listtf.core.dao.ListDao;

@Configuration
public class ListDaoConfig {

    @Inject
    private MongoTemplate mongoTemplate;

    @Bean
    public ListDao listDao() {
        ListDao listDao = new ListDao(mongoTemplate);
        return listDao;
    }
}
