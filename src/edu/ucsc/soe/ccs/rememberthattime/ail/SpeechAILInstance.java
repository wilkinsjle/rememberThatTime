package edu.ucsc.soe.ccs.rememberthattime.ail;

import java.time.LocalTime;

import edu.ucsc.soe.ccs.rememberthattime.ail.meta.SpeechActType;

public class SpeechAILInstance extends AILInstance {

	protected SpeechActType speechActType; //SAT
	protected String content;

	final String verb = "tell"; //this has to be extended to an enum, changed based on Speech Act etc

	public SpeechAILInstance(AILSubject subject, AILObject object,
			String content, LocalTime time, SpeechActType speechActType) {
		super(subject, object, time);
		this.speechActType = speechActType;
		this.content = content;
	}

	@Override
	public String getVerb() { //TODO return different verbs based on speech act
		return "tell";
	}

	public String getContent(){
		return content;
	}

	@Override
	public String getAILString() {
		return "SpeechAILI(" 
				+ this.getSubject().getName() + ", " 
				+ this.getObject().getName() + ", " 
				+ this.speechActType.name() + ", "
				+ this.content + ", "
				+ "null" + ", "
				+ this.time.toString() 
				+ ")";
	}

}
