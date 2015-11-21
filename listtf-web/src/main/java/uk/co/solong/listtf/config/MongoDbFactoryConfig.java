package uk.co.solong.listtf.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoException;
import com.mongodb.MongoURI;

@Configuration
public class MongoDbFactoryConfig {

    //mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?
    @Value("${mongoUri}")
    private MongoURI mongoUri;

    @Bean
    public MongoDbFactory MongoDbFactory() throws MongoException, UnknownHostException {
        
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoUri);
        return mongoDbFactory;
    }
}
