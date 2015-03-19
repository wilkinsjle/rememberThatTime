package edu.ucsc.soe.ccs.rememberthattime.deviationfinder;

import java.util.List;

public class StorySpan implements Comparable<StorySpan> {

	List<Integer> preconditions;
	List<Integer> leadingTo;

	public StorySpan(List<Integer> preconditions, List<Integer> leadingTo) {
		this.preconditions = preconditions;
		this.leadingTo = leadingTo;
	}

	//sort by first size of second list, then size of first list
	@Override
	public int compareTo(StorySpan o) {
//		int c1 = new Integer(this.leadingTo.size()).
//				compareTo(o.leadingTo.size());
//		int c2 = new Integer(this.preconditions.size()).
//				compareTo(o.preconditions.size());
//		return c1 == 0 ? c2 : c1;
		
		return new Integer(this.leadingTo.size() + this.preconditions.size()).
				compareTo(o.leadingTo.size() + o.preconditions.size());
	}

}