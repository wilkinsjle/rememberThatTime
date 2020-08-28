package edu.ucsc.soe.ccs.rememberthattime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.ucsc.soe.ccs.rememberthattime.ail.AILInstance;
import edu.ucsc.soe.ccs.rememberthattime.deviationfinder.*;
import edu.ucsc.soe.ccs.rememberthattime.nlg.NLGEngine;
import edu.ucsc.soe.ccs.rememberthattime.processinput.GenerateAILForRummy;


public class TellTheFunParts {

	private static int InterestingPartStartMargin = 2;
	private static int InterestingPartEndMargin = 2;

	public static void main(String[] args) {

		Map<String, List<AILUniqueSpanDesignator>> allInterestingParts = 
				new EventSpanExtractorBasedOnTrainedModel().findInterestingStorySpans();

		//System.out.println("all interesting parts" + allInterestingParts);

		String allInterestingStories, allRandomStories;
		List<List<AILInstance>> allInterestinSpans;
		List<List<AILInstance>> randomSpansofSameLength;
		List<List<String>> allInterestingSpansStories;
		List<List<String>> allRandomSpansStories;

		GenerateAILForRummy AILmaker = new GenerateAILForRummy();
		AILmaker.processFiles(false);

		NLGEngine nlg = new NLGEngine();

		new File("gameslogs/interesting").mkdir();

		//finding the files that have had interesting parts found in them
		Set<String> filesWithInterestingParts = allInterestingParts.keySet();
		// allInterestingParts in generating correct keys, but incorrect values

		//looping over each of such files
		for(String eachFile : filesWithInterestingParts){
			//System.out.println(eachFile); // PASS

			allInterestingStories = "";
			allRandomStories = "";
			allInterestinSpans = new ArrayList<List<AILInstance>>();
			randomSpansofSameLength = new ArrayList<List<AILInstance>>();
			allInterestingSpansStories = new ArrayList<List<String>>();
			allRandomSpansStories = new ArrayList<List<String>>();


			//getting a list of lists of aili for each file: interesting spans
			for (AILUniqueSpanDesignator eachSpan : allInterestingParts.get(eachFile)){
//				System.out.println("fail, need to work out what this part of the code is doing");
				allInterestinSpans.add(extractSpan(
						AILmaker.allFilesAILs.get(eachFile.trim()), eachSpan));
				randomSpansofSameLength.add(getRandomSpan(
						AILmaker.allFilesAILs.get(eachFile.trim()), eachSpan.getLength()));
			}

			//making a list of stories for interesting and random parts (each a list of sentences)
			for(List<AILInstance> eachSpan : allInterestinSpans)
				allInterestingSpansStories.add(nlg.generateStoryFromAIL(eachSpan));
				//System.out.println("looped over all interesting spans");
			for(List<AILInstance> eachSpan : randomSpansofSameLength)
				allRandomSpansStories.add(nlg.generateStoryFromAIL(eachSpan));
				//System.out.println("looped over random spans of same length");

			//write all story spans in a single string for interesting and random parts
			for(List<String> eachSpan : allInterestingSpansStories){
				allInterestingStories += 
						TellTheStory.makeItASingleString(eachSpan, AILmaker, true) +
						"\n\n---------------------------------\n\n";
			}
			for(List<String> eachSpan : allRandomSpansStories){
				allRandomStories += 
						TellTheStory.makeItASingleString(eachSpan, AILmaker, true) +
						"\n\n---------------------------------\n\n";
			}

			try { //write them to file
				Files.write(Paths.get("gameslogs/interesting/" 
						+ eachFile + " - interesting spans.txt"), 
						allInterestingStories.getBytes());
				Files.write(Paths.get("gameslogs/interesting/" 
						+ eachFile + " - random spans.txt"), 
						allRandomStories.getBytes());
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

	private static List<AILInstance> getRandomSpan(List<AILInstance> ail, int length) {
		int rand = new Random().nextInt(ail.size() - length);
		return new ArrayList<AILInstance>(ail.subList(rand, rand + length));
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
