
package uk.co.solong.tf2.schema;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Qualities{
       private Number Normal;
       private Number unique;
       private Number community;
       private Number completed;
       private Number customized;
       private Number developer;
       private Number haunted;
       private Number rarity1;
       private Number rarity2;
       private Number rarity3;
       private Number rarity4;
       private Number selfmade;
       private Number strange;
       private Number vintage;

     public Number getNormal(){
        return this.Normal;
    }
    public void setNormal(Number normal){
        this.Normal = normal;
    }
     public Number getUnique(){
        return this.unique;
    }
    public void setUnique(Number unique){
        this.unique = unique;
    }
     public Number getCommunity(){
        return this.community;
    }
    public void setCommunity(Number community){
        this.community = community;
    }
     public Number getCompleted(){
        return this.completed;
    }
    public void setCompleted(Number completed){
        this.completed = completed;
    }
     public Number getCustomized(){
        return this.customized;
    }
    public void setCustomized(Number customized){
        this.customized = customized;
    }
     public Number getDeveloper(){
        return this.developer;
    }
    public void setDeveloper(Number developer){
        this.developer = developer;
    }
     public Number getHaunted(){
        return this.haunted;
    }
    public void setHaunted(Number haunted){
        this.haunted = haunted;
    }
     public Number getRarity1(){
        return this.rarity1;
    }
    public void setRarity1(Number rarity1){
        this.rarity1 = rarity1;
    }
     public Number getRarity2(){
        return this.rarity2;
    }
    public void setRarity2(Number rarity2){
        this.rarity2 = rarity2;
    }
     public Number getRarity3(){
        return this.rarity3;
    }
    public void setRarity3(Number rarity3){
        this.rarity3 = rarity3;
    }
     public Number getRarity4(){
        return this.rarity4;
    }
    public void setRarity4(Number rarity4){
        this.rarity4 = rarity4;
    }
     public Number getSelfmade(){
        return this.selfmade;
    }
    public void setSelfmade(Number selfmade){
        this.selfmade = selfmade;
    }
     public Number getStrange(){
        return this.strange;
    }
    public void setStrange(Number strange){
        this.strange = strange;
    }
     public Number getVintage(){
        return this.vintage;
    }
    public void setVintage(Number vintage){
        this.vintage = vintage;
    }
}
