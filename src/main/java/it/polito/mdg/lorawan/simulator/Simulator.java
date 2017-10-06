package it.polito.mdg.lorawan.simulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.mdg.lorawan.simulator.model.Config;
import it.polito.mdg.lorawan.simulator.model.Results;
import it.polito.mdg.lorawan.simulator.modules.logical.Packet;
import it.polito.mdg.lorawan.simulator.modules.physical.EndDevice;
import it.polito.mdg.lorawan.simulator.modules.physical.Gateway;
import it.polito.mdg.lorawan.simulator.util.ApplicationsProfiler;
import it.polito.mdg.lorawan.simulator.util.Configurator;
import it.polito.mdg.lorawan.simulator.util.EndDeviceDeployer;
import it.polito.mdg.lorawan.simulator.util.EndDeviceScheduler;
import it.polito.mdg.lorawan.simulator.util.ResultsWriter;

public class Simulator {

	public static void main(String[] args) {
		
		String configurationFileName = null;
		String resultsFileName = null;
		
		if(args.length > 0){
			configurationFileName = args[0];
		}
		if(args.length > 1){
			configurationFileName = args[1];
		}
		
		//configure the simulator
		Config config;		
		try {
			config = Configurator.configSimulator(configurationFileName);
		} catch (IOException e) {
			System.err.println("Impossible to configure the simulator because: "+e.getMessage());
			System.err.println("Simulation aborted!");
			return;
		}
		
		//compute the total area covered
		double area = Math.PI*Math.pow(config.getRange(), 2.0);
		
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
		
		//deploy the end devices required for each application in the configurated area (random positions)
		List<EndDevice> endDevices;
		try {
			endDevices = EndDeviceDeployer.deployEndDevices(
					config.getApplications(),
					applicationEndDevices,
					config.getChannelNumber(),
					config.getTxPower(), 
					config.getRange(),
					config.getGwHeight(),
					config.getEdHeight(),
					config.getFrequency(),
					config.getDutyCycle(),
					config.getDataRates()
					);
		} catch (Exception e) {
			System.err.println("Impossible to deplot the end devices because: "+e.getMessage());
			System.err.println("Simulation aborted!");
			return;
		}
		
		//let the end devices send all their packets
		EndDeviceScheduler.scheduleEndDevices(applicationPackets, endDevices);
		
		//collect the sent packets
		List<Packet> sentPackets = new ArrayList<>();
		for (EndDevice endDevice : endDevices) {
			List<Packet> endDeviceSentPackets = endDevice.getSentPackets();
			for (Packet packet : endDeviceSentPackets) {
				sentPackets.add(packet);
			}
		}
		
		//instantiate the gateway
		Gateway gw = new Gateway(config.getDecodingPath());
		
		//let the sent packets reach the gateway
		List<Packet> receivedPackets = gw.receivePackets(sentPackets);
		
		//collect only the decoded packets among the received
		List<Packet> decodedPackets = gw.decodePackets(receivedPackets);
		
		//write the results into the results file
		Results results = new Results();
		try {
			ResultsWriter.writeResults(results, resultsFileName);
		} catch (IOException e) {
			System.err.println("Impossible to write the results in the designated file because: "+e.getMessage());
			return;
		}
		
		return;
		
	}
		

}
