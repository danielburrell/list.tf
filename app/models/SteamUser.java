package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
@Table
public class SteamUser extends Model{

    /**
     *
     */
    private static final long serialVersionUID = 3637338062178285436L;

    @Id
    public Long steamId;

    public Long schemaId;

    public String name;

    public String avatar;

    @OneToMany(cascade=CascadeType.ALL)
    public List<Item> item;

    public static Finder<Long,SteamUser> find = new Finder<Long,SteamUser>(
            Long.class, SteamUser.class
          );
}
