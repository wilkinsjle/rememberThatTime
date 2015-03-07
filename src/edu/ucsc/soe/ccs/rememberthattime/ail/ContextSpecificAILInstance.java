package edu.ucsc.soe.ccs.rememberthattime.ail;

import java.time.LocalTime;


public abstract class ContextSpecificAILInstance extends AILInstance {

	String context;

	public ContextSpecificAILInstance(AILSubject subject,
			AILObject object, LocalTime time, String context) {
		super(subject, object, time);
		this.context = context;
	}
	
}
