package uk.co.solong.listtf.config;

import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import uk.co.solong.application.annotations.RootConfiguration;
import uk.co.solong.application.config.PropertyPlaceholderConfig;
import uk.co.solong.restsec.core.config.EntitlementAspectConfig;
import uk.co.solong.restsec.core.config.SessionManagerConfig;
import uk.co.solong.restsec.core.config.SignedCookieManagerConfig;

@Configuration
@RootConfiguration
@Import({ PropertyPlaceholderConfig.class, RepositoryRestMvcAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class, WebMvcAutoConfiguration.class, EmbeddedServletContainerAutoConfiguration.class,
        DispatcherServletAutoConfiguration.class, ServerPropertiesAutoConfiguration.class, SyncControllerConfig.class, ListControllerConfig.class,
        ItemStateControllerConfig.class, DetailControllerConfig.class, WebConfig.class, ListDaoConfig.class, SessionManagerConfig.class,
        SignedCookieManagerConfig.class, EntitlementAspectConfig.class, MongoTemplateConfig.class, SessionControllerConfig.class, MongoDbFactoryConfig.class,
        MongoConverterConfig.class, RestSecLoaderConfig.class, SteamAuthConfig.class })
public class Config {

}
