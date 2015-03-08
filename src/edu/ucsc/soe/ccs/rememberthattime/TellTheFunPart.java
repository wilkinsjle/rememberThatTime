package edu.ucsc.soe.ccs.rememberthattime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ucsc.soe.ccs.rememberthattime.ail.AILInstance;
import edu.ucsc.soe.ccs.rememberthattime.deviationfinder.*;
import edu.ucsc.soe.ccs.rememberthattime.nlg.NLGEngine;
import edu.ucsc.soe.ccs.rememberthattime.processinput.GenerateAILForRummy;


public class TellTheFunPart {

	private static int InterestingPartStartMargin = 2;
	private static int InterestingPartEndMargin = 2;

	public static void main(String[] args) {

		Map<String, List<AILUniqueSpanDesignator>> allInterestingParts = 
				new EventSpanExtractorBasedOnTrainedModel().
				findInterestingStorySpans();

		String allInterestingStories;
		List<List<AILInstance>> allInterestinSpans ;
		List<List<String>> allInterestinSpansStories ;

		GenerateAILForRummy AILmaker = new GenerateAILForRummy();
		AILmaker.processFiles(false);

		NLGEngine nlg = new NLGEngine();

		new File("gameslogs/interesting").mkdir();

		//finding the files that have had interesting parts found in them
		Set<String> filesWithInterestingParts = allInterestingParts.keySet();

		//looping over each of such files
		for(String eachFile : filesWithInterestingParts){

			allInterestingStories = "";
			allInterestinSpans = new ArrayList<List<AILInstance>>();
			allInterestinSpansStories = new ArrayList<List<String>>();

			//getting a list of lists of aili for each file: interesting spans
			for (AILUniqueSpanDesignator eachSpan : allInterestingParts.get(eachFile))
				allInterestinSpans.add(extractSpan(
						AILmaker.allFilesAILs.get(eachFile.trim()), eachSpan));

			//making a list of stories (each a list of sentences)
			for(List<AILInstance> eachSpan : allInterestinSpans)
				allInterestinSpansStories.add(nlg.generateStoryFromAIL(eachSpan));

			//write all story spans in a single string
			for(List<String> eachSpan : allInterestinSpansStories)
				allInterestingStories += 
				TellTheStory.makeItASingleString(eachSpan) +
				"\n\n---------------------------------\n\n";

			//write it to file
			try { Files.write(Paths.get("gameslogs/interesting/" 
					+ eachFile + " - interesting spans.txt"), 
					allInterestingStories.getBytes());
			} catch (IOException e) {e.printStackTrace();}

		}

		System.out.println("-------------------------------\n"
				+ "SUCCESS: Model created from " + 
				TrainingTestingSetGenerator.TRAIN_SET_SIZE + 
				" randomly selected training set stories; \n"
				+ "extracted interesting story spans;\n"
				+ "printed out interesting parts found in remaining"
				+ " test set stories (if any) \nlook in into interesting folder :) \n");

	}

	private static List<AILInstance> extractSpan (
			List<AILInstance> ail, AILUniqueSpanDesignator span){
		//playing it safely with the margins there;
		return new ArrayList<AILInstance>(
				ail.subList((span.getStartingpoint() - InterestingPartStartMargin >= 0) ? 
						span.getStartingpoint() - InterestingPartStartMargin : 
							span.getStartingpoint(),
							(span.getEndingpoint() + InterestingPartEndMargin < ail.size()) ?
									span.getEndingpoint() + InterestingPartEndMargin: 
										span.getEndingpoint()));
	}

}
