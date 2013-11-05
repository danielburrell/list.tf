
package uk.co.solong.tf2.schema;

import java.util.List;

public class Item_sets{
   	private String item_set;
   	private List<Items> items;
   	private String name;

 	public String getItem_set(){
		return this.item_set;
	}
	public void setItem_set(String item_set){
		this.item_set = item_set;
	}
 	public List<Items> getItems(){
		return this.items;
	}
	public void setItems(List<Items> items){
		this.items = items;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
}
