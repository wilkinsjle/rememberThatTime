package edu.ucsc.soe.ccs.rememberthattime.processinput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessGamesRawLogs {

	private void processFiles(){
		List<File> logFilesList = Arrays.asList(new File("gameslogs/raw").listFiles());
		for (File file : logFilesList)
			try {
				processLines(file);
			} catch (IOException e) {
				System.out.println("Error opening log files.");
				e.printStackTrace();
			}
	}

	private void processLines(File file) throws IOException{
		new File("gameslogs/simplified").mkdir();
		BufferedReader br = new BufferedReader(new FileReader(file));
		List<String> allLines = new ArrayList<String>();
		String eachLine = "";

		while((eachLine = br.readLine()) != null)
			allLines.add(eachLine);

		String output = createSimplifiedLine(allLines);

		Files.write(Paths.get("gameslogs/simplified/" + 
				file.getName().trim().toLowerCase().split("-")[0] + 
				".txt"), output.getBytes());

		br.close();

	}

	private String createSimplifiedLine(List<String> allLines){

		String output = "", time = "", temp = "", happMood = "";
		List<String> happMem = new ArrayList<String>();

		boolean onlyOnce = true;

		for(String eachLine : allLines){

			//for now ignore "user chose to go second or first
			if(eachLine.contains("chosed")) continue;

			if(!eachLine.startsWith("INFO")){
				time = eachLine.substring(13, 24);
			}
			else{
				temp = eachLine.split("INFO: ")[1].trim().toLowerCase();
				if(!temp.startsWith("agent") && !temp.startsWith("user chose") 
						&& !temp.startsWith("user selected") && !temp.startsWith("user discarded")
						&& !temp.startsWith("user melded") && !temp.startsWith("user layoff")
						&& !temp.startsWith("game over - comment chosen") && !temp.startsWith("happiness"))
					continue;

				if(temp.contains("happiness")){
					int h = Integer.valueOf(temp.split("value: ")[1]);
					if(h < 10){
						if(!happMem.isEmpty())
							if(happMem.get(happMem.size()-1) == "laugh")
								happMood = "stopped laughing";
							else if (happMem.get(happMem.size()-1) == "smile")
								happMood = "stopped smiling";
						happMem.add("ser");
					}
					else if (h > 70 && h < 90){ // (60, 80) as smile
						if(!happMem.isEmpty())
							if(happMem.get(happMem.size()-1) != "smile")
								happMood = "smiled";
							else if (happMem.isEmpty())
								happMood = "smiled";
						happMem.add("smile");
					}
					else if (h >= 90){ // >=80 as laugh
						if(!happMem.isEmpty())
							if(happMem.get(happMem.size()-1) != "laugh")
								happMood = "laughed";
							else if (happMem.isEmpty())
								happMood = "laughed";
						happMem.add("laugh");
					}
				}
				
				if(onlyOnce){
					if(eachLine.toLowerCase().trim().contains("user selected")){
						output += "Agent asked user about their hand" + " -- at " + time + "\n";
						onlyOnce = false;
					}
				}

				if(temp.contains("happiness")){
					if(happMood != "")
						output += "User " + happMood + " -- at " + time + "\n";
				}
				else
					output += eachLine.split("INFO: ")[1] + " -- at " + time + "\n";
			}
			temp = ""; happMood = "";
		}

		return output;
	}

	public static void main(String[] args) {
		new ProcessGamesRawLogs().processFiles();
	}

}
