package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.expressme.openid.Association;
import org.expressme.openid.Endpoint;
import org.expressme.openid.OpenIdManager;
import org.jongo.MongoCollection;

import models.Detail;
import models.Item;
import models.SteamUser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.mongodb.WriteResult;

import play.Logger;
import play.Play;
import play.libs.Json;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.openid.OpenID;
import play.libs.openid.OpenID.UserInfo;
import play.mvc.Controller;
import play.mvc.Result;
import uk.co.solong.tf2.playersummaries.PlayerSummaryResult;
import uk.co.solong.tf2.schema.Schema;
import views.html.index;

public class Login extends Controller {
    private static ListDao listDao;
    private static String steamApiKey = Play.application().configuration().getString("apiKey");
    private static String domain = Play.application().configuration().getString("domain");
    

    public static Result logout() {
        session().clear();
        flash("success", "You have logged out");
        return ok(index.render(""));
    }

    public static Result loginStatus() {
        String steamId = session("steamId");
        Map<String, Object> map = new HashMap<String, Object>();

        if (steamId != null) {
            map.put("steamId", steamId);
            map.put("loggedIn", true);
            JsonNode result = Json.toJson(map);
            return ok(result);
        } else {
            map.put("loggedIn", false);
            JsonNode result = Json.toJson(map);
            return ok(result);
        }
    }

    public static Result openIDCallbackAPI() {
        switch (session("success")) {
        case "0": {
            String url = "/id/" + session("steamId") + "/";
            JsonNode result = Json.newObject().put("successUrl", url);
            return ok(result);
        }
        case "1": {
            String url = "/id/" + session("steamId") + "/";
            JsonNode result = Json.newObject().put("successUrl", url);
            return ok(result);
        }
        case "2": {
            String url = "/id/" + session("steamId") + "/";
            JsonNode result = Json.newObject().put("successUrl", url);
            return ok(result);
        }
        case "-1": {
            String url = "/loginFailed/";
            JsonNode result = Json.newObject().put("successUrl", url);
            return ok(result);
        }
        default: {
            String url = "/iHaveNoIdea/";
            JsonNode result = Json.newObject().put("successUrl", url);
            return ok(result);
        }
        }

    }

    public static Promise<Result> openIDCallback() {
        Promise<UserInfo> d = OpenID.verifiedId();

        return d.map(new Function<UserInfo, Result>() {
            public Result apply(UserInfo userInfo) throws MalformedURLException, IOException {
                Long steamId = getSteamIdFromResponseUrl(userInfo.id);
                if (steamId > 0) {
                    // perform login tasks
                    session("steamId", steamId.toString());
                    // call newUserItemNothing function.
                    int userStatus = getUserWelcomeStatus(steamId);
                    switch (userStatus) {
                    case 0:
                        session("success", "0");
                        return redirect("/id/" + steamId + "/");
                    case 1:
                        session("success", "1");
                        return redirect("/id/" + steamId + "/");
                    case 2:
                        session("success", "2");
                        return redirect("/id/" + steamId + "/");
                    default:
                        session("success", "-2");
                        return redirect("/id/" + steamId + "/");
                    }

                } else {
                    // something went wrong
                    session("success", "-1");
                    return ok(index.render(""));
                }

            }

            /**
             * Calls out to the database to obtain the UserWelcomeStatus:
             *
             * @param steamId
             *
             * @return 0 if the user is new
             * @return 1 if the user is not new, but there are new items
             * @return 2 if the user is not new and there are no new items
             * @throws IOException
             * @throws MalformedURLException
             */
            public int getUserWelcomeStatus(Long steamId) throws MalformedURLException, IOException {
                // TODO Auto-generated method stub
                Logger.info("Checking welcome status");
                SteamUser user = listDao.findUser(steamId);
                if (user == null) {
                    // TODO: create user
                    createNewUser(steamId);
                    return 0;
                } else {
                    Schema c = listDao.getLatestSchema();
                    if (c.getSchemaVersion() != user.schemaId) {
                        // TODO: add new items
                        return 1;
                    } else {
                        return 2;
                    }
                }
            }

            private void createNewUser(Long steamId) throws MalformedURLException, IOException {
                // TODO Auto-generated method stub
                Schema c = listDao.getLatestSchema();
                List<uk.co.solong.tf2.schema.Items> items = c.getResult().getItems();
                SteamUser user = getNameFromSteam(steamId);
                user.steamId = steamId;
                user.item = new ArrayList<Item>();
                user.schemaId = c.getSchemaVersion();

                for (uk.co.solong.tf2.schema.Items item : items) {
                    // item.
                    Item wantedItem = new Item();
                    wantedItem.itemId = item.getDefindex();
                    wantedItem.state = 2;
                    wantedItem.name = item.getItem_name();
                    wantedItem.image = item.getImage_url();
                    wantedItem.wantedId = UUID.randomUUID().toString();
                    wantedItem.details = new ArrayList<Detail>();
                    user.item.add(wantedItem);
                }
                
                WriteResult u = listDao.createUser(user);
            }

            private Long getSteamIdFromResponseUrl(String responseUrl) {
                String[] steamIdFragments = responseUrl.split("/");
                try {
                    return Long.parseLong(steamIdFragments[5]);
                } catch (Throwable nfe) {
                    return null;
                }
            }
        });

    }
    
    public static Result login() {

        Logger.info("Logging in");
        try {
            OpenIdManager manager = new OpenIdManager();
            manager.setReturnTo("http://" + domain + "/openIDCallback");
            manager.setRealm("http://" + domain + "/");

            Endpoint endpoint = manager.lookupEndpoint("http://steamcommunity.com/openid");
            Association association = manager.lookupAssociation(endpoint);
            String url = manager.getAuthenticationUrl(endpoint, association);
            JsonNode result = Json.newObject().put("steamUrl", url);
            return ok(result);

        } catch (Exception e) {
            JsonNode result = Json.newObject().put("steamDownUrl", "/steamDown");
            return ok(result);
        }

        /*
         * 
         * Map<String, String> attributes = new HashMap<String, String>();
         * attributes.put("email", "http://schema.openid.net/contact/email");
         * Promise<String> p =
         * OpenID.redirectURL("http://steamcommunity.com/openid",
         * "http://localhost:8010/openIDCallback", null, null,
         * "http://localhost:8010/");
         * 
         * return p.map(new Function<String, Result>() { public Result
         * apply(String url) { return redirect(url); } });
         */
    }
    
    private static SteamUser getNameFromSteam(Long steamId) throws MalformedURLException, IOException {
        // TODO: move this into a 1 time when user signs up, signs in, and
        // refresh once per day efficiently
        String url = String
                .format("http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=%s&steamIds=%s", new Object[] { steamApiKey, steamId });
        Logger.info(url);
        InputStream input = new URL(url).openStream();
        Reader streamReader = new InputStreamReader(input, "UTF-8");
        ObjectMapper m = new ObjectMapper();
        ObjectReader reader = m.reader(PlayerSummaryResult.class);
        PlayerSummaryResult s = reader.readValue(streamReader);
        streamReader.close();
        SteamUser user = new SteamUser();
        user.name = s.getResponse().getPlayers().get(0).getPersonaname();
        user.avatar = s.getResponse().getPlayers().get(0).getAvatarmedium();
        return user;
    }

    public static ListDao getListDao() {
        return listDao;
    }

    public static void setListDao(ListDao listDao) {
        Login.listDao = listDao;
    }
}
