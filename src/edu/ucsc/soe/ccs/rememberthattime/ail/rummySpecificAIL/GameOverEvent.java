package edu.ucsc.soe.ccs.rememberthattime.ail.rummySpecificAIL;

import java.time.LocalTime;

import edu.ucsc.soe.ccs.rememberthattime.ail.AILCharacter;
import edu.ucsc.soe.ccs.rememberthattime.ail.ContextSpecificAILInstance;

public class GameOverEvent extends ContextSpecificAILInstance {

	public GameOverEvent(AILCharacter subject, AILCharacter object,
			LocalTime time, String context) {
		super(subject, object, time, context);
	}

}
