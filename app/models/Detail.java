package models;


import org.jongo.marshall.jackson.oid.ObjectId;

public class Detail {
    // [{"itemId":"768","wantedId":"102985","details":[{"detailId":"789017293","quality":"1","level":"5","tradable":"1","craftable":"1","craftnumber":"0","giftwrapped":"0","price":"3-5keys"}]}];

    /**
     *
     */
    //detailId associated with any changes to this item
    public String detailId;
    //these are all steam item attributes
    public Integer quality;
    public Integer levelNumber;
    public Integer isTradable;
    public Integer isCraftable;
    public Long craftNumber;
    public Integer isGiftWrapped;
    public String price;
    public Boolean isObtained;
    public Integer priority;

    /*@ManyToOne(targetEntity=Item.class)
    public Long wantedId;*/
}
