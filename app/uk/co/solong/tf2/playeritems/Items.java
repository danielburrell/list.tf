
package uk.co.solong.tf2.playeritems;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Items{
     private List<Attributes> attributes;
     private Number defindex;
     private boolean flag_cannot_trade;
     private boolean flag_cannot_craft;
     private Number id;
     private Number inventory;
     private Number level;
     private Number origin;
     private Number original_id;
     private Number quality;
     private Number quantity;

   public List<Attributes> getAttributes(){
    return this.attributes;
  }
  public void setAttributes(List<Attributes> attributes){
    this.attributes = attributes;
  }
   public Number getDefindex(){
    return this.defindex;
  }
  public void setDefindex(Number defindex){
    this.defindex = defindex;
  }
   public boolean getFlag_cannot_trade(){
    return this.flag_cannot_trade;
  }
  public void setFlag_cannot_trade(boolean flag_cannot_trade){
    this.flag_cannot_trade = flag_cannot_trade;
  }
   public Number getId(){
    return this.id;
  }
  public void setId(Number id){
    this.id = id;
  }
   public Number getInventory(){
    return this.inventory;
  }
  public void setInventory(Number inventory){
    this.inventory = inventory;
  }
   public Number getLevel(){
    return this.level;
  }
  public void setLevel(Number level){
    this.level = level;
  }
   public Number getOrigin(){
    return this.origin;
  }
  public void setOrigin(Number origin){
    this.origin = origin;
  }
   public Number getOriginal_id(){
    return this.original_id;
  }
  public void setOriginal_id(Number original_id){
    this.original_id = original_id;
  }
   public Number getQuality(){
    return this.quality;
  }
  public void setQuality(Number quality){
    this.quality = quality;
  }
   public Number getQuantity(){
    return this.quantity;
  }
  public void setQuantity(Number quantity){
    this.quantity = quantity;
  }
  public boolean getFlag_cannot_craft() {
    return flag_cannot_craft;
  }
  public void setFlag_cannot_craft(boolean flag_cannot_craft) {
    this.flag_cannot_craft = flag_cannot_craft;
  }
}
