package uk.co.solong.listtf.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.co.solong.listtf.core.dao.ListDao;
import uk.co.solong.listtf.web.controllers.ItemStateController;
import uk.co.solong.restsec.core.sessions.SessionManager;

@Configuration
public class ItemStateControllerConfig {

    @Inject
    private ListDao listDao;
    @Inject
    private SessionManager sessionManager;

    @Bean
    public ItemStateController itemStateController() {
        ItemStateController itemStateController = new ItemStateController(listDao, sessionManager);
        return itemStateController;
    }
}
