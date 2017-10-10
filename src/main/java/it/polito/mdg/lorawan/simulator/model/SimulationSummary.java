package it.polito.mdg.lorawan.simulator.model;

import java.util.List;

import it.polito.mdg.lorawan.simulator.model.statistics.StatisticsResult;

public class SimulationSummary implements Comparable<SimulationSummary>{

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
	
	@Override
	public int compareTo(SimulationSummary o) {
		
		Integer thisEffectiveDeployedEndDevice = this.getConfiguration().getEndDeviceNumber();
		Integer thatEffectiveDeployedEndDevice = o.getConfiguration().getEndDeviceNumber();
		
		return thisEffectiveDeployedEndDevice.compareTo(thatEffectiveDeployedEndDevice);
	}
	
}
