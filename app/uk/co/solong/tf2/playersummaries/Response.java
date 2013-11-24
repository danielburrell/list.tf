package uk.co.solong.tf2.playersummaries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
"players"
})
public class Response {

@JsonProperty("players")
private List<Player> players = new ArrayList<Player>();
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("players")
public List<Player> getPlayers() {
return players;
}

@JsonProperty("players")
public void setPlayers(List<Player> players) {
this.players = players;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperties(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
