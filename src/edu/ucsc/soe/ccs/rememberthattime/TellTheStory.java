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

		AILmaker.processFiles();

		//making a map of file name to stories (list of sentences)
		for(String key : AILmaker.allFilesAILs.keySet())
			stories.put(key, nlg.generateStoryFromAIL(
					AILmaker.allFilesAILs.get(key)));

		new File("gameslogs/fullstories").mkdir();

		for(String storyName : stories.keySet()){
			try {
				Files.write(Paths.get("gameslogs/fullstories/" 
						+ storyName.trim().split(".txt")[0] + ".txt"), 
						makeItASingleString(stories.get(storyName)).getBytes());
			} catch (IOException e) {
				System.out.println("err in writing stories");
				e.printStackTrace(); 
			}
		}

	}

	private static String makeItASingleString(List<String> list){

		String singleString = "";

		List<String> connectors = new ArrayList<String>();
		connectors.add("Next");
		connectors.add("Then");
		connectors.add("Afterwards");
		connectors.add("After that");
		connectors.add("After which");
		connectors.add("Subsequently");
		connectors.add("When that was done");

		Random rand = new Random();

		for (String s : list) 
			singleString += 
			s 
			+ " "
			+ connectors.get(rand.nextInt(connectors.size() - 1)) 
			+ ", ";

		return singleString;
	}

}
