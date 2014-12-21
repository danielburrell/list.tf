package config;

import java.net.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

import controllers.ListDao;

@Configuration
// @Import({ PropertyPlaceholderConfig.class })
@ComponentScan("config")
public class ListDaoConfig {

    @Bean
    public ListDao getListDao() throws UnknownHostException {
        // TODO Auto-generated method stub
        DB db = new MongoClient("127.0.0.1", 27017).getDB("list");
        Jongo jongo = new Jongo(db);
        MongoCollection userCollection = jongo.getCollection("items").withWriteConcern(WriteConcern.ACKNOWLEDGED);
        MongoCollection schemaCollection = jongo.getCollection("schema").withWriteConcern(WriteConcern.ACKNOWLEDGED);
        
        ListDao listDao = new ListDao();
        listDao.setUserCollection(userCollection);
        listDao.setSchemaCollection(schemaCollection);
        return listDao;
    }

}
