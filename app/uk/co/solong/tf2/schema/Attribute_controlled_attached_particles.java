
package uk.co.solong.tf2.schema;

import java.util.List;

public class Attribute_controlled_attached_particles{
   	private boolean attach_to_rootbone;
   	private Number id;
   	private String name;
   	private String system;

 	public boolean getAttach_to_rootbone(){
		return this.attach_to_rootbone;
	}
	public void setAttach_to_rootbone(boolean attach_to_rootbone){
		this.attach_to_rootbone = attach_to_rootbone;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public String getSystem(){
		return this.system;
	}
	public void setSystem(String system){
		this.system = system;
	}
}
