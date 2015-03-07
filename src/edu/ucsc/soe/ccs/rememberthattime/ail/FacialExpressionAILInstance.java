package edu.ucsc.soe.ccs.rememberthattime.ail;

import java.time.LocalTime;

import edu.ucsc.soe.ccs.rememberthattime.ail.meta.FacialExpressionType;

public class FacialExpressionAILInstance extends AILInstance{

	FacialExpressionType facialEpxpressionType;
	boolean stopped = false;

	public FacialExpressionAILInstance(AILCharacter subject,
			AILCharacter object, LocalTime time,
			FacialExpressionType facialEpxpressionType, boolean isStopped) {
		super(subject, object, time);
		this.facialEpxpressionType = facialEpxpressionType;
		this.stopped = isStopped;
	}

}
