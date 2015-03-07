package edu.ucsc.soe.ccs.rememberthattime.ail;

import java.time.LocalTime;


public class ContextSpecificAILInstance extends AILInstance {

	String context;

	public ContextSpecificAILInstance(AILCharacter subject,
			AILCharacter object, LocalTime time, String context) {
		super(subject, object, time);
		this.context = context;
	}

}
