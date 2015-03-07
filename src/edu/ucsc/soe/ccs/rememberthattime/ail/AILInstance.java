package edu.ucsc.soe.ccs.rememberthattime.ail;

import java.time.LocalTime;

public class AILInstance {
	
	AILCharacter subject;
	AILCharacter object;
	LocalTime time;
	
	public AILInstance(AILCharacter subject, AILCharacter object, LocalTime time) {
		this.subject = subject;
		this.object = object;
		this.time = time;
	}
	
}
