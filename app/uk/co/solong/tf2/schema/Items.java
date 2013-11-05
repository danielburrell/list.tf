
package uk.co.solong.tf2.schema;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Items{
       private Capabilities capabilities;
       private String craft_class;
       private String craft_material_type;
       private Long defindex;
       private String image_inventory;
       private String image_url;
       private String image_url_large;
       private String item_class;
       private String item_name;
       private Number item_quality;
       private String item_slot;
       private String item_type_name;
       private Number max_ilevel;
       private Number min_ilevel;
       private String model_player;
       private String name;
       private boolean proper_name;
       private List<String> used_by_classes;

     public Capabilities getCapabilities(){
        return this.capabilities;
    }
    public void setCapabilities(Capabilities capabilities){
        this.capabilities = capabilities;
    }
     public String getCraft_class(){
        return this.craft_class;
    }
    public void setCraft_class(String craft_class){
        this.craft_class = craft_class;
    }
     public String getCraft_material_type(){
        return this.craft_material_type;
    }
    public void setCraft_material_type(String craft_material_type){
        this.craft_material_type = craft_material_type;
    }
     public Long getDefindex(){
        return this.defindex;
    }
    public void setDefindex(Long defindex){
        this.defindex = defindex;
    }
     public String getImage_inventory(){
        return this.image_inventory;
    }
    public void setImage_inventory(String image_inventory){
        this.image_inventory = image_inventory;
    }
     public String getImage_url(){
        return this.image_url;
    }
    public void setImage_url(String image_url){
        this.image_url = image_url;
    }
     public String getImage_url_large(){
        return this.image_url_large;
    }
    public void setImage_url_large(String image_url_large){
        this.image_url_large = image_url_large;
    }
     public String getItem_class(){
        return this.item_class;
    }
    public void setItem_class(String item_class){
        this.item_class = item_class;
    }
     public String getItem_name(){
        return this.item_name;
    }
    public void setItem_name(String item_name){
        this.item_name = item_name;
    }
     public Number getItem_quality(){
        return this.item_quality;
    }
    public void setItem_quality(Number item_quality){
        this.item_quality = item_quality;
    }
     public String getItem_slot(){
        return this.item_slot;
    }
    public void setItem_slot(String item_slot){
        this.item_slot = item_slot;
    }
     public String getItem_type_name(){
        return this.item_type_name;
    }
    public void setItem_type_name(String item_type_name){
        this.item_type_name = item_type_name;
    }
     public Number getMax_ilevel(){
        return this.max_ilevel;
    }
    public void setMax_ilevel(Number max_ilevel){
        this.max_ilevel = max_ilevel;
    }
     public Number getMin_ilevel(){
        return this.min_ilevel;
    }
    public void setMin_ilevel(Number min_ilevel){
        this.min_ilevel = min_ilevel;
    }
     public String getModel_player(){
        return this.model_player;
    }
    public void setModel_player(String model_player){
        this.model_player = model_player;
    }
     public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
     public boolean getProper_name(){
        return this.proper_name;
    }
    public void setProper_name(boolean proper_name){
        this.proper_name = proper_name;
    }
     public List<String> getUsed_by_classes(){
        return this.used_by_classes;
    }
    public void setUsed_by_classes(List<String> used_by_classes){
        this.used_by_classes = used_by_classes;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((defindex == null) ? 0 : defindex.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Items other = (Items) obj;
        if (defindex == null) {
            if (other.defindex != null)
                return false;
        } else if (!defindex.equals(other.defindex))
            return false;
        return true;
    }


}
