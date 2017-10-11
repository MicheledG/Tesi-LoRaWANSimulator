package it.polito.mdg.lorawan.simulator;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.mdg.lorawan.simulator.model.Config;
import it.polito.mdg.lorawan.simulator.model.ResultInformation;
import it.polito.mdg.lorawan.simulator.model.SimulationResult;
import it.polito.mdg.lorawan.simulator.model.SimulationSummary;
import it.polito.mdg.lorawan.simulator.modules.logical.Packet;
import it.polito.mdg.lorawan.simulator.modules.physical.EndDevice;
import it.polito.mdg.lorawan.simulator.modules.physical.Gateway;
import it.polito.mdg.lorawan.simulator.util.ApplicationsProfiler;
import it.polito.mdg.lorawan.simulator.util.Configurator;
import it.polito.mdg.lorawan.simulator.util.EndDeviceDeployer;
import it.polito.mdg.lorawan.simulator.util.EndDeviceScheduler;
import it.polito.mdg.lorawan.simulator.util.ResultInformationCrawler;
import it.polito.mdg.lorawan.simulator.util.ResultsWriter;
import it.polito.mdg.lorawan.simulator.util.SummaryCrawler;
import it.polito.mdg.lorawan.simulator.util.SummaryWriter;

public class Simulator {
	
	private static final int DEFAULT_STEP = 100;
	
