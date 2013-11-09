package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table
public class Item extends Model {

    private static final long serialVersionUID = 1L;
    // [{"itemId":"768","details":[{"detailId":"789017293","quality":"1","level":"5","tradable":"1","craftable":"1","craftnumber":"0","giftwrapped":"0","price":"3-5keys"}]}];

    @Id
    public Long wantedId;

    public int state;

    /*@ManyToOne(targetEntity=SteamUser.class)
    public long steamId;*/

    public Long itemId;

    public String name;

    public String image;

    @OneToMany(cascade=CascadeType.ALL)
    public List<Detail> details;

}
