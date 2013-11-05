
package uk.co.solong.tf2.schema;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Capabilities{
       private boolean can_be_restored;
       private boolean can_card_upgrade;
       private boolean can_consume;
       private boolean can_craft_mark;
       private boolean can_killstreakify;
       private boolean can_strangify;
       private boolean nameable;
       private boolean strange_parts;

     public boolean getCan_be_restored(){
        return this.can_be_restored;
    }
    public void setCan_be_restored(boolean can_be_restored){
        this.can_be_restored = can_be_restored;
    }
     public boolean getCan_card_upgrade(){
        return this.can_card_upgrade;
    }
    public void setCan_card_upgrade(boolean can_card_upgrade){
        this.can_card_upgrade = can_card_upgrade;
    }
     public boolean getCan_consume(){
        return this.can_consume;
    }
    public void setCan_consume(boolean can_consume){
        this.can_consume = can_consume;
    }
     public boolean getCan_craft_mark(){
        return this.can_craft_mark;
    }
    public void setCan_craft_mark(boolean can_craft_mark){
        this.can_craft_mark = can_craft_mark;
    }
     public boolean getCan_killstreakify(){
        return this.can_killstreakify;
    }
    public void setCan_killstreakify(boolean can_killstreakify){
        this.can_killstreakify = can_killstreakify;
    }
     public boolean getCan_strangify(){
        return this.can_strangify;
    }
    public void setCan_strangify(boolean can_strangify){
        this.can_strangify = can_strangify;
    }
     public boolean getNameable(){
        return this.nameable;
    }
    public void setNameable(boolean nameable){
        this.nameable = nameable;
    }
     public boolean getStrange_parts(){
        return this.strange_parts;
    }
    public void setStrange_parts(boolean strange_parts){
        this.strange_parts = strange_parts;
    }
}
