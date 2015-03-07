package edu.ucsc.soe.ccs.rememberthattime.ail;

import java.time.LocalTime;

public abstract class AILInstance {
	
	AILSubject subject;
	AILObject object;
	protected LocalTime time;
	
	public AILInstance(AILSubject subject, AILObject object, LocalTime time) {
		this.subject = subject;
		this.object = object;
		this.time = time;
	}

	public AILSubject getSubject() {
		return subject;
	}

	public AILObject getObject() {
		return object;
	}

	public LocalTime getTime() {
		return time;
	}
	
	public abstract String getVerb();
	
	public abstract String getAILString();
	
}
