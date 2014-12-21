package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import models.DeleteError;
import models.Detail;
import models.Item;
import models.SteamUser;
import models.ValidationFailure;

import org.expressme.openid.Association;
import org.expressme.openid.Endpoint;
import org.expressme.openid.OpenIdManager;
import org.jongo.MongoCollection;

import play.Logger;
import play.Play;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.openid.OpenID;
import play.libs.openid.OpenID.UserInfo;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import uk.co.solong.tf2.playeritems.Attributes;
import uk.co.solong.tf2.playeritems.Backpack;
import uk.co.solong.tf2.playeritems.Items;
import uk.co.solong.tf2.playersummaries.PlayerSummaryResult;
import uk.co.solong.tf2.schema.Schema;
import views.html.index;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.WriteResult;

public class Application extends Controller {

    private static ListDao listDao;
    private static String steamApiKey = Play.application().configuration().getString("apiKey");
    private static String domain = Play.application().configuration().getString("domain");
    private static String playerSummaryUrl = Play.application().configuration().getString("playerSummary");
    private static String getPlayerItems = Play.application().configuration().getString("getPlayerItems");

    
    public static Result index() {
        return ok(index.render(""));
    }

    public static Result privacy() {
        return TODO;
    }

    public static Result about() {
        return TODO;
    }

    public static Result wanted(Long steamId) {
        return ok(index.render(""));
    }

    

