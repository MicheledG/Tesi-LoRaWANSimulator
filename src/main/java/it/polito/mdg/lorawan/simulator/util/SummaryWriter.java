package it.polito.mdg.lorawan.simulator.util;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import it.polito.mdg.lorawan.simulator.model.SimulationSummary;

public class SummaryWriter {

	private final static String DEFAULT_SUMMARY_FILE_FOLDER ="summaries";
	private final static String DEFAULT_SUMMARY_FILE_NAME_PREFIX ="summary";
	private final static String DEFAULT_SUMMARY_FILE_NAME_SUFFIX =".json";	
	private final static String DEFAULT_RAW_SUMMARY_FILE_NAME_PREFIX ="raw";
	private final static String DEFAULT_RAW_SUMMARY_FILE_NAME_SUFFIX =".txt";
	private final static DateFormat DEFAULT_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmmssS");
	
	public static void writeResults(List<SimulationSummary> simulationSummaries, String summaryFileName) throws IOException{
		
		//sort the summaries
		Collections.sort(simulationSummaries);
		
		Date timestamp = new Date();
		
		//create the summary path
		String summaryPath = DEFAULT_SUMMARY_FILE_FOLDER + "/";
		if(summaryFileName != null){
			summaryPath += summaryFileName;	
		}
		else{
			summaryPath += DEFAULT_SUMMARY_FILE_NAME_PREFIX;
		}
		summaryPath += "-" 
		+ DEFAULT_TIMESTAMP_FORMAT.format(timestamp) 
		+ DEFAULT_SUMMARY_FILE_NAME_SUFFIX;
		
		
		//create the file writer where to push the summary
		FileWriter jsonFileWriter = new FileWriter(summaryPath);	
		//create ObjectWriter to serialize the summary object into the json file
		ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();				
		//print to the file
		objectWriter.writeValue(jsonFileWriter , simulationSummaries);		
		
		//create the raw summary path
		String rawSummaryPath = DEFAULT_SUMMARY_FILE_FOLDER 
				+ "/"
				+ DEFAULT_RAW_SUMMARY_FILE_NAME_PREFIX
				+ "-";
		
		if(summaryFileName != null){
			rawSummaryPath += summaryFileName;	
		}
		else{
			rawSummaryPath += DEFAULT_SUMMARY_FILE_NAME_PREFIX;
		}
		
		rawSummaryPath += "-" 
				+ DEFAULT_TIMESTAMP_FORMAT.format(timestamp) 
				+ DEFAULT_RAW_SUMMARY_FILE_NAME_SUFFIX;
		
		FileOutputStream fileOut = new FileOutputStream(rawSummaryPath);
		PrintWriter printWriter = new PrintWriter(fileOut, true);
		
		for (SimulationSummary simulationSummary : simulationSummaries) {
			int endDevice = simulationSummary.getConfiguration().getEndDeviceNumber();
			int trials = simulationSummary.getStatisticsResults().get(0).getTrials();
			double average = simulationSummary.getStatisticsResults().get(0).getAverage();
			double variance = simulationSummary.getStatisticsResults().get(0).getVariance();
			double stdDev = simulationSummary.getStatisticsResults().get(0).getStdDeviation();
			printWriter.format("%d\t%d\t%f\t%f\t%f\n", endDevice, trials, average, variance, stdDev);
		}
		
		printWriter.close();
	}	
	
}
