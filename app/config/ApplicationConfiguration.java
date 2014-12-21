package config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import play.Logger;
import controllers.Application;
import controllers.Login;

/*
 * mongo.collection=hatf2
 mongo.db=hatf2
 mongo.host=127.0.0.1
 mongo.port=27017
 */
@Configuration
// @Import({ BackpackListenerConfig.class, PropertyPlaceholderConfig.class })
@ComponentScan("config")
public class ApplicationConfiguration {

    @Autowired
    private ListDaoConfig listDaoConfig;

    @Bean
    public Application application() throws UnknownHostException {
        Application application = new Application();
        application.setListDao(listDaoConfig.getListDao());
        Logger.info("application started===============================");
        return application;
    }

    @Bean
    public Login login() {
        return new Login();
    }

}
