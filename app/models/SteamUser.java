package models;

import java.util.List;

import org.jongo.marshall.jackson.oid.Id;

public class SteamUser {

    // Id representing the user
    @Id
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
