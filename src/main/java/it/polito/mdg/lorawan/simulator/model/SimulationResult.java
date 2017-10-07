package it.polito.mdg.lorawan.simulator.model;

import java.time.Duration;
import java.util.List;

public class SimulationResult {

	private Config configuration;
	private int effectiveDeployedEndDevices;
	private Duration simulationTime;
	private List<ResultInformation> results;
	public Config getConfiguration() {
		return configuration;
	}
	public void setConfiguration(Config configuration) {
		this.configuration = configuration;
	}
	public int getEffectiveDeployedEndDevices() {
		return effectiveDeployedEndDevices;
	}
	public void setEffectiveDeployedEndDevices(int effectiveDeployedEndDevices) {
		this.effectiveDeployedEndDevices = effectiveDeployedEndDevices;
	}
	public Duration getSimulationTime() {
		return simulationTime;
	}
	public void setSimulationTime(Duration simulationTime) {
		this.simulationTime = simulationTime;
	}
	public List<ResultInformation> getResults() {
		return results;
	}
	public void setResults(List<ResultInformation> results) {
		this.results = results;
	}	
	
}
