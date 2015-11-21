package uk.co.solong.listtf.web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.solong.listtf.core.exceptions.SteamAPIDown;
import uk.co.solong.restsec.core.cookies.DecryptedCookie;
import uk.co.solong.restsec.core.cookies.EncryptedCookie;
import uk.co.solong.restsec.core.sessions.SignedCookieManager;
import uk.co.solong.steam4j.auth.AuthorizationResult;
import uk.co.solong.steam4j.auth.SteamAuth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

@RestController
@RequestMapping("/api")
public class SessionController {
    private static final Logger logger = LoggerFactory.getLogger(ListController.class);
    private final SignedCookieManager cookieManager;
    private final SteamAuth steamAuth;

    @RequestMapping("/whoAmI")
    public WhoAmI whoAmI(HttpServletRequest request) {
        DecryptedCookie cookie = cookieManager.decryptCookieFromRequest(request);
        WhoAmI whoAmI = new WhoAmI();
        whoAmI.setId(cookie.getUserId());
        whoAmI.setName(cookie.getDisplayName());
        return whoAmI;
    }

    /**
     * A call is first made to generateSteam to establish what addres
     * 
     * @param request
     * @return
     */
    @RequestMapping("/generateSteamUrl")
    public @ResponseBody JsonNode generateSteamUrl(HttpServletRequest request) {
        logger.info("Logging in");
        try {
            return new TextNode(steamAuth.getLoginUrl());
        } catch (Exception e) {
            throw new SteamAPIDown();
        }
    }

    @RequestMapping("/openIDCallback")
    public @ResponseBody boolean openIDCallback(HttpServletRequest request, HttpServletResponse response) {
        AuthorizationResult result = steamAuth.authenticate(request);
        if (result.isSuccess()) {
            logger.info("{} logged in ", result.getUserId());
            DecryptedCookie decryptedCookie = new DecryptedCookie();
            decryptedCookie.setDisplayName("unknown");
            decryptedCookie.setUserId(result.getUserId());
            decryptedCookie.setExpiry(new DateTime().plusDays(1).getMillis());
            decryptedCookie.setLoggedIn(true);
            EncryptedCookie cookie = cookieManager.encryptCookie(decryptedCookie);
            response.addCookie(cookie);
            return true;
        } else {
            return false;
        }
    }

    public SessionController(SteamAuth steamAuth, SignedCookieManager cookieManager) {
        this.steamAuth = steamAuth;
        this.cookieManager = cookieManager;
    }

}