    public static Result markImpossibleHistoric() throws FileNotFoundException {

        File f = Play.application().getFile("/reference/historic.json");
        FileInputStream fis = new FileInputStream(f);

        JsonNode historic = Json.parse(fis);
        Map<String, String> map = Json.fromJson(historic, Map.class);

        Long steamId = Long.parseLong(session("steamId"));
        // List<Item> items = getPlayerItemDocumentFromMongo(steamId).item;

        SteamUser s = getPlayerItemDocumentFromMongo(steamId);

        for (Item wantedItem : s.item) {
            for (String blackList : map.keySet()) {
                // for each blacklistable item, blacklist it
                if (wantedItem.itemId.toString().equals(blackList))
                    wantedItem.state = 0;
            }
        }
        WriteResult u = listDao.saveSteamUser(s);
        
        ObjectNode result = Json.newObject();
        result.put("success", "Historic items marked");
        return ok(result);
    }

    
/*
 * Operation should run in 3 iterations.
 * 1) for each item, add a detail if you have it.
 *     - When adding a detail because you have it, if a superset of the detail exists, tick it off.
 *     - When adding a detail because you have it, if a superset of the detail doesn't exist, just add the exact contents.
 * 2) If a schema item is suppressable, and you don't have it, then suppress it.
 * 3) if 'add missing qualities' is checked for each item, add a detail if you dont' have 1 detail in a possible quality, using the loosest sensible terms.
 *    e.g. Any,Any,Any, Strange
 *    - do not add any qualities which are in the "except" list (e.g. unusual)
 *    - do not add this to any supressed items.
 */
    public static Promise<Result> sync() {

        Long steamId = Long.parseLong(session("steamId"));

        Promise<Result> p = WS.url(getPlayerItems).setQueryParameter("key", steamApiKey).setQueryParameter("steamId", steamId.toString()).get()
                .map(new Function<WSResponse, Result>() {
                    public Result apply(WSResponse response) {
                        try {
                            Long steamId = Long.parseLong(session("steamId"));
                            String node = response.getBody();
                            ObjectMapper om = new ObjectMapper();
                            Backpack backpack = om.readValue(node, Backpack.class);
                            if (new Integer(1).equals(backpack.getResult().getStatus())) {
                                List<Items> items = backpack.getResult().getItems();
                                SteamUser s = getPlayerItemDocumentFromMongo(steamId);
                                for (Items backpackItem : items) {
                                    if (!isBackPackItemInWantedList(backpackItem, s.item)) {

                                        addDetailToWantedList(s.item, backpackItem);
                                    }
                                }

                                for (Item wantedItem : s.item) {
                                    boolean areAllDetailsObtained = areAllDetailsObtained(wantedItem.details);
                                    if (areAllDetailsObtained) {
                                        applyCompleteStateToItem(wantedItem);
                                    }
                                }

                                // Logger.info("Model generation complete {}",Json.toJson(s));

                                WriteResult u = listDao.saveSteamUser(s);
                                Logger.info("Write complete");
                                ObjectNode result = Json.newObject();
                                result.put("success", "Backpack synced");
                                return ok(result);
                            } else {
                                ObjectNode result = Json.newObject();
                                result.put("error", "not public profile");
                                return ok(result);
                            }

                        } catch (Exception e) {
                            ObjectNode result = Json.newObject();
                            result.put("error", "steamDown");
                            return ok(result);
                        }
                    }

                    private void applyCompleteStateToItem(Item wantedItem) { // TODO
                        wantedItem.state = 3;
                        Logger.info("Closed item group");
                    }

                    private boolean areAllDetailsObtained(List<Detail> details) { // TODO
                        if (details == null || details.size() == 0)
                            return false;
                        boolean areAllDetailsObtained = true;
                        for (Detail detail : details) {
                            areAllDetailsObtained &= detail.isObtained;
                        }
                        return areAllDetailsObtained;
                    }

                    private void addDetailToWantedList(List<Item> wantedItems, Items backpackItem) {

                        for (Item wantedItem : wantedItems) {
                            if (wantedItem.itemId.equals(backpackItem.getDefindex().longValue())) {
                                Logger.info("Comparison {} {} ", wantedItem.itemId, backpackItem.getDefindex());

                                Detail detail = new Detail();
                                detail.quality = backpackItem.getQuality().intValue();
                                detail.levelNumber = backpackItem.getLevel().intValue();
                                detail.isTradable = !backpackItem.getFlag_cannot_trade() ? 1 : 0;
                                detail.isCraftable = !backpackItem.getFlag_cannot_craft() ? 1 : 0;
                                detail.craftNumber = getCraftNumber(backpackItem).longValue();
                                detail.isGiftWrapped = isGiftWrapped(backpackItem) ? 1 : 0;
                                detail.isObtained = true;
                                wantedItem.details.add(detail);

                                Logger.info("Really imported Single Item");
                                break;
                            }
                        }
                        Logger.info("Imported Single Item");

                    }

                    /**
                     * Returns true if the item in the users backpack is listed
                     * within their wanted items.
                     * 
                     * @param backpackItem
                     * 
                     * @param wantedItemsWithDetails
                     * 
                     * @return
                     */
                    private boolean isBackPackItemInWantedList(Items backpackItem, List<Item> wantedItemsWithDetails) {
                        for (Item wantedItemWithDetails : wantedItemsWithDetails) {
                            if (wantedItemWithDetails.itemId.equals(backpackItem.getDefindex())) {
                                return (isSubsetOfDetails(backpackItem, wantedItemWithDetails));
                            }
                        }
                        return false;
                    }

                    /**
                     * Returns true if the the given item, exactly or by subset,
                     * matches the given details If true, then the detail is
                     * marked as complete, and true returned, otherwise false is
                     * returned.
                     * 
                     * @param wantedItem
                     * 
                     * @param backpackItem
                     * 
                     * @return
                     */
                    private boolean isSubsetOfDetails(Items backpackItem, Item wantedItem) {
                        for (Detail detail : wantedItem.details) {
                            if (isSubsetOfDetail(backpackItem, detail)) {
                                markDetailAsObtained(detail);
                                return true;
                            }
                        }
                        return false;
                    }

                    /**
                     * Returns true if the the given item, exactly or by subset,
                     * matches the given detail. If true, then the detail is
                     * marked as complete, and true returned, otherwise false is
                     * returned.
                     * 
                     * @param backpackItem
                     * 
                     * @param detail
                     * 
                     * @return
                     */
                    private boolean isSubsetOfDetail(Items backpackItem, Detail detail) {
                        return checkQualitySubset(detail, backpackItem) && checkLevelSubset(detail, backpackItem) && checkTradableSubset(detail, backpackItem)
                                && checkCraftableSubset(detail, backpackItem) && checkCraftNumberSubset(detail, backpackItem)
                                && checkGiftWrappedSubset(detail, backpackItem);
                    }

                    private void markDetailAsObtained(Detail detail) {
                        // TODO extract the detailid, isObtained to true
                        detail.isObtained = true;
                    }

                    private boolean checkGiftWrappedSubset(Detail detail, Items backpackItem) {
                        boolean isGiftWrapped = isGiftWrapped(backpackItem);
                        boolean equalYes = (isGiftWrapped && new Integer(1).equals(detail.isGiftWrapped));
                        boolean equalNo = (!isGiftWrapped && new Integer(0).equals(detail.isGiftWrapped));
                        boolean equalAny = (new Integer(-1).equals(detail.isGiftWrapped));
                        return (equalYes || equalNo || equalAny);
                    }

                    private boolean isGiftWrapped(Items backpackItem) {
                        if (backpackItem.getAttributes() != null) {
                            for (Attributes attribute : backpackItem.getAttributes()) {
                                if (new Integer(186).equals(attribute.getDefindex())) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    }

                    private boolean checkCraftNumberSubset(Detail detail, Items backpackItem) {
                        Number craftNumber = getCraftNumber(backpackItem);
                        boolean equalYes = (craftNumber.equals(detail.quality));
                        boolean equalAny = (new Integer(-1).equals(detail.quality));
                        return (equalYes || equalAny);
                    }

                    private Number getCraftNumber(Items backpackItem) {
                        Number craftNumber = 0;
                        if (backpackItem.getAttributes() != null) {
                            for (Attributes attribute : backpackItem.getAttributes()) {
                                if (new Integer(229).equals(attribute.getDefindex())) {
                                    craftNumber = Long.parseLong(attribute.getValue());
                                }
                            }
                        }
                        return craftNumber;
                    }

                    private boolean checkCraftableSubset(Detail detail, Items backpackItem) {
                        boolean isCraftable = !backpackItem.getFlag_cannot_craft();
                        boolean equalYes = (isCraftable && new Integer(1).equals(detail.isCraftable));
                        boolean equalNo = (!isCraftable && new Integer(0).equals(detail.isCraftable));
                        boolean equalAny = (new Integer(-1).equals(detail.isCraftable));
                        return (equalYes || equalNo || equalAny);
                    }

                    private boolean checkTradableSubset(Detail detail, Items backpackItem) { // TODO
                                                                                             // Auto-generated
                                                                                             // method
                                                                                             // stub
                        boolean isTradable = !backpackItem.getFlag_cannot_trade();
                        boolean equalYes = (isTradable && new Integer(1).equals(detail.isCraftable));
                        boolean equalNo = (!isTradable && new Integer(0).equals(detail.isCraftable));
                        boolean equalAny = (new Integer(-1).equals(detail.isCraftable));
                        return (equalYes || equalNo || equalAny);
                    }

                    private boolean checkLevelSubset(Detail detail, Items backpackItem) { // TODO
                                                                                          // Auto-generated
                                                                                          // method
                                                                                          // stub
                                                                                          //
                        Number level = backpackItem.getLevel();
                        boolean equalYes = (level.equals(detail.levelNumber));
                        boolean equalAny = (new Integer(-1).equals(detail.levelNumber));
                        return (equalYes || equalAny);
                    }

                    private boolean checkQualitySubset(Detail detail, Items backpackItem) { // TODO
                                                                                            // Auto-generated
                                                                                            // method
                                                                                            // stub
                                                                                            //
                        Number quality = backpackItem.getLevel();
                        boolean equalYes = (quality.equals(detail.quality));
                        boolean equalAny = (new Integer(-1).equals(detail.quality));
                        return (equalYes || equalAny);
                    }
                });
        return p;

        // fetch items from backpack // error if not public

        // otherwise, get all wanted items, // for each item in
        // backpack, call the shouldAdd() function passing in // the
        // wanted list. // if it returns true, then call the
        // importSingleItem passing in the // item

    }

    public static Result getName(Long steamId) {
        SteamUser c = listDao.findUser(steamId);
        if (c != null) {
            SteamUser user = new SteamUser();
            user.name = c.name;
            user.avatar = c.avatar;
            JsonNode result = Json.toJson(user);
            return ok(result);
        } else {
            return ok("name Unavailable");
        }
    }

    

    

    public static Result getWantedList(Long steamId) {
        // call the dao to get the wanted list from the database
        Logger.info("Getting Wanted List For {}", steamId);
        SteamUser list = getPlayerItemDocumentFromMongo(steamId);
        JsonNode jsonList = Json.toJson(list);
        return ok(jsonList);
    }

    private static SteamUser getPlayerItemDocumentFromMongo(Long steamId) {
        SteamUser c = listDao.findUser(steamId);
        return c;
    }

    public static Result wantedPartial() {
        return TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result addDetail() throws JsonProcessingException {
        JsonNode json = request().body().asJson();// (Detail.class);
        Item item = Json.fromJson(json, Item.class);
        item.details.get(0).detailId = UUID.randomUUID().toString();
        Logger.info("Validating..{}", json);
        boolean validDetail = validate(item);
        if (validDetail) {
            Logger.info("Valid input");
            long steamId = Long.parseLong(session("steamId"));
            WriteResult u = listDao.saveDetail(steamId,item); 
            Logger.info("Add item");

            return ok(Json.toJson(item)); //
        } else {
            Logger.info("Invalid input");
            ValidationFailure error = new ValidationFailure();
            error.error = "Invalid Request";
            return badRequest(Json.toJson(error)); // fail
        }
        // (<{steamId bigint}>, <{wantedId bigint}>, <{quality int}>, <{level
        // smallint}>, <{isTradable tinyint}>, <{isCraftable tinyint}>,
        // <{craftNumber bigint}>, <{isGiftWrapped tinyint}>, <{isObtained
        // tinyInt}>, <{priority int}>, <{price varchar(45)}>

        // user.save();
        /*
         * List<Detail> details = new ArrayList<Detail>(1); details.add(detail);
         * Item item = new Item(); item.wantedId = wantedId; item.details =
         * details; SteamUser user = new SteamUser(); user.steamId =
         * Long.parseLong(session("steamId"));
         * 
         * JsonNode result = Json.toJson(1);
         */
    }

    private static boolean validate(Item item) {
        // TODO Auto-generated method stub
        Detail detail = item.details.get(0);
        Logger.info("{}", Json.toJson(detail));
        return (detail.isCraftable != null && detail.isGiftWrapped != null && detail.isObtained != null && detail.isTradable != null
                && detail.craftNumber != null && detail.levelNumber != null && detail.quality != null);
    }

    public static Result editDetail(Long detailId) {
        // requires index on [detailId & steamId]
        // update wanted.details () where detailId = x and steamId = session()

        return TODO;
    }

    public static Result deleteDetail(String wantedId, String detailId) {
        // require index on [detailId & steamId]
        // delete from wanted.details where detailId = x and steamId = session()

        long steamId = Long.parseLong(session("steamId"));

        SteamUser s = listDao.findUser(steamId);
        for (Item item : s.item) {
            if (item.wantedId.equals(wantedId)) {
                for (Detail detail : item.details) {
                    if (detail.detailId.equals(detailId)) {
                        item.details.remove(detail);
                        WriteResult u = listDao.deleteDetail(steamId,wantedId,item);
                        Logger.info("Results: {}", u.getN());
                        return ok(Json.newObject().put("rowCount", u.getN()));
                    }
                }
            }
        }

        DeleteError error = new DeleteError();
        error.error = "Delete failed";
        return badRequest(Json.toJson(error));

    }

    public static Result setPriority(String wantedId, String detailId, int priority) {
        // require index on [detailId & steamId]
        // delete from wanted.details where detailId = x and steamId = session()

        if (priority > 3 || priority < 0) {
            DeleteError error = new DeleteError();
            error.error = "Delete failed";
            return badRequest(Json.toJson(error));
        } else {

            long steamId = Long.parseLong(session("steamId"));
            SteamUser s = listDao.findUser(steamId);
            for (Item item : s.item) {
                if (item.wantedId.equals(wantedId)) {
                    for (Detail detail : item.details) {
                        if (detail.detailId.equals(detailId)) {
                            detail.priority = priority;
                            WriteResult u = listDao.setPriority(steamId,wantedId,item);
                            
                            Logger.info("Results: {}", u.getN());
                            return ok(Json.newObject().put("rowCount", u.getN()));
                        }
                    }
                }
            }

            DeleteError error = new DeleteError();
            error.error = "Delete failed";
            return badRequest(Json.toJson(error));

        }

    }

    public static Result priorityDown(long detailId) {
        // require index on [detailId & steamId]
        // delete from wanted.details where detailId = x and steamId = session()
        /*
         * long steamId = Long.parseLong(session("steamId")); Object[] params =
         * new Object[] { steamId, detailId }; JdbcTemplate jdbcTemplate = new
         * JdbcTemplate(DB.getDataSource()); SimpleJdbcCall call = new
         * SimpleJdbcCall
         * (jdbcTemplate).withSchemaName("wanted").withProcedureName
         * ("decreaseDetailPriority"); Logger.info("Executed"); Map<String,
         * Object> result = call.execute(params); Logger.info("Results: {}",
         * Json.toJson(result)); long rowCount = (long)
         * result.get("updated_row_count"); if ((rowCount > 0)) { return
         * ok(Json.newObject().put("rowCount", rowCount)); } else { DeleteError
         * error = new DeleteError(); error.error = "Delete failed"; return
         * badRequest(Json.toJson(error)); }
         */
        Logger.error("funcitonality missing");
        return null;
    }

    // TODO add wantedId, either: query for the wantedid contents, and update
    // that content, or trust the client to send the contents

    public static Result markDetailAs(String wantedId, String detailId, Boolean isObtained) {
        // TODO extract the detailid, isObtained to true

        long steamId = Long.parseLong(session("steamId"));
        SteamUser s = listDao.findUser(steamId);
        for (Item item : s.item) {
            if (item.wantedId.equals(wantedId)) {
                for (Detail detail : item.details) {
                    if (detail.detailId.equals(detailId)) {
                        detail.isObtained = isObtained;
                        WriteResult u = listDao.setDetailObtained(steamId,wantedId,item);
                        Logger.info("Results: {}", u.getN());
                        return ok(Json.newObject().put("rowCount", u.getN()));
                    }
                }
            }
        }

        return ok(Json.newObject().put("rowCount", 0));
    }

    public static Result markAs(String wantedId, Long state) {
        Logger.info("markedas");
        long rowCount = setItemState(wantedId, state);
        if ((rowCount > 0)) {
            return ok(Json.newObject().put("rowCount", rowCount));
        } else {
            DeleteError error = new DeleteError();
            error.error = "Delete failed";
            return badRequest(Json.toJson(error));
        }
    }

    private static long setItemState(String wantedId, Long state) {
        long steamId = Long.parseLong(session("steamId"));
        WriteResult u = listDao.setItemState(steamId,wantedId,state);
        Logger.info("Executed");
        long rowCount = u.getN();
        return rowCount;
    }

    public ListDao getListDao() {
        return listDao;
    }

    public void setListDao(ListDao listDao) {
        Application.listDao = listDao;
    }

}
