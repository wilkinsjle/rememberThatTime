package edu.ucsc.soe.ccs.rememberthattime.ail;


public class AILProperty implements AILObject, AILSubject{

	String name;

	public AILProperty(String name){
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
