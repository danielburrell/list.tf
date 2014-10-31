package models;

import java.util.List;

public class SteamUser {

    // Id representing the user
    public Long steamId;

    // version of the schema that this user is currently running on
    public Long schemaId;

    // display name of this user
    public String name;

    // display avatar of this user (http url)
    public String avatar;

    // list of items this user wants
    public List<Item> item;

}
