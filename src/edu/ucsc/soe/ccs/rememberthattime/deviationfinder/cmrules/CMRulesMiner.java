package edu.ucsc.soe.ccs.rememberthattime.deviationfinder.cmrules;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import ca.pfv.spmf.algorithms.sequential_rules.cmrules.AlgoCMRules;
import ca.pfv.spmf.test.MainTestCMRULES;

public class CMRulesMiner {
	
	public void mine(String inputFilePath, String outPutfilePath) throws IOException{
		// Load database
		
//		String input = fileToPath("contextPrefixSpan.txt");  // the database
//		String  output = "mining/testoutput.txt";  // the path for saving the frequent itemsets found
		
		String input = inputFilePath;
		String output = outPutfilePath;
		
		double minSup = 0.75;
		double minConf = 0.50; 
 
		AlgoCMRules algo = new AlgoCMRules();
		
		// TO SET MINIMUM / MAXIMUM SIZE CONSTRAINTS you can use the following lines:
//		algo.setMinLeftSize(1);
//		algo.setMaxLeftSize(2);
//		algo.setMinRightSize(1);
//		algo.setMaxRightSize(2);
		
		algo.runAlgorithm(input, output, minSup, minConf);
		
		algo.printStats();
	}

	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = MainTestCMRULES.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}
