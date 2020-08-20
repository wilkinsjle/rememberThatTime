package edu.ucsc.soe.ccs.rememberthattime.deviationfinder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class TrainingTestingSetGenerator {

	//	private final int TEST_SET_SIZE = 5;
	public static final int TRAIN_SET_SIZE = 10;
	public static final long CONSISTENT_RANDOM_SEED = 12345;

	List<String> AILFileNames;

	//all ail files with instaces as strings,
	Map<String, List<String>> ailListAsStrings; 
	//all ail files, with each instance replace with its event type string,
	Map<String, List<String>> ailListEventNamesOnly; 
	//event type strings replaced with number identifiers (below).
	/* 1 FacialExpressionAILI, 2 SpeechAILI, 3 ResponseAILI
	4 DiscardEventAILI, 5 MeldEventAILI, 6 LayOffEventAILI, 7 GameOverAILI */
	Map<String, List<Integer>> ailListEventIDsOnly; 

	Map<String, List<Integer>> TrainSet; 
	Map<String, List<Integer>> TestSet; 

	public TrainingTestingSetGenerator(){

		AILFileNames = new ArrayList<String>();
		ailListAsStrings = new HashMap<String, List<String>>();
		ailListEventNamesOnly = new HashMap<String, List<String>>();
		ailListEventIDsOnly = new HashMap<String, List<Integer>>();
		TrainSet = new HashMap<String, List<Integer>>();
		TestSet = new HashMap<String, List<Integer>>();

		loadAILsFromFile();
		extractEventNames();

		try{convertToEventIDs();}
		catch(IllegalArgumentException e)
		{System.out.println("Event type not recognized in convertToEventIDs.");
		e.printStackTrace();}

		//shuffling file names and choosing test and train sets
		//Shuffling with a seed to get consistent results
		Collections.shuffle(AILFileNames, new Random(CONSISTENT_RANDOM_SEED));

		populateTrainAndTestSets();

		//create the test file data
		//		try{generateTrainingDataFileFromAILs();}
		//				catch(Exception e)
		//				{System.out.println("Err in generating test file.");
		//				e.printStackTrace();}

	}

	//TODO pass directly to trainer
	public void generateTrainingDataFileFromAILs(String OutputFilePath) throws IOException{
		String masterString = "";
		int i = 0;
		for(List<Integer> eachAILData : TrainSet.values()){
			for(int eachNumber : eachAILData){
				masterString += eachNumber + " -1 ";
			}
			if(i == TrainSet.values().size() - 1)
				masterString += "-2";
			else
				masterString += "-2\n";
			i++;
		}
		Files.write(Paths.get(OutputFilePath)
				, masterString.getBytes());
	}


	private void populateTrainAndTestSets(){
		int i = 1;
		for( Entry<String, List<Integer>> each : ailListEventIDsOnly.entrySet()){
			if(i <= TRAIN_SET_SIZE)
				TrainSet.put(each.getKey(), each.getValue());
			else
				TestSet.put(each.getKey(), each.getValue());
			i++;
		}
	}

	private void convertToEventIDs(){

		List<Integer> tmp;

		for(String eachKey : ailListEventNamesOnly.keySet()){
			int i = 0;
			tmp = new ArrayList<Integer>();

			for(String eachAILIType : ailListEventNamesOnly.get(eachKey)){

				if(eachAILIType.trim()
						.contains("SpeechGreet"))
					i = 1;
				else if(eachAILIType.trim()
						.contains("SpeechPositive"))
					i = 2;
				else if(eachAILIType.trim()
						.contains("SpeechNegative"))
					i = 3;
				else if(eachAILIType.trim()
						.contains("ThoughtPositive"))
					i = 4;
				else if(eachAILIType.trim()
						.contains("ThoughtNegative"))
					i = 5;
				else if(eachAILIType.trim()
						.contains("ThoughtConflict"))
					i = 6;
				else if(eachAILIType.trim()
						.contains("SpeechProposal"))
					i = 7;
				else if(eachAILIType.trim()
						.contains("SpeechRejection"))
					i = 8;
				else if(eachAILIType.trim()
						.contains("SpeechDrop"))
					i = 9;
				else if(eachAILIType.trim()
						.contains("SpeechAccept"))
					i = 10;
				else
					throw new IllegalArgumentException();

				tmp.add(i);

			}

			ailListEventIDsOnly.put(eachKey, tmp);
		}

	}

	//for now mining only with event names
	//NOTE: SKIPS IF AILI TYPE STARTS WITH "AILI" to skip line 0 (introductory row)
	private void extractEventNames(){
		List<String> tmp;

		for(String eachKey : ailListAsStrings.keySet()){
			tmp = new ArrayList<String>();
			for(String eachAILI : ailListAsStrings.get(eachKey)){
				if(eachAILI.startsWith("AILI")) 
					continue;
				tmp.add(eachAILI.split("\\(")[0]);
			}
			ailListEventNamesOnly.put(eachKey, tmp);
		}
	}


	private void loadAILsFromFile() {
		List<File> logFilesList = Arrays.asList(
				new File("agentMapsGamelogs/ail").listFiles());
		for (File file : logFilesList){
			if(file.isHidden()) continue;
			AILFileNames.add(file.getName().split(" - ")[0].trim());
			try {
				ailListAsStrings.put(file.getName().split(" - ")[0].trim()
						, getAILAsListOfString(file));
			} catch (IOException e) {
				System.out.println("Error opening AIL files.");
				e.printStackTrace();
			}
		}
	}

	private List<String> getAILAsListOfString(File file) throws IOException{

		List<String> ListOfString = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String eachLine = "";

		while((eachLine = br.readLine()) != null)
			ListOfString.add(eachLine);

		br.close();
		return ListOfString;

	}

	//for test
	public static void main(String[] args) {

	}

}