	public static void main(String[] args) {
		
		System.out.println("======================");
		System.out.println("SIM: configuring simulator...");
		System.out.println("======================");
		
		String configurationFileName = null;
		String resultsFileName = null;
		String summaryFileName = null;
		int numberOfSimulations = 1;
		int minDevices = -1;
		int step = 0;
		boolean printSingleSimulationResults = true;
		
		if(args.length > 0){
			configurationFileName = args[0];
		}
		if(args.length > 1){
			resultsFileName = args[1];
		}
		if(args.length > 2){
			summaryFileName = args[2];
		}
		if(args.length > 3){
			numberOfSimulations = Integer.parseInt(args[3]);
			printSingleSimulationResults = false;
		}
		if(args.length > 4){
			minDevices = Integer.parseInt(args[4]);
			step = DEFAULT_STEP;
		}
		if(args.length > 5){
			step = Integer.parseInt(args[5]);
		}
		if(args.length > 6){
			printSingleSimulationResults = Boolean.parseBoolean(args[6]);
		}
		
		
		//configure the simulator
		Config userConfig;		
		try {
			userConfig = Configurator.configSimulator(configurationFileName);
		} catch (IOException e) {
			System.err.println("Impossible to configure the simulator because: "+e.getMessage());
			System.err.println("Simulation aborted!");
			return;
		}
		
		System.out.println("======================");
		System.out.println("SIM: configuration completed!");
		System.out.println("======================");
		
		//compute the total area covered
		double area = Math.PI*Math.pow(userConfig.getRange(), 2.0);
				
		//create the requested configurations for the simulations
		List<Config> simulationConfigurations = new ArrayList<>();
		if(minDevices > 0 && minDevices < userConfig.getEndDeviceNumber()){
			//there are multiple configurations to create to execute simulations with different end device number
			int numberOfConfiguration = userConfig.getEndDeviceNumber() / step;
			int numberOfEndDevice = minDevices;
			for(int i = 0; i <= numberOfConfiguration; i++){				
				Config config = new Config(userConfig);
				config.setEndDeviceNumber(numberOfEndDevice);
				simulationConfigurations.add(config);
				numberOfEndDevice += step;
				
			}			
		}
		else{
			simulationConfigurations.add(userConfig);
		}
		
		//for each configuration execute the simulation the requested number of times
		List<SimulationSummary> simulationSummaries = new ArrayList<>();
		for(Config config: simulationConfigurations){
		
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
			
			int simulationNumber;
			List<SimulationResult> simulationResults = new ArrayList<>();
			
			for(simulationNumber = 0; simulationNumber < numberOfSimulations; simulationNumber++){
				System.out.println("======================");
				System.out.println("SIM: starting simulation number: "+simulationNumber);	
				System.out.println("======================");
				
				Instant simulationStart = Instant.now();
				
				System.out.println("======================");
				System.out.println("SIM: deploying the end devices...");
				System.out.println("======================");
				
				
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
					System.err.println("Impossible to deploy the end devices because: "+e.getMessage());
					System.err.println("Simulation aborted!");
					return;
				}
				
				
				
				System.out.println("======================");
				System.out.println("SIM: deployment completed!");
				System.out.println("======================");
				
				//let the end devices send all their packets
				EndDeviceScheduler.scheduleEndDevices(applicationPackets, endDevices, config.getTime());
				
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
				List<Packet> receivedPackets = gw.receivePackets(sentPackets, config.getCollisionAlgorithm());
				
				//collect only the decoded packets among the received
				List<Packet> decodedPackets = gw.decodePackets(receivedPackets);
				
				//collect the results! :D
				Instant simulationEnd = Instant.now();
				Duration simulationTime = Duration.between(simulationStart, simulationEnd); 
				
				SimulationResult simulationResult = new SimulationResult();
				simulationResult.setConfiguration(config);
				simulationResult.setEffectiveDeployedEndDevices(effectiveEndDevicesNumber);
				simulationResult.setSimulationTime(simulationTime);
					
				ResultInformation overallResult = new ResultInformation();
				overallResult.setResultSet("overall");
				overallResult.setSentPackets(sentPackets.size());
				overallResult.setReceivedPackets(receivedPackets.size());
				overallResult.setDecodedPackets(decodedPackets.size());
				overallResult.setEndDevices(effectiveEndDevicesNumber);
				
				//print to the user the overall results
				System.out.println("======================");
				System.out.println("SIM: completed simulation number: "+simulationNumber);
				System.out.println("SIM: simulation time: " + simulationResult.getSimulationTime().toString());
				System.out.println("SIM: number of deployed end devices: "+ effectiveEndDevicesNumber);
				System.out.println("SIM: number of sent packets: "+ overallResult.getSentPackets());
				System.out.println("SIM: number of received packets: "+ overallResult.getReceivedPackets());		
				System.out.println("SIM: received ratio: "+ (int)(overallResult.getReceivedRatio()) +"%");
				System.out.println("SIM: number of decoded packets: "+ overallResult.getDecodedPackets());		
				System.out.println("SIM: decoded ratio: "+ (int)(overallResult.getDecodedRatio()) +"%");
				System.out.println("SIM: data extraction ratio (DER): "+ (int)(overallResult.getDataExtractionRatio()) +"%");
				System.out.println("SIM: check out results file for deeper analysis!");
				System.out.println("======================");
				
				System.out.println("======================");
				System.out.println("SIM: collecting simulation complete results...");
				System.out.println("======================");
				
				//collect all the other results
				List<ResultInformation> results = ResultInformationCrawler
						.crawlResutlInformation(
								sentPackets,
								receivedPackets,
								decodedPackets,
								config.getApplications(),
								config.getDataRates(),
								config.getChannelNumber());
				
				results.add(overallResult);
				simulationResult.setResults(results);
				
				System.out.println("======================");
				System.out.println("SIM: results collection completed!");
				System.out.println("======================");
				
				simulationResults.add(simulationResult);
				
				if(printSingleSimulationResults){
					System.out.println("======================");
					System.out.println("SIM: writing results in results file...");
					System.out.println("======================");
					
					//write the results into the results file
					try {
						ResultsWriter.writeResults(simulationResult, resultsFileName);
					} catch (IOException e) {
						System.err.println("Impossible to write the results in the designated file because: "+e.getMessage());
						return;
					}
					
					System.out.println("======================");
					System.out.println("SIM: results of simulation number "+simulationNumber+" have been written!");
					System.out.println("======================");
				}
			}
			
			System.out.println("======================");
			System.out.println("SIM: collecting simulations summary...");
			System.out.println("======================");
			
			SimulationSummary simulationSummary = SummaryCrawler.crawlSummary(config, simulationResults);
			simulationSummaries.add(simulationSummary);
			
			System.out.println("======================");
			System.out.println("SIM: summary of the simulations:");
			System.out.println("SIM: number of executed simulations: "+simulationNumber);		
			System.out.println("SIM: average DER: "+(int)(simulationSummary.getStatisticsResults().get(0).getAverage())+"%");
			System.out.println("======================");						
		}
		
				
		System.out.println("======================");
		System.out.println("SIM: writing summaries in summary file...");
		System.out.println("======================");
		
		try {
			SummaryWriter.writeResults(simulationSummaries, summaryFileName);
		} catch (IOException e) {
			System.err.println("Impossible to write the summary in the designated file because: "+e.getMessage());
			return;
		}
		
		System.out.println("======================");
		System.out.println("SIM: summary has been written!");
		System.out.println("SIM: bye bye! :D");
		System.out.println("======================");
		
		return;
		
	}
		

}
