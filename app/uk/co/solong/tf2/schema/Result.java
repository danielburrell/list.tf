
package uk.co.solong.tf2.schema;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result{
       //private List<Attribute_controlled_attached_particles> attribute_controlled_attached_particles;
       private List<Attributes> attributes;
       private List<Item_levels> item_levels;
       //private List<Item_sets> item_sets;
       private List<Items> items;
       private String items_game_url;
       //private List<Kill_eater_ranks> kill_eater_ranks;
       //private List<Kill_eater_score_types> kill_eater_score_types;
       private List<OriginNames> originNames;
       private Qualities qualities;
       private Number status;
       private List<String_lookups> string_lookups;

    /* public List<Attribute_controlled_attached_particles> getAttribute_controlled_attached_particles(){
        return this.attribute_controlled_attached_particles;
    }
    public void setAttribute_controlled_attached_particles(List<Attribute_controlled_attached_particles> attribute_controlled_attached_particles){
        this.attribute_controlled_attached_particles = attribute_controlled_attached_particles;
    }*/
     public List<Attributes> getAttributes(){
        return this.attributes;
    }
    public void setAttributes(List<Attributes> attributes){
        this.attributes = attributes;
    }
     public List<Item_levels> getItem_levels(){
        return this.item_levels;
    }
    public void setItem_levels(List<Item_levels> item_levels){
        this.item_levels = item_levels;
    }
     /*public List<Item_sets> getItem_sets(){
        return this.item_sets;
    }
    public void setItem_sets(List<Item_sets> item_sets){
        this.item_sets = item_sets;
    }*/
     public List<Items> getItems(){
        return this.items;
    }
    public void setItems(List<Items> items){
        this.items = items;
    }
     public String getItems_game_url(){
        return this.items_game_url;
    }
    public void setItems_game_url(String items_game_url){
        this.items_game_url = items_game_url;
    }/*
     public List<Kill_eater_ranks> getKill_eater_ranks(){
        return this.kill_eater_ranks;
    }
    public void setKill_eater_ranks(List<Kill_eater_ranks> kill_eater_ranks){
        this.kill_eater_ranks = kill_eater_ranks;
    }
     public List<Kill_eater_score_types> getKill_eater_score_types(){
        return this.kill_eater_score_types;
    }
    public void setKill_eater_score_types(List<Kill_eater_score_types> kill_eater_score_types){
        this.kill_eater_score_types = kill_eater_score_types;
    }*/
     public List<OriginNames> getOriginNames(){
        return this.originNames;
    }
    public void setOriginNames(List<OriginNames> originNames){
        this.originNames = originNames;
    }
     public Qualities getQualities(){
        return this.qualities;
    }
    public void setQualities(Qualities qualities){
        this.qualities = qualities;
    }
     public Number getStatus(){
        return this.status;
    }
    public void setStatus(Number status){
        this.status = status;
    }
     public List<String_lookups> getString_lookups(){
        return this.string_lookups;
    }
    public void setString_lookups(List<String_lookups> string_lookups){
        this.string_lookups = string_lookups;
    }
}
