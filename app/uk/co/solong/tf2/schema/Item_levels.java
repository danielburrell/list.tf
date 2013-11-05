
package uk.co.solong.tf2.schema;

import java.util.List;

public class Item_levels{
   	private List<Levels> levels;
   	private String name;

 	public List<Levels> getLevels(){
		return this.levels;
	}
	public void setLevels(List<Levels> levels){
		this.levels = levels;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
}
