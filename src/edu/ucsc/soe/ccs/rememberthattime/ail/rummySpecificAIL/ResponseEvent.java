package edu.ucsc.soe.ccs.rememberthattime.ail.rummyspecificail;


import java.time.LocalTime;

import edu.ucsc.soe.ccs.rememberthattime.ail.AILObject;
import edu.ucsc.soe.ccs.rememberthattime.ail.AILSubject;
import edu.ucsc.soe.ccs.rememberthattime.ail.SpeechAILInstance;
import edu.ucsc.soe.ccs.rememberthattime.ail.meta.SpeechActType;

public class ResponseEvent extends SpeechAILInstance {

	final String verb = "respond to";

	public ResponseEvent(AILSubject subject, AILObject object, String content,
			LocalTime time, SpeechActType speechActType) {
		super(subject, object, content, time, speechActType);
	}

	@Override
	public String getVerb() { return verb; }

	@Override
	public String getAILString() {
		return "ResponseAILI(" 
				+ (this.getSubject() != null ? this.getSubject().getName() : "null") + ", " 
				+ (this.getObject() != null ? this.getObject().getName() : "null") + ", " 
				+ this.speechActType.name() + ", "
				+ this.content + ", "
				+ "rummy" + ", "
				+ this.time.toString() 
				+ ")";
	}

}
