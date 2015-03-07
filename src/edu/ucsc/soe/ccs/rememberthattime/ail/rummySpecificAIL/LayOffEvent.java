package edu.ucsc.soe.ccs.rememberthattime.ail.rummyspecificail;


import java.time.LocalTime;

import edu.ucsc.soe.ccs.rememberthattime.ail.AILObject;
import edu.ucsc.soe.ccs.rememberthattime.ail.AILSubject;
import edu.ucsc.soe.ccs.rememberthattime.ail.ContextSpecificAILInstance;

public class LayOffEvent extends ContextSpecificAILInstance {

	final String verb = "lay off";

	public LayOffEvent(AILSubject subject, AILObject object, LocalTime time,
			String context) {
		super(subject, object, time, context);
	}

	@Override
	public String getVerb() { return verb; }

	@Override
	public String getAILString() {
		return "LayOffEventAILI(" 
				+ (this.getSubject() != null ? this.getSubject().getName() : "null") + ", " 
				+ (this.getObject() != null ? this.getObject().getName() : "null") + ", " 
				+ "null" + ", "
				+ "null "+ ", "
				+ "rummy" + ", "
				+ this.time.toString() 
				+ ")";
	}

}
