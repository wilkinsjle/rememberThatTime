package edu.ucsc.soe.ccs.rememberthattime.deviationfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RareSequenceFinder {

	private static final double CONFIDENCE_THRESHOLD = 0.9;
	private static final double SUPPORT_THRESHOLD = 9;

	public List<StorySpan> extractLeastFrequentSpansFromModelOutput() 
			throws IOException{

		List<StorySpan> storySpans = new ArrayList<StorySpan>();

		BufferedReader br = new BufferedReader(
				new FileReader(EventSpanExtractorBasedOnTrainedModel.modelLocation));

		String eachLine = "";
		List<String> allLines = new ArrayList<String>();
		while((eachLine = br.readLine()) != null)
			allLines.add(eachLine);

		for(String line : allLines){
			if(Double.valueOf(line.split("#CONF:")[1].trim()) <= CONFIDENCE_THRESHOLD 
					&& (Integer.valueOf(line.split("#CONF:")[0].split("#SUP:")[1].trim()) 
							<= SUPPORT_THRESHOLD)){

				List<Integer> list1 = new ArrayList<Integer>();
				List<Integer> list2 = new ArrayList<Integer>();

				for(String eachEventID : 
					line.split("#SUP")[0].split("==>")[0].trim().split(","))
					list1.add(Integer.valueOf(eachEventID));

				for(String eachEventID : 
					line.split("#SUP")[0].split("==>")[1].trim().split(","))
					list2.add(Integer.valueOf(eachEventID));

				storySpans.add(new StorySpan(list1, list2));
			}
		}

		br.close();
		return storySpans;
	}

}
