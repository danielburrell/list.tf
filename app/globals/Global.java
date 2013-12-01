package globals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.db.DB;
import play.libs.Akka;
import play.libs.F.Function;
import play.libs.WS;
import play.mvc.Result;
import scala.concurrent.duration.Duration;
import uk.co.solong.tf2.schema.Items;
import uk.co.solong.tf2.schema.Schema;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Global extends GlobalSettings {
  private static String schemaUrl;
  private static String schemaKey;
  private static String language;
  private static int schemaNameMaxLength;
  @Override
  public void beforeStart(Application app) {
    // Initialise configuration
    super.beforeStart(app);

    schemaUrl = Play.application().configuration().getString("schemaUrl");
    schemaKey = Play.application().configuration().getString("apiKey");
    language = Play.application().configuration().getString("language");
    schemaNameMaxLength = Integer.parseInt(Play.application().configuration().getString("schemaNameMaxLength"));
  }

  @Override
  public void onStart(Application app) {
    Logger.info("Scheduling schema watcher");
    Akka.system().scheduler().schedule(Duration.create(0, TimeUnit.MILLISECONDS), Duration.create(1, TimeUnit.HOURS),

    new Runnable() {

      private JdbcTemplate jdbcTemplate = new JdbcTemplate(DB.getDataSource());

      public void run() {
        WS.url(schemaUrl).setQueryParameter("key", schemaKey).setQueryParameter("language", language).get().map(new Function<WS.Response, Result>() {


          public Result apply(WS.Response response) {
            try {

              Logger.info("Fetching schema data");
              String node = response.getBody();
              ObjectMapper om = new ObjectMapper();
              Schema s;

              s = om.readValue(node, Schema.class);

              List<Items> schemaItems = s.getResult().getItems();
              List<Items> knownItems = getKnownItems();
              // get the difference in the items.
              schemaItems.removeAll(knownItems);
              if (schemaItems.size() > 0) {
                Logger.info("Schema change detected");
                {// TODO:begin transaction
                  StringBuilder sb = new StringBuilder();
                  for (Items item : schemaItems) {
                    addItemToSchema(item);
                    sb.append(item.getItem_name()).append(", ").append(item.getDefindex()).append("\n");
                  }
                  Logger.info("Items added:\n{}", sb.toString());
                }
                // finally update the schemaId
                updateSchemaVersionNumber();
                // TODO: evict the schema from
                // the
                // cache
                Logger.info("Schema check complete. Schema was changed.");
              } else {
                Logger.info("Schema check complete. Schema was not changed.");
              }
              return null;
            } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            return null;
          }

          private void updateSchemaVersionNumber() {
            Logger.info("Updating schema version");
            Object[] params = new Object[] { new java.sql.Timestamp(new java.util.Date().getTime()) };
            jdbcTemplate.update("CALL `wanted`.`addSchemaVersion`(?)", params);
          }

          private void addItemToSchema(Items item) {
              item.setItem_name(item.getItem_name().replaceAll("[\u0000-\u001f]", ""));
              item.setItem_name(item.getItem_name().substring(0, Math.min(item.getItem_name().length(),schemaNameMaxLength)));
              Object[] params = new Object[] { item.getDefindex(), item.getItem_name(), item.getImage_url() };
              jdbcTemplate.update("CALL `wanted`.`addSchemaItem`(?, ?, ?)", params);

          }

          private List<Items> getKnownItems() {
            Logger.info("Fetching known items");
            List<Items> result = jdbcTemplate.query("CALL `wanted`.`getSchema`()", new RowMapper<Items>() {
              @Override
              public Items mapRow(ResultSet rs, int row) throws SQLException {
                Items item = new Items();
                item.setDefindex(rs.getLong("item_id"));
                item.setName(rs.getString("item_name"));
                return item;
              }
            });
            return result;
          }

        });
      }
    },

    Akka.system().dispatcher()

    );

  }
}
