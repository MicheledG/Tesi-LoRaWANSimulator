package it.polito.mdg.lorawan.simulator.util;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import it.polito.mdg.lorawan.simulator.model.SimulationResult;

public class ResultsWriter {

	private final static String DEFAULT_RESULTS_FILE_FOLDER ="results";
	private final static String DEFAULT_RESULTS_FILE_NAME_PREFIX ="results";
	private final static String DEFAULT_RESULTS_FILE_NAME_SUFFIX =".json";	
	private final static DateFormat DEFAULT_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmmss");
	
	public static void writeResults(SimulationResult results, String resultsFileName) throws IOException{
		
		//sort the results
		Collections.sort(results.getResults());
		
		//create the results path
		String resultsPath = DEFAULT_RESULTS_FILE_FOLDER + "/";
		
		//print to the file
		if(resultsFileName == null){
			resultsPath += DEFAULT_RESULTS_FILE_NAME_PREFIX
					+ "-"
					+ DEFAULT_TIMESTAMP_FORMAT.format(new Date())
					+ DEFAULT_RESULTS_FILE_NAME_SUFFIX;
		}
		else{
			resultsPath += resultsFileName;
		}
		
		//create the file writer where to push the results
		FileWriter jsonFileWriter = new FileWriter(resultsPath);	
		//create ObjectWriter to serialize the results object into the json file
		ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();				
		objectWriter.writeValue(jsonFileWriter , results);		
		
	}
	
}
