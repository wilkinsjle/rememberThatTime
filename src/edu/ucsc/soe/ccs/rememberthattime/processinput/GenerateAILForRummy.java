package edu.ucsc.soe.ccs.rememberthattime.processinput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsc.soe.ccs.rememberthattime.ail.*;
import edu.ucsc.soe.ccs.rememberthattime.ail.meta.CharacterType;
import edu.ucsc.soe.ccs.rememberthattime.ail.meta.FacialExpressionType;
import edu.ucsc.soe.ccs.rememberthattime.ail.meta.Gender;
import edu.ucsc.soe.ccs.rememberthattime.ail.meta.SpeechActType;
import edu.ucsc.soe.ccs.rememberthattime.ail.rummySpecificAIL.*;

public class GenerateAILForRummy {

	Map<String, List<AILInstance>> allFilesAILs;

	AILCharacter agent, user;

	public GenerateAILForRummy() {

		allFilesAILs = new HashMap<String, List<AILInstance>>();

		agent = new AILCharacter(
				"Karen", CharacterType.AGENT, Gender.FEMALE);
		user = new AILCharacter(
				"user", CharacterType.HUMAN, Gender.UNKNOWN);

		processFiles();

	}

	private void processFiles(){
		List<File> logFilesList = Arrays.asList(new File("gameslogs/simplified").listFiles());
		for (File file : logFilesList){
			if(file.isHidden()) continue;
			try {
				processFile(file);
			} catch (IOException e) {
				System.out.println("Error opening processed log files.");
				e.printStackTrace();
			}
		}
	}

	private void processFile(File file) throws IOException{

		//		List<AILInstance> AIL = new ArrayList<AILInstance>();

		new File("gameslogs/ail").mkdir();
		BufferedReader br = new BufferedReader(new FileReader(file));
		List<String> allLines = new ArrayList<String>();
		String eachLine = "";

		while((eachLine = br.readLine()) != null)
			allLines.add(eachLine);

		List<AILInstance> AIL = generateAIL(allLines);

		//each AIL to file
		Files.write(Paths.get("gameslogs/ail/" + file.getName() 
				+ " - ail.txt"), toString(AIL).getBytes());

		//putting all AILs for all files into a map
		allFilesAILs.put(file.getName(), AIL);

		br.close();

	}

	private List<AILInstance> generateAIL(List<String> allLines) {

		LocalTime time;
		AILCharacter subj = null, potentialObj = null;
		List<AILInstance> AIL = new ArrayList<AILInstance>();
		String eventPart = "";

		for(String eachLine : allLines){

			String timeTemp = convertTime(eachLine.split("-- at")[1].trim());
			time = LocalTime.parse(timeTemp);
			eachLine = eachLine.trim().toLowerCase();

			if(eachLine.startsWith("game over")) { 
				//special case of gameover dealt with here
				AIL.add(new GameOverEvent(null, null, time, "rummy"));
				AIL.add(new SpeechAILInstance(
						eachLine.split("--")[0].split(":")[0].contains("user") ? 
								this.user : this.agent, potentialObj, 
								eachLine.split("--")[0].split(":")[1].trim(), 
								time, SpeechActType.EXPRESSIVE));
				continue;
			}

			subj = eachLine.startsWith("user") ? this.user : this.agent;
			potentialObj = eachLine.startsWith("user") ? this.agent : this.user;

			eventPart = eachLine.split("--")[0].contains(":") ? 
					eachLine.split("--")[0].split(":")[0] : eachLine.split("--")[0];

					//goin all down riiiiiit heeeer

					if(eventPart.contains("smile"))
						AIL.add(new FacialExpressionAILInstance(subj, null, time, 
								FacialExpressionType.SMILE, eventPart.contains("stop")));

					else if(eventPart.contains("laugh"))
						AIL.add(new FacialExpressionAILInstance(subj, null, time, 
								FacialExpressionType.LAUGHTER, eventPart.contains("stop")));

					else if(eventPart.contains("laid off") || eventPart.contains("layoff"))
						AIL.add(new LayOffEvent(subj, null, time, "rummy"));

					else if(eventPart.contains("meld"))
						AIL.add(new MeldEvent(subj, null, time, "rummy"));

					else if(eventPart.contains("discard"))
						AIL.add(new DiscardEvent(subj, null, time, "rummy"));

					else if(eventPart.contains("comment"))
						AIL.add(new SpeechAILInstance(subj, potentialObj, 
								eachLine.split("--")[0].split(":")[1].trim(), 
								time, SpeechActType.EXPRESSIVE));

					else if(eventPart.contains("respon"))
						AIL.add(new ResponseEvent(subj, potentialObj, 
								eachLine.split("--")[0].split(":")[1].trim(), 
								time, SpeechActType.EXPRESSIVE));

					eventPart = "";

		}

		return AIL;

	}


	private String toString(List<AILInstance> list) {
		// TODO Auto-generated method stub
		return "check";
	}

	private String convertTime(String t){
		String ttemp = t.split(" ")[0].trim();
		String ampm = t.split(" ")[1].trim();
		int h = Integer.valueOf(ttemp.split(":")[0].trim());
		int m = Integer.valueOf(ttemp.split(":")[1].trim());
		int s = Integer.valueOf(ttemp.split(":")[2].trim());
		if(ampm.toLowerCase().equals("pm") && h != 12) h += 12;
		if(ampm.toLowerCase().equals("am") && h == 12) h = 0;
		return new DecimalFormat("00").format(h) + ":" + 
		new DecimalFormat("00").format(m) + ":" + 
		new DecimalFormat("00").format(s);
	}

	public static void main(String[] args) {
		new GenerateAILForRummy();

	}

}
