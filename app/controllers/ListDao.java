package controllers;

import models.Item;
import models.SteamUser;

import org.jongo.MongoCollection;

import uk.co.solong.tf2.schema.Schema;

import com.mongodb.WriteResult;

public class ListDao {
    private MongoCollection userCollection;
    private MongoCollection schemaCollection;

    public MongoCollection getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(MongoCollection userCollection) {
        this.userCollection = userCollection;
    }

    public MongoCollection getSchemaCollection() {
        return schemaCollection;
    }

    public void setSchemaCollection(MongoCollection schemaCollection) {
        this.schemaCollection = schemaCollection;
    }

    public WriteResult saveSteamUser(SteamUser s) {
        return userCollection.save(s);
    }

    public SteamUser findUser(Long steamId) {
        return userCollection.findOne("{_id:#}", steamId).as(SteamUser.class);
    }

    public WriteResult saveDetail(long steamId, Item item) {
        return userCollection.update("{_id:#, item.wantedId:#}", steamId, item.wantedId).with("{$push:{item.$.details:#}}", item.details.get(0));
    }

    public WriteResult setItemState(long steamId, String wantedId, Long state) {
        return userCollection.update("{_id:#, item.wantedId:#}", steamId, wantedId).with("{$set:{item.$.state:#}}", state);
    }

    public WriteResult setDetailObtained(long steamId, String wantedId, Item item) {
        return userCollection.update("{_id:#, item.wantedId:#}", steamId, wantedId).with("{$set:{item.$.details:#}}", item.details);
    }

    public WriteResult setPriority(long steamId, String wantedId, Item item) {
        return userCollection.update("{_id:#, item.wantedId:#}", steamId, wantedId).with("{$set:{item.$.details:#}}", item.details);
    }

    public WriteResult deleteDetail(long steamId, String wantedId, Item item) {
        return userCollection.update("{_id:#, item.wantedId:#}", steamId, wantedId).with("{$set:{item.$.details:#}}", item.details);
    }

    public Schema getLatestSchema() {
        return schemaCollection.findOne("").orderBy("{schemaVersion:-1}").as(Schema.class);
    }

    public WriteResult createUser(SteamUser user) {
        return userCollection.insert(user);
    }
}
