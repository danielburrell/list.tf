package uk.co.solong.listtf.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.co.solong.listtf.web.controllers.SessionController;
import uk.co.solong.restsec.core.sessions.SignedCookieManager;
import uk.co.solong.steam4j.auth.SteamAuth;

@Configuration
public class SessionControllerConfig {

    @Inject
    private SteamAuth steamAuth;

    @Inject
    private SignedCookieManager signedCookieManager;

    @Bean
    public SessionController sessionController() {
        SessionController sessionController = new SessionController(steamAuth, signedCookieManager);
        return sessionController;
    }
}
