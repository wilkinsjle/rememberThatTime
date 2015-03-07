package edu.ucsc.soe.ccs.rememberthattime.ail;

import java.time.LocalTime;

import edu.ucsc.soe.ccs.rememberthattime.ail.meta.SpeechActType;

public class SpeechAILInstance extends AILInstance {

	SpeechActType speechActType;
	String content;

	public SpeechAILInstance(AILCharacter subject, AILCharacter object,
			String content, LocalTime time, SpeechActType speechActType) {
		super(subject, object, time);
		this.speechActType = speechActType;
		this.content = content;
	}

}
