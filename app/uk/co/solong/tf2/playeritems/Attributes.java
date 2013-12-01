
package uk.co.solong.tf2.playeritems;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attributes{
     private Number defindex;
     private Number float_value;
     private String value;

   public Number getDefindex(){
    return this.defindex;
  }
  public void setDefindex(Number defindex){
    this.defindex = defindex;
  }
   public Number getFloat_value(){
    return this.float_value;
  }
  public void setFloat_value(Number float_value){
    this.float_value = float_value;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }

}
