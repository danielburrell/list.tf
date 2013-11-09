package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import play.db.DB;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.OpenID;
import play.libs.OpenID.UserInfo;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;

public class Application extends Controller {

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
    return ok();
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
            return ok(index.render(""));

          case 1:
            return ok(index.render(""));// "Oh hey, you're back! New items have been released since you were last here. Go! Go! Go!"

          case 2:
            return ok(index.render("")); // "Oh hey, you're back! No new items have been released since you were last here."

          default:
            return ok("What is this I don't even..");
          }

        } else {
          return ok("Doesn't look like logging in worked. Maybe try again?");
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

  public static Result login() {

    try {
      OpenIdManager manager = new OpenIdManager();
      manager.setReturnTo("http://localhost:8010/openIDCallback");
      manager.setRealm("http://localhost:8010/");

      Endpoint endpoint = manager.lookupEndpoint("http://steamcommunity.com/openid");
      Association association = manager.lookupAssociation(endpoint);
      String url = manager.getAuthenticationUrl(endpoint, association);
      return redirect(url);
    } catch (Exception e) {
      return ok("Steam community is down :(");
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
    JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());
    Object[] params = new Object[] { steamId };
    List<Map<String, Object>> results = jdbcTemplate.queryForList("CALL `wanted`.`getWantedList`(?)", params);
    SteamUser s = new SteamUser();
    s.steamId = steamId;
    s.item = new LinkedList<Item>();
    Long currentWantedId = null;
    Item currentItem = null;
    for (Map<String, Object> row : results) {
      Long wantedId = (Long) row.get("wanted_id");
      if (wantedId != currentWantedId) {
        Item item = new Item();
        item.wantedId = wantedId;
        item.itemId = (Long) row.get("item_id");
        item.name = (String) row.get("item_name");
        item.image = (String) row.get("item_image");
        item.state = (Integer) row.get("state");
        item.details = new LinkedList<Detail>();
        currentWantedId = wantedId;
        s.item.add(item);
        currentItem = item;
      }

      Long detailId = (Long) row.get("detail_id");
      if (detailId != null) {
        Detail detail = new Detail();
        detail.detailId = detailId;
        detail.craftNumber = (Long) row.get("craft_number");
        detail.isCraftable = (Integer) row.get("is_craftable");
        detail.isGiftWrapped = (Integer) row.get("is_gift_wrapped");
        detail.isObtained = (Boolean) row.get("is_obtained");
        detail.isTradable = (Integer) row.get("is_tradable");
        detail.levelNumber = (Integer) row.get("level_number");
        detail.price = (String) row.get("price");
        detail.quality = (Integer) row.get("quality");
        currentItem.details.add(detail);
      }

    }

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

  public static Result wantedPartial() {
    return TODO;
  }

  @BodyParser.Of(BodyParser.Json.class)
  public static Result addDetail(Long wantedId) {
    JsonNode json = request().body().asJson();// (Detail.class);
    Detail detail = Json.fromJson(json, Detail.class);
    Logger.info("Validating..");
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

  public static Result markAs(Long detailId, Long state) {
    // requires index on [detailId & steamId]
    // update wanted.details () where detailId = x and steamId = session()
    // set obtained = 1
    long steamId = Long.parseLong(session("steamId"));
    Object[] params = new Object[] { steamId, detailId, state };
    JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());
    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate).withSchemaName("wanted").withProcedureName("editState");
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

  public static Result markAsUnobtained(Long detailId) {
    // requires index on [detailId & steamId]
    // update wanted.details () where detailId = x and steamId = session()
    // set obtained = 0
    return TODO;
  }
}
