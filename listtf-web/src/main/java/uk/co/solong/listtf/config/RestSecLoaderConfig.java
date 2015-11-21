package uk.co.solong.listtf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.co.solong.listtf.web.controllers.RestSecLoader;
import uk.co.solong.restsec.core.loader.AbstractRestSecLoader;

@Configuration
public class RestSecLoaderConfig {

    @Bean
    public AbstractRestSecLoader abstractRestSecLoader() {
        RestSecLoader restSecLoader = new RestSecLoader();
        return restSecLoader;
    }
}
