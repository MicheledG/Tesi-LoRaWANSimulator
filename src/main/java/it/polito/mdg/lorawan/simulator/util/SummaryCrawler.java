package it.polito.mdg.lorawan.simulator.util;

import java.util.ArrayList;
import java.util.List;

import it.polito.mdg.lorawan.simulator.model.Config;
import it.polito.mdg.lorawan.simulator.model.ResultInformation;
import it.polito.mdg.lorawan.simulator.model.SimulationResult;
import it.polito.mdg.lorawan.simulator.model.SimulationSummary;
import it.polito.mdg.lorawan.simulator.model.statistics.StatisticsResult;
import it.polito.mdg.lorawan.simulator.util.statistics.StatisticsCalculator;

public class SummaryCrawler {

	private final static String DEFAULT_RESULT_SET = "overall";
	
	public static SimulationSummary crawlSummary(Config configuration, List<SimulationResult> simulationResults){
				
		int numberOfSimulation = simulationResults.size();
		double dataExtractionRates[] = new double[numberOfSimulation]; 
		
		//collect all the data extraction ratio from all the overall results of each simulation
		for (int i = 0; i < numberOfSimulation; i++) {
			SimulationResult simulationResult = simulationResults.get(i);	
			for (ResultInformation resultInformation: simulationResult.getResults()) {
				if(resultInformation.getResultSet() instanceof String){
					String resultSet = (String) resultInformation.getResultSet();
					if(resultSet.equals(DEFAULT_RESULT_SET)){
						dataExtractionRates[i] = resultInformation.getDataExtractionRatio();
					}
				}
			}
		}
		
		double derAvg = StatisticsCalculator.computeAverage(dataExtractionRates);
		double derVariance = StatisticsCalculator.computeVariance(dataExtractionRates);
		double derStdDev = StatisticsCalculator.computeStdDev(dataExtractionRates);
		
		List<StatisticsResult> statisticsResults = new ArrayList<>();
		StatisticsResult statisticsResult = new StatisticsResult(
				"dataExtractionRatio",
				numberOfSimulation,
				derAvg,
				derVariance,
				derStdDev);
		statisticsResults.add(statisticsResult);
		
		SimulationSummary simulationSummary = new SimulationSummary();
		simulationSummary.setConfiguration(configuration);				
		simulationSummary.setStatisticsResults(statisticsResults);
		
		return simulationSummary;
	}
	
}
