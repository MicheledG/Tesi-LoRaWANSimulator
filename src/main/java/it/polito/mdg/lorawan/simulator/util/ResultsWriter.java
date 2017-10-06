package it.polito.mdg.lorawan.simulator.util;

import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import it.polito.mdg.lorawan.simulator.model.Results;

public class ResultsWriter {

	private final static String DEFAULT_RESULTS_FILE_NAME ="results.json";
	
	public static void writeResults(Results results, String resultsFileName) throws IOException{
		
		if(resultsFileName == null){
			resultsFileName = DEFAULT_RESULTS_FILE_NAME;
		}
		
		//create the file writer where to push the results
		FileWriter jsonFileWriter = new FileWriter(resultsFileName);	
		//create ObjectWriter to serialize the results object into the json file
		ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();				
		objectWriter.writeValue(jsonFileWriter , results);		
		
	}
	
}
