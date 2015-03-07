package edu.ucsc.soe.ccs.rememberthattime.ail;

import java.time.LocalTime;

import edu.ucsc.soe.ccs.rememberthattime.ail.meta.FacialExpressionType;

public class FacialExpressionAILInstance extends AILInstance{

	FacialExpressionType facialEpxpressionType;
	public boolean stopped = false;

	public FacialExpressionAILInstance(AILSubject subject, AILObject object, 
			LocalTime time, FacialExpressionType facialEpxpressionType, boolean isStopped) {
		super(subject, object, time);
		this.facialEpxpressionType = facialEpxpressionType;
		this.stopped = isStopped;
	}

	@Override
	public String getVerb() { 

		String verb;

		if(stopped) return "stop";

		switch (this.facialEpxpressionType)
		{
		case SMILE: verb = "smile"; break;
		case LAUGHTER: verb = "laugh"; break;
		case GAZE: verb = "gaze"; break;
		case NOD:verb = "nod"; break;
		case HEADSHAKE: verb = "shake head"; break; //TODO if "shake head" does not work fix later, altering obj?
		case EYEBROWS_UP: verb = "wondered"; break; //TODO fix later, altering obj?
		default: verb = "";
		}

		return verb;

	}

	public String stoppedWhat(){

		String what;
		switch (this.facialEpxpressionType)
		{
		case SMILE: what = "smiling"; break;
		case LAUGHTER: what = "laughing"; break;
		case GAZE: what = "gazing"; break;
		case NOD:what = "nodding"; break;
		case HEADSHAKE: what = "head-shaking"; break; //TODO if "shake head" does not work fix later, altering obj?
		case EYEBROWS_UP: what = "wondering"; break; //TODO fix later, altering obj?
		default: what = "";
		}
		return what;
	}

	@Override
	public String getAILString() {
		return "FacialExpressionAILI(" 
				+ this.getSubject() != null ? this.getSubject().getName() : "null" + ", " 
				+ this.getObject() != null ? this.getObject().getName() : "null" + ", " 
				+ this.facialEpxpressionType.name() + ", "
				+ "null "+ ", "
				+ "null" + ", "
				+ this.time.toString() 
				+ ")";
	}

}
