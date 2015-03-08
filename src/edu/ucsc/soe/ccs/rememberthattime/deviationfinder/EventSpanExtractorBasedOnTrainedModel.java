package edu.ucsc.soe.ccs.rememberthattime.deviationfinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.ucsc.soe.ccs.rememberthattime.deviationfinder.cmrules.CMRulesMiner;

public class EventSpanExtractorBasedOnTrainedModel {

	static final String trainDataLocation = "mining/traindata.txt";
	static final String modelDataLocation = "mining/model.txt";

	TrainingTestingSetGenerator trainTestSetGenerator;
	CMRulesMiner miner;
	RareSequenceFinder rareSequenceFinder;

	public EventSpanExtractorBasedOnTrainedModel() {

		//generate train and test sets (dynamic access)
		trainTestSetGenerator = new TrainingTestingSetGenerator();

		//miner (CMRULES) object
		miner = new CMRulesMiner();

		//less repeatitive sequences finder
		rareSequenceFinder = new RareSequenceFinder();

	}

	public Map<String, List<AILUniqueSpanDesignator>> findInterestingStorySpans() {

		//-----------------running models-----------------
		try {//GENERATING TRAINING DATA
			trainTestSetGenerator.generateTrainingDataFileFromAILs(trainDataLocation);
		} catch (IOException e) {
			System.out.println("Err in generating training data file.");
			e.printStackTrace();
		}

		try {// RUNNING MINING ALGORITHM
			miner.mine(trainDataLocation, modelDataLocation);
		} catch (IOException e) {
			System.out.println("Err in running mining.");
			e.printStackTrace();
		}

		List<StorySpan> rarePatterns = new ArrayList<StorySpan>();
		try {// finding less repeatitive sequences
			rarePatterns.addAll(rareSequenceFinder
					.extractLeastFrequentSpansFromModelOutput());
		} catch (IOException e) {
			System.out.println("Err in running mining.");
			e.printStackTrace();
		}
		//------------------------------------------------

		//sorting story spans based on first set size, then second set's
		Collections.sort(rarePatterns);

		//FINDING RARE SPANS OF ALL TEST SET INSTANCES
		return findMatchingPatternsInTestSet(rarePatterns);

	}

	private Map<String, List<AILUniqueSpanDesignator>> findMatchingPatternsInTestSet(
			List<StorySpan> rarePatterns) {

		Map<String, List<AILUniqueSpanDesignator>> interestingPartsForAllStories = 
				new HashMap<String, List<AILUniqueSpanDesignator>>();
		List<AILUniqueSpanDesignator> interestingPartsOfAStory;
		List<Integer> wholeSequence;

		for(Entry<String, List<Integer>> eachAIL 
				: trainTestSetGenerator.TestSet.entrySet()){

			interestingPartsOfAStory = new ArrayList<AILUniqueSpanDesignator>();

			for(StorySpan rarePattern : rarePatterns){

				wholeSequence = new ArrayList<Integer>();
				wholeSequence.addAll(rarePattern.preconditions);
				wholeSequence.addAll(rarePattern.leadingTo);

				if(stringFromInts(eachAIL.getValue()).contains(
						stringFromInts(wholeSequence))){

					int overlapIndex = stringFromInts(eachAIL.getValue())
							.indexOf(stringFromInts(wholeSequence));
					int overlapLength = wholeSequence.size();

					interestingPartsOfAStory.add(
							new AILUniqueSpanDesignator(overlapIndex, overlapLength));
				}
			}

			interestingPartsForAllStories.put(
					eachAIL.getKey(), interestingPartsOfAStory);
		}

		return interestingPartsForAllStories;
		
	}

	private static String stringFromInts(List<Integer> list){
		String output = "";
		for(int each : list)
			output += each + ",";
		return output;
	}

}
