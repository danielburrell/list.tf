
package uk.co.solong.tf2.schema;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attributes{
       private String attribute_class;
       private Number defindex;
       private String description_format;
       private String description_string;
       private String effect_type;
       private boolean hidden;
       private String name;
       private boolean stored_as_integer;

     public String getAttribute_class(){
        return this.attribute_class;
    }
    public void setAttribute_class(String attribute_class){
        this.attribute_class = attribute_class;
    }
     public Number getDefindex(){
        return this.defindex;
    }
    public void setDefindex(Number defindex){
        this.defindex = defindex;
    }
     public String getDescription_format(){
        return this.description_format;
    }
    public void setDescription_format(String description_format){
        this.description_format = description_format;
    }
     public String getDescription_string(){
        return this.description_string;
    }
    public void setDescription_string(String description_string){
        this.description_string = description_string;
    }
     public String getEffect_type(){
        return this.effect_type;
    }
    public void setEffect_type(String effect_type){
        this.effect_type = effect_type;
    }
     public boolean getHidden(){
        return this.hidden;
    }
    public void setHidden(boolean hidden){
        this.hidden = hidden;
    }
     public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
     public boolean getStored_as_integer(){
        return this.stored_as_integer;
    }
    public void setStored_as_integer(boolean stored_as_integer){
        this.stored_as_integer = stored_as_integer;
    }
}
