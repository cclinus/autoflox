package com.rca;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.core.CandidateElement;
import com.crawljax.core.CrawlSession;
import com.crawljax.core.CrawljaxException;
import com.crawljax.core.plugin.GeneratesOutput;
import com.crawljax.core.plugin.PostCrawlingPlugin;
import com.crawljax.core.plugin.PreCrawlingPlugin;
import com.crawljax.core.plugin.PreStateCrawlingPlugin;
import com.crawljax.core.plugin.OnNewStatePlugin;
import com.crawljax.util.Helper;
import com.rca.gui.rcaGui;

import org.w3c.dom.*;

public class RCAPlugin implements PostCrawlingPlugin, GeneratesOutput {
	private static String outputFolder;
	private static String traceFolder;
	
	private static String jsSourceFolder;
	
	public RCAPlugin(String trace_folder) { 
		//trace_folder should be set to the folder where the traces are stored
		this.traceFolder = trace_folder;
	}
	
	public RCAPlugin(String trace_folder, String jsFolder) { 
		//trace_folder should be set to the folder where the traces are stored
		this.traceFolder = trace_folder;
		this.jsSourceFolder = jsFolder;
	}
	
	@Override
	public void postCrawling(CrawlSession session) {
		System.out.println("\n\n*****AutoFLox*******");
		rcaGui.setOutputConsole("\n\n*****AutoFLox*******");
		//Note: Attach a variable to the error trace containing the
		//error message
		
		//Note: Create a function in RootCauseAnalyzer that finds the
		//error trace. You can do this by invoking TraceParser on the
		//file first, and then looking through each trace to see if any
		//of them is of type ERROR. The return value should be the error
		//message. Call this findErrorMsg().
		
		//Note: Create a function in RootCauseAnalyzer that takes the 
		//trace to be parsed and the initial null_var as its parameters. This
		//function would return true if it found a GEBID line; false 
		//otherwise. Suppose this function is called findGEBID()
		
		//STEPS:
		//Get outputFolder
		String of = getOutputFolder();
		
		//For each output trace file in the outputFolder, do the following
		//	1. Determine the initial null_var (should be "" if it's of the form document.getElementById(...)).
		//		You can do this by calling findErrorMsg() and getting the error message.
		//		Once you have the error message, determine if it is of the form "<something> is null".
		//		If so, we will ASSUME this is the corresponding error message.
		//		If there is no error message, move on to the next one
		//	2. Call findGEBID()
		//	3a. If findGEBID() returns true, an output must have been printed showing the GEBID line.
		//		In this case, halt execution since the output has been found
		//	3b. If findGEBID() returns false, move on to the next one
		boolean lineFound = false;
		File dir = new File(traceFolder);
		RootCauseAnalyzer rca = new RootCauseAnalyzer(jsSourceFolder);
		for (File child : dir.listFiles()) {
			//Ignore the self and parent aliases, and files beginning with "."
			if (".".equals(child.getName()) || "..".equals(child.getName()) || child.getName().indexOf(".") == 0) {
			      continue; 
			}
			
			String fullPath = traceFolder + "/" + child.getName();
			String errorMessage = rca.findErrorMsg(fullPath);
			if (errorMessage != null) {
				//Determine if it is of the form "<something> is null"
				if (errorMessage.matches(".* is null")) {
					//Get the initial null_var
					String initNullVar = errorMessage.substring(0, errorMessage.lastIndexOf(" is null"));
					if (initNullVar.startsWith("document.getElementById(") || initNullVar.startsWith("$(")
							|| initNullVar.contains("getAttribute") || initNullVar.contains("getComputedStyle")) {
						initNullVar = "";
					}
					
					//Find the direct DOM access
					boolean GEBIDfound = rca.findGEBID(fullPath, initNullVar);
					
					if (GEBIDfound) {
						System.out.println("Direct DOM access found!\n");
						rcaGui.setOutputConsole("Direct DOM access found!");
						return;
					}
				}
			}
		}
		
		//If GEBID line was not found, print a message saying so
		System.out.println("Direct DOM access NOT found\n");
		rcaGui.setOutputConsole("Direct DOM access NOT found");
		
		//Note: See first note in "Improvements to Tool" in your iTouch
	}
	
	@Override
	public String getOutputFolder() {
		return Helper.addFolderSlashIfNeeded(outputFolder);
	}
	
	@Override
	public void setOutputFolder(String absolutePath) {
		outputFolder = absolutePath;
	}
}