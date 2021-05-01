package hu.emraxxor.fstack.demo.core.data.es;

import lombok.Data;

/**
 * 
 * @author Attila Barna
 *
 */
@Data
public class ESDefaultJoinRelation {
	
	private String name;
	
	private String parent;
	
	public ESDefaultJoinRelation() {
		// TODO Auto-generated constructor stub
	}
	
	public ESDefaultJoinRelation(String name, String parent) {
		this.name = name;
		this.parent = parent;
	}
	
	
}
