
package uk.co.solong.tf2.playeritems;

import java.util.List;

public class Result{
   	private List<Items> items;
   	private Number num_backpack_slots;
   	private Number status;

 	public List<Items> getItems(){
		return this.items;
	}
	public void setItems(List<Items> items){
		this.items = items;
	}
 	public Number getNum_backpack_slots(){
		return this.num_backpack_slots;
	}
	public void setNum_backpack_slots(Number num_backpack_slots){
		this.num_backpack_slots = num_backpack_slots;
	}
 	public Number getStatus(){
		return this.status;
	}
	public void setStatus(Number status){
		this.status = status;
	}
}
