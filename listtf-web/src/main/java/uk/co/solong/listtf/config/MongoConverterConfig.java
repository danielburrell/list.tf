package uk.co.solong.listtf.config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;

import uk.co.solong.listtf.core.pojo.mongo.converters.WantedDocumentIdReaderConverter;
import uk.co.solong.listtf.core.pojo.mongo.converters.WantedDocumentIdWriterConverter;

@Configuration
public class MongoConverterConfig {

    @Inject
    private MongoDbFactory mongoDbFactory;

    @Bean
    public MongoConverter MongoConverter() {
        MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext = new MongoMappingContext();
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(mongoDbFactory, mappingContext);
        List<Object> converters = new ArrayList<Object>();
        converters.add(new WantedDocumentIdReaderConverter());
        converters.add(new WantedDocumentIdWriterConverter());
        CustomConversions c = new CustomConversions(converters);
        mappingMongoConverter.setCustomConversions(c);
        return mappingMongoConverter;
    }
}
