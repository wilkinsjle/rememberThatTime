package edu.ucsc.soe.ccs.rememberthattime.ail.rummyspecificail;

import java.time.LocalTime;

import edu.ucsc.soe.ccs.rememberthattime.ail.AILObject;
import edu.ucsc.soe.ccs.rememberthattime.ail.AILSubject;
import edu.ucsc.soe.ccs.rememberthattime.ail.ContextSpecificAILInstance;

public class GameOverEvent extends ContextSpecificAILInstance {

	final String verb = "end";
	
	public GameOverEvent(AILSubject subject, AILObject object,
			LocalTime time, String context) {
		super(subject, object, time, context);
	}

	@Override
	public String getVerb() { return verb; }

	@Override
	public String getAILString() {
		return "GameOverAILI(" 
				+ this.getSubject() != null ? this.getSubject().getName() : "null" + ", " 
				+ this.getObject() != null ? this.getObject().getName() : "null" + ", " 
				+ "null" + ", "
				+ "null "+ ", "
				+ "rummy" + ", "
				+ this.time.toString() 
				+ ")";
	}
	
}
