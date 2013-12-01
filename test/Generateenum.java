import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import play.Logger;
import play.Play;
import play.libs.WS;
import play.libs.F.Function;
import play.mvc.Result;
import uk.co.solong.tf2.schema.Items;
import uk.co.solong.tf2.schema.Schema;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Generateenum {

  private static String schemaUrl;
  private static String schemaKey;
  private static String language;
  private static int schemaNameMaxLength;

  @Test
  public void test() throws JsonParseException, JsonMappingException, IOException {
    schemaUrl = "http://api.steampowered.com/IEconItems_440/GetSchema/v0001/?key=?&language=?";
    schemaKey = "2B5FE11A92455CD15647AC98AB4BF693";
    language = "en_US";
    schemaNameMaxLength = 150;

    RestTemplate t = new RestTemplate();
    String node = t.getForObject("http://api.steampowered.com/IEconItems_440/GetSchema/v0001/?key=2B5FE11A92455CD15647AC98AB4BF693&language=en_US",
        String.class);

    Logger.info("Fetching schema data");

    ObjectMapper om = new ObjectMapper();
    Map<Long, String> map = new TreeMap<Long, String>();
    Schema s = om.readValue(node, Schema.class);
    List<Items> items = s.getResult().getItems();
    for (Items item : items) {
      map.put(item.getDefindex(), item.getItem_name());
    }

    String output = om.writeValueAsString(map);
    System.out.println(output);

  }

}
