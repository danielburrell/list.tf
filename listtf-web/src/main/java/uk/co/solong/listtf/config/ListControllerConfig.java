package uk.co.solong.listtf.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.co.solong.listtf.core.dao.ListDao;
import uk.co.solong.listtf.web.controllers.ListController;

@Configuration
public class ListControllerConfig {

    @Inject
    private ListDao listDao;

    @Bean
    public ListController listController() {
        ListController listController = new ListController(listDao);
        return listController;
    }
}
