package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import models.DeleteError;
import models.Detail;
import models.Item;
import models.SteamUser;
import models.ValidationFailure;

import org.expressme.openid.Association;
import org.expressme.openid.Endpoint;
import org.expressme.openid.OpenIdManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import play.Logger;
import play.Play;
import play.db.DB;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.F.Tuple;
import play.libs.Json;
import play.libs.OpenID;
import play.libs.OpenID.UserInfo;
import play.libs.WS;
import play.mvc.BodyParser;
import play.mvc.Content;
import play.mvc.Controller;
import play.mvc.Result;
import uk.co.solong.tf2.playeritems.Attributes;
import uk.co.solong.tf2.playeritems.Backpack;
import uk.co.solong.tf2.playeritems.Items;
import uk.co.solong.tf2.playersummaries.PlayerSummaryResult;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Application extends Controller {

  private static String steamApiKey = Play.application().configuration().getString("apiKey");
  private static String playerSummaryUrl = Play.application().configuration().getString("playerSummary");
  private static String domain = Play.application().configuration().getString("domain");
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

  public static Result markImpossibleHistoric() throws FileNotFoundException {

    File f = Play.application().getFile("/reference/historic.json");
    FileInputStream fis = new FileInputStream(f);

    JsonNode historic = Json.parse(fis);
    Map<String, String> map = Json.fromJson(historic, Map.class);

    Long steamId = Long.parseLong(session("steamId"));
    SteamUser s = getItemsForPlayer(steamId);

    for (String item : map.keySet()) {
      for (Item backpackItem : s.item) {
        if (backpackItem.itemId.equals(Long.parseLong(item))) {
          setItemState(backpackItem.wantedId, 0L);
        }
      }
    }
    ObjectNode result = Json.newObject();
    result.put("success", "Historic items marked");
    return ok(result);
  }

  public static Promise<Result> openIDCallback() {
    Promise<UserInfo> d = OpenID.verifiedId();

    return d.map(new Function<UserInfo, Result>() {
      public Result apply(UserInfo userInfo) {
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
       */
      public int getUserWelcomeStatus(Long steamId) {
        // TODO Auto-generated method stub
        Logger.info("Checking welcome status");
        Object[] params = new Object[] { steamId };
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withSchemaName("wanted").withProcedureName("getWelcomeStatus");
        Map<String, Object> result = call.execute(params);
        return (int) result.get("welcome_status");
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

  public static Promise<Result> sync() {

    Long steamId = Long.parseLong(session("steamId"));

    Promise<Result> p = WS.url(getPlayerItems).setQueryParameter("key", steamApiKey).setQueryParameter("steamId", steamId.toString()).get()
        .map(new Function<WS.Response, Result>() {
          public Result apply(WS.Response response) {
            try {
              Long steamId = Long.parseLong(session("steamId"));
              String node = response.getBody();
              ObjectMapper om = new ObjectMapper();
              Backpack backpack = om.readValue(node, Backpack.class);
              if (new Integer(1).equals(backpack.getResult().getStatus())) {
                List<Items> items = backpack.getResult().getItems();
                SteamUser s = getItemsForPlayer(steamId);
                for (Items backpackItem : items) {
                  if (!isBackPackItemInWantedList(backpackItem, s.item)) {
                    importSingleDetail(backpackItem);
                  }
                }
                s = getItemsForPlayer(steamId);
                for (Item wantedItem : s.item) {
                  boolean areAllDetailsObtained = areAllDetailsObtained(wantedItem.details);
                  if (areAllDetailsObtained) {
                    applyCompleteStateToItem(wantedItem);
                  }
                }
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

          private void applyCompleteStateToItem(Item wantedItem) {
            // TODO Auto-generated method stub
            long rowCount = setItemState(wantedItem.wantedId, 3L);
            Logger.info("Closed item group");
          }

          private boolean areAllDetailsObtained(List<Detail> details) {
            // TODO Auto-generated method stub
            if (details == null || details.size() == 0)
              return false;
            boolean areAllDetailsObtained = true;
            for (Detail detail : details) {
              areAllDetailsObtained &= detail.isObtained;
            }
            return areAllDetailsObtained;
          }

          private void importSingleDetail(Items backpackItem) {
            // TODO add detail to table
            long steamId = Long.parseLong(session("steamId"));
            Object[] params = new Object[] { steamId, backpackItem.getDefindex(), backpackItem.getQuality(), backpackItem.getLevel(),
                !backpackItem.getFlag_cannot_trade(), !backpackItem.getFlag_cannot_craft(), getCraftNumber(backpackItem), isGiftWrapped(backpackItem), true };
            JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());
            SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withSchemaName("wanted").withProcedureName("addDetailFromItemId");
            Logger.info("Executed");
            Map<String, Object> result = call.execute(params);
            Logger.info("Results: {}", Json.toJson(result));
            Logger.info("Imported Single Item");

          }

          /**
           * Returns true if the item in the users backpack is listed within their wanted items.
           *
           * @param backpackItem
           * @param wantedItemsWithDetails
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
           * Returns true if the the given item, exactly or by subset, matches the given details If true, then the detail is marked as complete, and true
           * returned, otherwise false is returned.
           *
           * @param wantedItem
           * @param backpackItem
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
           * Returns true if the the given item, exactly or by subset, matches the given detail. If true, then the detail is marked as complete, and true
           * returned, otherwise false is returned.
           *
           * @param backpackItem
           * @param detail
           * @return
           */
          private boolean isSubsetOfDetail(Items backpackItem, Detail detail) {
            return checkQualitySubset(detail, backpackItem) && checkLevelSubset(detail, backpackItem) && checkTradableSubset(detail, backpackItem)
                && checkCraftableSubset(detail, backpackItem) && checkCraftNumberSubset(detail, backpackItem) && checkGiftWrappedSubset(detail, backpackItem);
          }

          private void markDetailAsObtained(Detail detail) {
            // TODO extract the detailid, isObtained to true
            long detailId = detail.detailId;
            long steamId = Long.parseLong(session("steamId"));
            Object[] params = new Object[] { steamId, detailId };
            JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());
            SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withSchemaName("wanted").withProcedureName("markDetailAsObtained");
            Logger.info("Executed");
            Map<String, Object> result = call.execute(params);
            Logger.info("Results: {}", Json.toJson(result));
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

          private boolean checkTradableSubset(Detail detail, Items backpackItem) {
            // TODO Auto-generated method stub
            boolean isTradable = !backpackItem.getFlag_cannot_trade();
            boolean equalYes = (isTradable && new Integer(1).equals(detail.isCraftable));
            boolean equalNo = (!isTradable && new Integer(0).equals(detail.isCraftable));
            boolean equalAny = (new Integer(-1).equals(detail.isCraftable));
            return (equalYes || equalNo || equalAny);
          }

          private boolean checkLevelSubset(Detail detail, Items backpackItem) {
            // TODO Auto-generated method stub
            Number level = backpackItem.getLevel();
            boolean equalYes = (level.equals(detail.levelNumber));
            boolean equalAny = (new Integer(-1).equals(detail.levelNumber));
            return (equalYes || equalAny);
          }

          private boolean checkQualitySubset(Detail detail, Items backpackItem) {
            // TODO Auto-generated method stub
            Number quality = backpackItem.getLevel();
            boolean equalYes = (quality.equals(detail.quality));
            boolean equalAny = (new Integer(-1).equals(detail.quality));
            return (equalYes || equalAny);
          }
        });
    return p;

    // fetch items from backpack
    // error if not public

    // otherwise, get all wanted items,
    // for each item in backpack, call the shouldAdd() function passing in the wanted list.
    // if it returns true, then call the importSingleItem passing in the item

  }

  public static Promise<Result> getName(Long steamId) {
    Promise<Result> p = WS.url(playerSummaryUrl).setQueryParameter("key", steamApiKey).setQueryParameter("steamIds", steamId.toString()).get()
        .map(new Function<WS.Response, Result>() {
          public Result apply(WS.Response response) {
            try {
              String node = response.getBody();
              ObjectMapper om = new ObjectMapper();
              PlayerSummaryResult s = om.readValue(node, PlayerSummaryResult.class);
              SteamUser user = new SteamUser();
              user.name = s.getResponse().getPlayers().get(0).getPersonaname();
              user.avatar = s.getResponse().getPlayers().get(0).getAvatarmedium();
              JsonNode result = Json.toJson(user);
              return ok(result);
            } catch (Exception e) {
              return ok("name Unavailable");
            }
          }
        });
    return p;
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
     * Map<String, String> attributes = new HashMap<String, String>(); attributes.put("email", "http://schema.openid.net/contact/email"); Promise<String> p =
     * OpenID.redirectURL("http://steamcommunity.com/openid", "http://localhost:8010/openIDCallback", null, null, "http://localhost:8010/");
     *
     * return p.map(new Function<String, Result>() { public Result apply(String url) { return redirect(url); } });
     */
  }

  public static Result getWantedList(Long steamId) {
    // call the dao to get the wanted list from the database
    Logger.info("Getting Wanted List For {}", steamId);
    SteamUser s = getItemsForPlayer(steamId);
    JsonNode result = Json.toJson(s);
    return ok(result);

    // JsonNode result = Json.toJson(user);
    /*
     * DataSource ds = DB.getDataSource(); JdbcTemplate d = new JdbcTemplate(ds); d.qu s.setSql("CALL `wanted`.`getWantedList`(?)"); s.setJdbcTemplate(d);
     * s.qu(steamId);
     *
     *
     * if (steamId == 4027L) { ArrayList<Item> wantedList = new ArrayList<Item>(); Item item = new Item(); item.itemId = 5000L; ArrayList<Detail> details = new
     * ArrayList<Detail>(); Detail detail = new Detail(); detail.craftNumber = 666L; detail.detailId = 11L; detail.isCraftable = true; detail.isGiftWrapped =
     * true; detail.isObtained = true; detail.isTradable = true; detail.level = 100; detail.quality = 3; detail.price = "3 Keys"; details.add(detail);
     * item.details = details; wantedList.add(item); wantedList.add(item); wantedList.add(item);
     *
     * JsonNode result = Json.toJson(wantedList); return ok(result); } else
     */
    // return TODO;
  }

  private static SteamUser getItemsForPlayer(Long steamId) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());
    Object[] params = new Object[] { steamId };
    List<Map<String, Object>> results = jdbcTemplate.queryForList("CALL `wanted`.`getWantedList`(?)", params);
    SteamUser s = new SteamUser();
    s.steamId = steamId;
    s.item = new LinkedList<Item>();
    Map<Long, Item> normalisationmap = new HashMap<Long, Item>();
    for (Map<String, Object> row : results) {
      Long wantedId = (Long) row.get("wanted_id");
      Item existingWanted = normalisationmap.get(wantedId);

      if (existingWanted == null) {
        Item newWantedItem = new Item();
        newWantedItem.wantedId = wantedId;
        newWantedItem.itemId = (Long) row.get("item_id");
        newWantedItem.name = (String) row.get("item_name");
        newWantedItem.image = (String) row.get("item_image");
        newWantedItem.state = (Integer) row.get("state");
        newWantedItem.details = new LinkedList<Detail>();

        normalisationmap.put(wantedId, newWantedItem);
        s.item.add(newWantedItem);
      }

      Object detailId = row.get("detail_id");
      if (detailId != null) {
        Detail detail = new Detail();
        detail.detailId = (Long) detailId;
        detail.craftNumber = (Long) row.get("craft_number");
        detail.isCraftable = (Integer) row.get("is_craftable");
        detail.isGiftWrapped = (Integer) row.get("is_gift_wrapped");
        detail.isObtained = (Boolean) row.get("is_obtained");
        detail.isTradable = (Integer) row.get("is_tradable");
        detail.levelNumber = (Integer) row.get("level_number");
        detail.price = Objects.toString(row.get("price"));
        detail.quality = (Integer) row.get("quality");
        Object priority = row.get("priority");
        if (priority == null) {
          priority = new Integer(0);
        }
        detail.priority = (Integer) priority;
        normalisationmap.get(wantedId).details.add(detail);
      }

    }
    // apply priority
    for (Item item : s.item) {
      item.effectivePriority = maxOfWantedDetails(item.details);
    }

    return s;
  }

  private static int maxOfWantedDetails(List<Detail> details) {
    int highest = 0;
    for (Detail detail : details) {
      if (!detail.isObtained && detail.priority > highest) {
        highest = detail.priority;
      }
    }
    return highest;
  }

  public static Result wantedPartial() {
    return TODO;
  }

  @BodyParser.Of(BodyParser.Json.class)
  public static Result addDetail(Long wantedId) {
    JsonNode json = request().body().asJson();// (Detail.class);
    Detail detail = Json.fromJson(json, Detail.class);
    Logger.info("Validating..{}", json.asText());
    boolean validDetail = validate(detail);

    if (validDetail) {
      Logger.info("Valid input");
      long steamId = Long.parseLong(session("steamId"));
      Object[] params = new Object[] { steamId, wantedId, detail.quality, detail.levelNumber, detail.isTradable, detail.isCraftable, detail.craftNumber,
          detail.isGiftWrapped, detail.isObtained, detail.priority, detail.price };
      JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());
      SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withSchemaName("wanted").withProcedureName("addDetail");
      Logger.info("Executed");
      Map<String, Object> result = call.execute(params);
      Logger.info("Results: {}", Json.toJson(result));
      detail.detailId = (long) result.get("detail_id");
      Item item = new Item();
      item.wantedId = wantedId;
      item.details = new ArrayList<Detail>(1);
      item.details.add(detail);
      return ok(Json.toJson(item));
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
     * List<Detail> details = new ArrayList<Detail>(1); details.add(detail); Item item = new Item(); item.wantedId = wantedId; item.details = details; SteamUser
     * user = new SteamUser(); user.steamId = Long.parseLong(session("steamId"));
     *
     * JsonNode result = Json.toJson(1);
     */
  }

  private static boolean validate(Detail detail) {
    // TODO Auto-generated method stub
    Logger.info("{}", Json.toJson(detail));
    return (detail.isCraftable != null && detail.isGiftWrapped != null && detail.isObtained != null && detail.isTradable != null && detail.craftNumber != null
        && detail.levelNumber != null && detail.quality != null);
  }

  public static Result editDetail(Long detailId) {
    // requires index on [detailId & steamId]
    // update wanted.details () where detailId = x and steamId = session()

    return TODO;
  }

  public static Result deleteDetail(long detailId) {
    // require index on [detailId & steamId]
    // delete from wanted.details where detailId = x and steamId = session()
    long steamId = Long.parseLong(session("steamId"));
    Object[] params = new Object[] { steamId, detailId };
    JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withSchemaName("wanted").withProcedureName("deleteDetail");
    Logger.info("Executed");
    Map<String, Object> result = call.execute(params);
    Logger.info("Results: {}", Json.toJson(result));
    long rowCount = (long) result.get("deleted_row_count");
    if ((rowCount > 0)) {
      return ok(Json.newObject().put("rowCount", rowCount));
    } else {
      DeleteError error = new DeleteError();
      error.error = "Delete failed";
      return badRequest(Json.toJson(error));
    }
  }

  public static Result setPriority(long detailId, int priority) {
    // require index on [detailId & steamId]
    // delete from wanted.details where detailId = x and steamId = session()
    if (priority > 3 || priority < 0) {
      DeleteError error = new DeleteError();
      error.error = "Delete failed";
      return badRequest(Json.toJson(error));
    } else {
      long steamId = Long.parseLong(session("steamId"));
      Object[] params = new Object[] { steamId, detailId, priority };
      JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());
      SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withSchemaName("wanted").withProcedureName("setDetailPriority");
      Logger.info("Executed");
      Map<String, Object> result = call.execute(params);
      Logger.info("Results: {}", Json.toJson(result));
      long rowCount = (long) result.get("updated_row_count");
      if ((rowCount > 0)) {
        return ok(Json.newObject().put("rowCount", rowCount));
      } else {
        DeleteError error = new DeleteError();
        error.error = "Delete failed";
        return badRequest(Json.toJson(error));
      }
    }
  }

  public static Result priorityDown(long detailId) {
    // require index on [detailId & steamId]
    // delete from wanted.details where detailId = x and steamId = session()
    long steamId = Long.parseLong(session("steamId"));
    Object[] params = new Object[] { steamId, detailId };
    JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withSchemaName("wanted").withProcedureName("decreaseDetailPriority");
    Logger.info("Executed");
    Map<String, Object> result = call.execute(params);
    Logger.info("Results: {}", Json.toJson(result));
    long rowCount = (long) result.get("updated_row_count");
    if ((rowCount > 0)) {
      return ok(Json.newObject().put("rowCount", rowCount));
    } else {
      DeleteError error = new DeleteError();
      error.error = "Delete failed";
      return badRequest(Json.toJson(error));
    }
  }

  public static Result markAs(Long wantedId, Long state) {
    // requires index on [detailId & steamId]
    // update wanted.details () where detailId = x and steamId = session()
    // set obtained = 1
    long rowCount = setItemState(wantedId, state);
    if ((rowCount > 0)) {
      return ok(Json.newObject().put("rowCount", rowCount));
    } else {
      DeleteError error = new DeleteError();
      error.error = "Delete failed";
      return badRequest(Json.toJson(error));
    }
  }

  private static long setItemState(Long wantedId, Long state) {
    long steamId = Long.parseLong(session("steamId"));
    Object[] params = new Object[] { steamId, wantedId, state };
    JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withSchemaName("wanted").withProcedureName("editState");
    Logger.info("Executed");
    Map<String, Object> result = call.execute(params);
    Logger.info("Results: {}", Json.toJson(result));
    long rowCount = (long) result.get("updated_row_count");
    return rowCount;
  }

}
