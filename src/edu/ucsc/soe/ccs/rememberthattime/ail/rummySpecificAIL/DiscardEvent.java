package edu.ucsc.soe.ccs.rememberthattime.ail.rummyspecificail;


import java.time.LocalTime;

import edu.ucsc.soe.ccs.rememberthattime.ail.AILObject;
import edu.ucsc.soe.ccs.rememberthattime.ail.AILSubject;
import edu.ucsc.soe.ccs.rememberthattime.ail.ContextSpecificAILInstance;

public class DiscardEvent extends ContextSpecificAILInstance {

	final String verb = "discard";

	public DiscardEvent(AILSubject subject, AILObject object, LocalTime time,
			String context) {
		super(subject, object, time, context);
	}

	@Override
	public String getVerb() { return verb; }

	@Override
	public String getAILString() {
		return "DiscardEventAILI(" 
				+ this.getSubject().getName() + ", " 
				+ this.getObject().getName() + ", " 
				+ "null" + ", "
				+ "null "+ ", "
				+ "rummy" + ", "
				+ this.time.toString() 
				+ ")";
	}

}
