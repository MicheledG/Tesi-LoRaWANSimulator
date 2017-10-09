package it.polito.mdg.lorawan.simulator.model;

import java.util.List;

import it.polito.mdg.lorawan.simulator.model.statistics.StatisticsResult;

public class SimulationSummary {

	private Config configuration;
	private List<StatisticsResult> statisticsResults;
	public Config getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Config configuration) {
		this.configuration = configuration;
	}
	public List<StatisticsResult> getStatisticsResults() {
		return statisticsResults;
	}
	public void setStatisticsResults(List<StatisticsResult> statisticsResults) {
		this.statisticsResults = statisticsResults;
	}
	
}
