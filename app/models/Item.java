package models;

import java.util.List;

import org.bson.types.ObjectId;


public class Item {

    // [{"itemId":"768","details":[{"detailId":"789017293","quality":"1","level":"5","tradable":"1","craftable":"1","craftnumber":"0","giftwrapped":"0","price":"3-5keys"}]}];

    // id associated with any changes to this item

    
    public String wantedId;

    // state (.e.g wanted, unwanted etc)
    public int state;

    // the steam defindex of the item that's wanted
    public Long itemId;

    // the friendly name of the item
    public String name;

    // the image associated with the item
    public String image;

    // the list of detailed specifications for the item

    public List<Detail> details;

    // effective priority of the item (i.e. max([details.priority]))
    public int effectivePriority;
}
