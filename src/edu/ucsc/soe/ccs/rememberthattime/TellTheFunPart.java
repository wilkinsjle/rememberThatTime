package edu.ucsc.soe.ccs.rememberthattime;

import java.io.File;
import java.util.List;
import java.util.Map;

import edu.ucsc.soe.ccs.rememberthattime.deviationfinder.*;
import edu.ucsc.soe.ccs.rememberthattime.processinput.GenerateAILForRummy;


public class TellTheFunPart {

	public static void main(String[] args) {
		
		Map<String, List<AILUniqueSpanDesignator>> allInterestingParts = 
				new EventSpanExtractorBasedOnTrainedModel().
				findInterestingStorySpans();
		
		GenerateAILForRummy AILmaker = new GenerateAILForRummy();
		AILmaker.processFiles(false);
		
		//map of file name to interesting spans list
		//map of file names to AIL instances
		
		//extract the second based on first
		//make stories, spit out
		
		
		new File("gameslogs/interesting").mkdir();
		
		



	}

}
