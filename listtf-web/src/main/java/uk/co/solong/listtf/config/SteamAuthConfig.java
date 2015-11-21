package uk.co.solong.listtf.config;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.co.solong.steam4j.auth.SteamAuth;

@Configuration
public class SteamAuthConfig {

    @Value("${realm}")
    private String realm;

    @Bean
    public SteamAuth steamAuth() throws UnsupportedEncodingException {
        String returnTo = realm+"api/openIDCallback";
        SteamAuth steamAuth = new SteamAuth(realm, returnTo);
        return steamAuth;
    }
}
