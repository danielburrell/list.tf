package uk.co.solong.tf2.playersummaries;

import java.util.HashMap;
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
"steamid",
"communityvisibilitystate",
"profilestate",
"personaname",
"lastlogoff",
"profileurl",
"avatar",
"avatarmedium",
"avatarfull",
"personastate",
"primaryclanid",
"timecreated",
"personastateflags",
"gameserverip",
"gameextrainfo",
"gameid",
"gameserversteamid"
})
public class Player {

@JsonProperty("steamid")
private String steamid;
@JsonProperty("communityvisibilitystate")
private Long communityvisibilitystate;
@JsonProperty("profilestate")
private Long profilestate;
@JsonProperty("personaname")
private String personaname;
@JsonProperty("lastlogoff")
private Long lastlogoff;
@JsonProperty("profileurl")
private String profileurl;
@JsonProperty("avatar")
private String avatar;
@JsonProperty("avatarmedium")
private String avatarmedium;
@JsonProperty("avatarfull")
private String avatarfull;
@JsonProperty("personastate")
private Long personastate;
@JsonProperty("primaryclanid")
private String primaryclanid;
@JsonProperty("timecreated")
private Long timecreated;
@JsonProperty("personastateflags")
private Long personastateflags;
@JsonProperty("gameserverip")
private String gameserverip;
@JsonProperty("gameextrainfo")
private String gameextrainfo;
@JsonProperty("gameid")
private String gameid;
@JsonProperty("gameserversteamid")
private String gameserversteamid;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("steamid")
public String getSteamid() {
return steamid;
}

@JsonProperty("steamid")
public void setSteamid(String steamid) {
this.steamid = steamid;
}

@JsonProperty("communityvisibilitystate")
public Long getCommunityvisibilitystate() {
return communityvisibilitystate;
}

@JsonProperty("communityvisibilitystate")
public void setCommunityvisibilitystate(Long communityvisibilitystate) {
this.communityvisibilitystate = communityvisibilitystate;
}

@JsonProperty("profilestate")
public Long getProfilestate() {
return profilestate;
}

@JsonProperty("profilestate")
public void setProfilestate(Long profilestate) {
this.profilestate = profilestate;
}

@JsonProperty("personaname")
public String getPersonaname() {
return personaname;
}

@JsonProperty("personaname")
public void setPersonaname(String personaname) {
this.personaname = personaname;
}

@JsonProperty("lastlogoff")
public Long getLastlogoff() {
return lastlogoff;
}

@JsonProperty("lastlogoff")
public void setLastlogoff(Long lastlogoff) {
this.lastlogoff = lastlogoff;
}

@JsonProperty("profileurl")
public String getProfileurl() {
return profileurl;
}

@JsonProperty("profileurl")
public void setProfileurl(String profileurl) {
this.profileurl = profileurl;
}

@JsonProperty("avatar")
public String getAvatar() {
return avatar;
}

@JsonProperty("avatar")
public void setAvatar(String avatar) {
this.avatar = avatar;
}

@JsonProperty("avatarmedium")
public String getAvatarmedium() {
return avatarmedium;
}

@JsonProperty("avatarmedium")
public void setAvatarmedium(String avatarmedium) {
this.avatarmedium = avatarmedium;
}

@JsonProperty("avatarfull")
public String getAvatarfull() {
return avatarfull;
}

@JsonProperty("avatarfull")
public void setAvatarfull(String avatarfull) {
this.avatarfull = avatarfull;
}

@JsonProperty("personastate")
public Long getPersonastate() {
return personastate;
}

@JsonProperty("personastate")
public void setPersonastate(Long personastate) {
this.personastate = personastate;
}

@JsonProperty("primaryclanid")
public String getPrimaryclanid() {
return primaryclanid;
}

@JsonProperty("primaryclanid")
public void setPrimaryclanid(String primaryclanid) {
this.primaryclanid = primaryclanid;
}

@JsonProperty("timecreated")
public Long getTimecreated() {
return timecreated;
}

@JsonProperty("timecreated")
public void setTimecreated(Long timecreated) {
this.timecreated = timecreated;
}

@JsonProperty("personastateflags")
public Long getPersonastateflags() {
return personastateflags;
}

@JsonProperty("personastateflags")
public void setPersonastateflags(Long personastateflags) {
this.personastateflags = personastateflags;
}

@JsonProperty("gameserverip")
public String getGameserverip() {
return gameserverip;
}

@JsonProperty("gameserverip")
public void setGameserverip(String gameserverip) {
this.gameserverip = gameserverip;
}

@JsonProperty("gameextrainfo")
public String getGameextrainfo() {
return gameextrainfo;
}

@JsonProperty("gameextrainfo")
public void setGameextrainfo(String gameextrainfo) {
this.gameextrainfo = gameextrainfo;
}

@JsonProperty("gameid")
public String getGameid() {
return gameid;
}

@JsonProperty("gameid")
public void setGameid(String gameid) {
this.gameid = gameid;
}

@JsonProperty("gameserversteamid")
public String getGameserversteamid() {
return gameserversteamid;
}

@JsonProperty("gameserversteamid")
public void setGameserversteamid(String gameserversteamid) {
this.gameserversteamid = gameserversteamid;
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
