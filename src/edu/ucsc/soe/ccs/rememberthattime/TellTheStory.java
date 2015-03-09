package edu.ucsc.soe.ccs.rememberthattime;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.ucsc.soe.ccs.rememberthattime.nlg.NLGEngine;
import edu.ucsc.soe.ccs.rememberthattime.processinput.GenerateAILForRummy;

public class TellTheStory {

	public static void main(String[] args) {

		GenerateAILForRummy AILmaker = new GenerateAILForRummy();
		NLGEngine nlg = new NLGEngine();

		Map<String, List<String>> stories = new HashMap<String, List<String>>();

		AILmaker.processFiles(true);

		//making a map of file name to stories (list of sentences)
		for(String key : AILmaker.allFilesAILs.keySet())
			stories.put(key, nlg.generateStoryFromAIL(
					AILmaker.allFilesAILs.get(key)));

		new File("gameslogs/fullstories").mkdir();

		for(String storyName : stories.keySet()){
			try {
				Files.write(Paths.get("gameslogs/fullstories/" 
						+ storyName.trim() + " - story.txt"), makeItASingleString(
								stories.get(storyName), AILmaker, false).getBytes());
			} catch (IOException e) {
				System.out.println("err in writing stories");
				e.printStackTrace(); 
			}
		}

		System.out.println("SUCCESS: AIL and story files generated"
				+ ", check ail and fullstories folders :) \n");

	}

	static String makeItASingleString(List<String> list
			, GenerateAILForRummy AILmaker, boolean span){

		String singleString = "";

		if(!span){
			singleString += "There once was "
					+ ((AILmaker.getAgent().getType().toString().toLowerCase().startsWith("a")) ? "an " : "a ")
					+ AILmaker.getAgent().getType().toString().toLowerCase()
					+ " named " + AILmaker.getAgent().getName() + " who was " 
					+ "playing rummy" /*this should be an activityDesciption() from a class*/ 
					+ " with " 
					+ ((AILmaker.getUser().getType().toString().toLowerCase().startsWith("a")) ? "an " : "a ") 
					+ AILmaker.getUser().getType().toString().toLowerCase() 
					+ " named " 
					+ AILmaker.getUser().getName().toLowerCase() + ". "; 
		} else {
			singleString += "During " + AILmaker.getAgent().getName() + " and "
					+ AILmaker.getUser().getName() + "'s " 
					+ "rummy game" /*this should be an activityName() from a class*/ 
					+ ", this happened: ";

		}

		List<String> connectors = new ArrayList<String>();
		connectors.add("Next");
		connectors.add("Then");
		connectors.add("Afterwards");
		connectors.add("After that");
		connectors.add("After which");
		connectors.add("Subsequently");
		connectors.add("When that was done");

		Random rand = new Random();
		int i = 0;
		for (String s : list){
			singleString += 
					s 
					+ " "
					+ ((rand.nextBoolean() && (i != list.size() - 1)) ? 
							connectors.get(rand.nextInt(connectors.size() - 1)) + ", " : "") 
							;
			i++;
		}

		return singleString;
	}


}
