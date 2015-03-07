package edu.ucsc.soe.ccs.rememberthattime.ail.rummySpecificAIL;


import java.time.LocalTime;

import edu.ucsc.soe.ccs.rememberthattime.ail.AILCharacter;
import edu.ucsc.soe.ccs.rememberthattime.ail.SpeechAILInstance;
import edu.ucsc.soe.ccs.rememberthattime.ail.meta.SpeechActType;

public class ResponseEvent extends SpeechAILInstance {

	public ResponseEvent(AILCharacter subject, AILCharacter object, String content,
			LocalTime time, SpeechActType speechActType) {
		super(subject, object, content, time, speechActType);
	}

}
