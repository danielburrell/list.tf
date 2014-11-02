package globals;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.libs.Akka;
import play.libs.F.Function;

import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import scala.concurrent.duration.Duration;
import uk.co.solong.tf2.schema.Items;
import uk.co.solong.tf2.schema.Result;
import uk.co.solong.tf2.schema.Schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

public class Global extends GlobalSettings {
    private static Jongo jongo;
    private static MongoCollection schemaCollection;
    private static String schemaUrl;
    private static String schemaKey;
    private static String language;

    @Override
    public void beforeStart(Application app) {
        // Initialise configuration
        super.beforeStart(app);
        try {
            DB db = new MongoClient("127.0.0.1",27017).getDB("list");
            jongo = new Jongo(db);
            schemaCollection = jongo.getCollection("schema").withWriteConcern(WriteConcern.ACKNOWLEDGED);
            
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Jongo jongo = new Jongo(db);

        schemaUrl = Play.application().configuration().getString("schemaUrl");
        schemaKey = Play.application().configuration().getString("apiKey");
        language = Play.application().configuration().getString("language");
    }

    // TODO: schedule http://backpack.tf/api/IGetPrices/v3/?format=json&key=?
    @Override
    public void onStart(Application app) {
        Logger.info("Scheduling schema watcher");
        Akka.system().scheduler().schedule(Duration.create(0, TimeUnit.MILLISECONDS), Duration.create(1, TimeUnit.HOURS),

        new Runnable() {

            public void run() {
                WS.url(schemaUrl).setQueryParameter("key", schemaKey).setQueryParameter("language", language).get().map(new Function<WSResponse, Result>() {

                    public Result apply(WSResponse response) {
                        try {
                            Logger.info("Fetching schema data");
                            InputStream input = response.getBodyAsStream();
                            Reader streamReader = new InputStreamReader(input, "UTF-8");
                            ObjectMapper m = new ObjectMapper();
                            ObjectReader reader = m.reader(Schema.class);
                            Schema remoteSchema = reader.readValue(streamReader);
                            streamReader.close();
                            Schema localSchema = getLocalSchema();
                            List<Items> remoteSchemaItems = remoteSchema.getResult().getItems();
                            List<Items> localSchemaItems = localSchema.getResult().getItems();
                            // get the difference in the items.
                            remoteSchemaItems.removeAll(localSchemaItems);
                            if (remoteSchemaItems.size() > 0) {
                                Logger.info("Schema change detected. {} items to add.", remoteSchemaItems.size());
                                remoteSchema.setSchemaVersion(DateTime.now().getMillis());
                                schemaCollection.insert(remoteSchema);
                                Logger.info("Schema check complete. Schema was changed.");
                            } else {
                                Logger.info("Schema check complete. Schema was not changed.");
                            }
                            return null;
                        } catch (Throwable e) {
                            Logger.error("Unexpected exception caught: {}",e);
                        }
                        return null;
                    }

                    private Schema getLocalSchema() {
                        Logger.info("Fetching known items");
                        // get the latest schema.
                        Schema c = schemaCollection.findOne("").orderBy("{schemaVersion:-1}").as(Schema.class);
                        if (c == null) {
                            Logger.warn("LocalSchema was null. Only acceptable on first run");
                            Schema s = new Schema();
                            Result result = new Result();
                            result.setItems(new ArrayList<Items>());
                            s.setResult(result);
                            return s;
                        } else {
                        
                        return c;
                        }
                        
                    }

                });
            }
        },

        Akka.system().dispatcher()

        );

    }
}
