package edu.ucsc.soe.ccs.rememberthattime.ail;

import edu.ucsc.soe.ccs.rememberthattime.ail.meta.CharacterType;
import edu.ucsc.soe.ccs.rememberthattime.ail.meta.Gender;


public class AILCharacter {
	
	String name;
	CharacterType type;
	Gender gender;
	
	public AILCharacter(String name, CharacterType type, Gender gender){
		this.name = name;
		this.type = type; 
		this.gender = gender;
	}
	
}
