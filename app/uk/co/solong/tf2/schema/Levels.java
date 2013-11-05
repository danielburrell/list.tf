
package uk.co.solong.tf2.schema;

import java.util.List;

public class Levels{
   	private Number level;
   	private String name;
   	private Number required_score;

 	public Number getLevel(){
		return this.level;
	}
	public void setLevel(Number level){
		this.level = level;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public Number getRequired_score(){
		return this.required_score;
	}
	public void setRequired_score(Number required_score){
		this.required_score = required_score;
	}
}
