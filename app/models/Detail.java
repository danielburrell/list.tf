package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table
public class Detail extends Model{
    // [{"itemId":"768","wantedId":"102985","details":[{"detailId":"789017293","quality":"1","level":"5","tradable":"1","craftable":"1","craftnumber":"0","giftwrapped":"0","price":"3-5keys"}]}];

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    public Long detailId;
    public Integer quality;
    public Integer levelNumber;
    public Boolean isTradable;
    public Boolean isCraftable;
    public Long craftNumber;
    public Boolean isGiftWrapped;
    public String price;
    public Boolean isObtained;
    public Integer priority;

    /*@ManyToOne(targetEntity=Item.class)
    public Long wantedId;*/
}
