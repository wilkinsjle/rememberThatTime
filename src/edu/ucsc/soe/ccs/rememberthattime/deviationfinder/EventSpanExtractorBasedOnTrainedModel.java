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
	static final String modelLocation = "mining/model.txt";

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
			miner.mine(trainDataLocation, modelLocation);
		} catch (IOException e) {
			System.out.println("Err in running mining.");
			e.printStackTrace();
		}

		List<StorySpan> rarePatterns = new ArrayList<StorySpan>();
		try {//FINDING LESS REPEATITIVE SEQUENCES
			rarePatterns.addAll(rareSequenceFinder
					.extractLeastFrequentSpansFromModelOutput());
		} catch (IOException e) {
			System.out.println("Err in running mining.");
			e.printStackTrace();
		}
		//------------------------------------------------

		//SORTING STORY SPANS based on first set size, then second set's
		Collections.sort(rarePatterns);
		Collections.reverse(rarePatterns);

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

			System.out.println("______________________________________");
			System.out.println("AIL: " + stringFromInts(eachAIL.getValue()));

			interestingPartsOfAStory = new ArrayList<AILUniqueSpanDesignator>();

			for(StorySpan rarePattern : rarePatterns){

				wholeSequence = new ArrayList<Integer>();
				wholeSequence.addAll(rarePattern.preconditions);
				wholeSequence.addAll(rarePattern.leadingTo);

				System.out.println("Preconditions: " + stringFromInts(rarePattern.preconditions));
				System.out.println("LeadingTo: " + stringFromInts(rarePattern.leadingTo));

				System.out.println(stringFromInts(wholeSequence));

				// `(wholeSequence)` is our preconditions + postconditions
				// `eachAIL.getValue()` is what we are looking in, the story trace

				if(stringFromInts(eachAIL.getValue()).contains(
						stringFromInts(wholeSequence))){

					System.out.println("*************** Contains Passed ***************");

					int overlapIndex = stringFromInts(eachAIL.getValue())
							.indexOf(stringFromInts(wholeSequence));
					int overlapLength = wholeSequence.size();

					//removing comma effect on the index
					overlapIndex -= overlapIndex/2;

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

	// Find instances of myPreconditions appearing, ignoring events in between:
	private int searchForPreConditions(List<Integer> preConditions, List<Integer> storyTrace){

		boolean continueSearch = true;

		int indexBeginPostConditionSearch = 0;

		for (int i=0; i<preConditions.size() && continueSearch; i++) {

			for (int j = 0; j < storyTrace.size(); j++) {

				if (preConditions.get(i) == storyTrace.get(j)) {

					// System.out.println("found a match for: " + preConditions.get(i));

					indexBeginPostConditionSearch = j;

					break;
				}

				if (j == storyTrace.size() - 1) {
					continueSearch = false;
				}
			}
		}

		return indexBeginPostConditionSearch;
	}

	// Find instances of myPreconditions appearing, ignoring events in between:
	private boolean searchForPostConditions(List<Integer> postConditions, List<Integer> storyTrace, int index){

		for (int i=0; i<postConditions.size(); i++) {

			for (int j=index+1; j < storyTrace.size(); j++) {

				//System.out.println("searching Entire Trace at: " + j);

				if (postConditions.get(i) == storyTrace.get(j)) {

					// System.out.println("found a match for: " + myPostconditions[i]);

					break; // move to next integer in storyTrace
				}

				if (j == storyTrace.size() - 1) {

					return false;
				}
			}
		}

		return true;
	}

}
