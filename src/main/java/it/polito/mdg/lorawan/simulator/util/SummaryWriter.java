package it.polito.mdg.lorawan.simulator.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import it.polito.mdg.lorawan.simulator.model.SimulationSummary;

public class SummaryWriter {

	private final static String DEFAULT_SUMMARY_FILE_FOLDER ="summaries";
	private final static String DEFAULT_SUMMARY_FILE_NAME_PREFIX ="summary";
	private final static String DEFAULT_SUMMARY_FILE_NAME_SUFFIX =".json";	
	private final static DateFormat DEFAULT_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmmss");
	
	public static void writeResults(SimulationSummary summary, String summaryFileName) throws IOException{
		
		
		//create the summary path
		String summaryPath = DEFAULT_SUMMARY_FILE_FOLDER + "/";
		if(summaryFileName != null){
			summaryPath += summaryFileName;	
		}
		else{
			summaryPath += DEFAULT_SUMMARY_FILE_NAME_PREFIX;
		}
		summaryPath += "-" 
		+ DEFAULT_TIMESTAMP_FORMAT.format(new Date()) 
		+ DEFAULT_SUMMARY_FILE_NAME_SUFFIX;
		
		
		//create the file writer where to push the summary
		FileWriter jsonFileWriter = new FileWriter(summaryPath);	
		//create ObjectWriter to serialize the summary object into the json file
		ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();				
		//print to the file
		objectWriter.writeValue(jsonFileWriter , summary);		
		
	}
	
}
