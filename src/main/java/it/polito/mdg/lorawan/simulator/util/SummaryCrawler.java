package it.polito.mdg.lorawan.simulator.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polito.mdg.lorawan.simulator.model.Config;
import it.polito.mdg.lorawan.simulator.model.ResultInformation;
import it.polito.mdg.lorawan.simulator.model.SimulationResult;
import it.polito.mdg.lorawan.simulator.model.SimulationSummary;
import it.polito.mdg.lorawan.simulator.model.statistics.StatisticsResult;
import it.polito.mdg.lorawan.simulator.util.statistics.StatisticsCalculator;

public class SummaryCrawler {

	private final static String DEFAULT_PARAMETER_NAME = "dataExtractionRatio";
	
	public static SimulationSummary crawlSummary(Config configuration, List<SimulationResult> simulationResults){
				
		SimulationResult testSimulationResult = simulationResults.get(0);
		
		int numberOfSimulations = simulationResults.size();
		int numberOfResultSet = testSimulationResult.getResults().size(); //1 result information per each result set
		Collections.sort(testSimulationResult.getResults());
		
		double dataExtractionRates[][] = new double[numberOfResultSet][numberOfSimulations]; 
		
		//collect all the data extraction ratio from all the result information of each simulation result
		for (int simulationIndex = 0; simulationIndex < numberOfSimulations; simulationIndex++) {
			SimulationResult simulationResult = simulationResults.get(simulationIndex);	
			List<ResultInformation> resultInformations =  simulationResult.getResults();
			Collections.sort(resultInformations);						
			for (int resultSetIndex = 0; resultSetIndex < numberOfResultSet; resultSetIndex++) {
				ResultInformation resultInformation = simulationResult.getResults().get(resultSetIndex);	
				dataExtractionRates[resultSetIndex][simulationIndex] = resultInformation.getDataExtractionRatio();
			}
		}
		
		List<StatisticsResult> statisticsResults = new ArrayList<>();
		for(int resultSetIndex = 0; resultSetIndex < numberOfResultSet; resultSetIndex++){
			Object resultSet = testSimulationResult.getResults().get(resultSetIndex).getResultSet();			
			double derAvg = StatisticsCalculator.computeAverage(dataExtractionRates[resultSetIndex]);
			double derVariance = StatisticsCalculator.computeVariance(dataExtractionRates[resultSetIndex]);
			double derStdDev = StatisticsCalculator.computeStdDev(dataExtractionRates[resultSetIndex]);

			StatisticsResult statisticsResult = new StatisticsResult(
					resultSet,
					DEFAULT_PARAMETER_NAME,
					numberOfSimulations,
					derAvg,
					derVariance,
					derStdDev);
			statisticsResults.add(statisticsResult);
		}
		
		
		SimulationSummary simulationSummary = new SimulationSummary();
		simulationSummary.setConfiguration(configuration);				
		simulationSummary.setStatisticsResults(statisticsResults);
		
		return simulationSummary;
	}
	
}
