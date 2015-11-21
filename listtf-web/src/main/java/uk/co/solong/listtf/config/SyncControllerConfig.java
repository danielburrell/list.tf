package uk.co.solong.listtf.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.co.solong.listtf.core.dao.ListDao;
import uk.co.solong.listtf.web.controllers.SyncController;
import uk.co.solong.restsec.core.sessions.SessionManager;

@Configuration
public class SyncControllerConfig {

    @Inject
    private ListDao listDao;
    @Inject
    private SessionManager sessionManager;

    @Bean
    public SyncController syncController() {
        SyncController syncController = new SyncController(listDao, sessionManager);
        return syncController;
    }
}
