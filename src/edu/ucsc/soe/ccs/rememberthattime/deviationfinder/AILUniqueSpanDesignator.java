package edu.ucsc.soe.ccs.rememberthattime.deviationfinder;

public class AILUniqueSpanDesignator {
	
	int startingpoint;
	int length;
	
	public AILUniqueSpanDesignator(int startingpoint, int endingpoint) {
		this.startingpoint = startingpoint;
		this.length = endingpoint;
	}
	
	public int getStartingpoint() {
		return startingpoint;
	}

	public int getEndingpoint() {
		return startingpoint + length;
	}

}
