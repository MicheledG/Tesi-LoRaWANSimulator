package it.polito.mdg.lorawan.simulator;

import java.io.IOException;
import java.util.Map;

import it.polito.mdg.lorawan.simulator.model.Config;
import it.polito.mdg.lorawan.simulator.model.Results;
import it.polito.mdg.lorawan.simulator.util.ApplicationsProfiler;
import it.polito.mdg.lorawan.simulator.util.Configurator;
import it.polito.mdg.lorawan.simulator.util.PathLossCalculator;
import it.polito.mdg.lorawan.simulator.util.ResultsWriter;

public class Simulator {

	public static void main(String[] args) {
		
		//configure the simulator
		Config config;		
		try {
			config = Configurator.configSimulator(args[0]);
		} catch (IOException e) {
			System.err.println("Impossible to configure the simulator because: "+e.getMessage());
			System.err.println("Simulation aborted!");
			return;
		}
		
		//compute the total area covered
		double area = Math.PI*Math.pow(config.getRange(), 2.0);
		
		//compute the max path loss
		double maxPathLoss = PathLossCalculator.computeHataOkumura(
				config.getGwHeight(),
				config.getEdHeight(),
				config.getFrequency(),
				config.getRange());
		
		//compute the coverage of the application needs in terms of end device deployed
		double applicationCoverageRate = ApplicationsProfiler
				.computeApplicationCoverageRate(
						config.getApplications(),
						area, 
						config.getEndDeviceNumber());
		
		//compute the number of end devices deployed for each applcation
		Map<Integer, Integer> applicationEndDevices = ApplicationsProfiler
				.computeApplicationEndDevices(
						config.getApplications(),
						config.getEndDeviceNumber()); 
		
		//compute the effective number of end devices to deploy
		//can be different from the configurated value since approximations
		int effectiveEndDevicesNumber = 0;
		for (Map.Entry<Integer, Integer> applicationEntry: applicationEndDevices.entrySet()) {
			effectiveEndDevicesNumber += applicationEntry.getValue();
		}
		
		//compute the number of packets that each end device of an applications has to send in the configurated amount of time
		Map<Integer, Integer> applicationPackets = ApplicationsProfiler
				.computeApplicationPackets(
						config.getApplications(),
						config.getTime());
		
		//write the results into the results file
		Results results = new Results();
		try {
			ResultsWriter.writeResults(results, args[1]);
		} catch (IOException e) {
			System.err.println("Impossible to write the results in the designated file because: "+e.getMessage());
			return;
		}
		
		return;
		
	}
		

}
