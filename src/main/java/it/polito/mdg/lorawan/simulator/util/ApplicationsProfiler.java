package it.polito.mdg.lorawan.simulator.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.mdg.lorawan.simulator.modules.logical.Application;

public class ApplicationsProfiler {

	//returns the total application coverage rate in terms of expected end devices and deployed end devices
	public static double computeApplicationCoverageRate(List<Application> applications, double area, int N){
		
		double expectedEndDevices = 0.0;
		double totalEndDeviceDensity = 0.0;
		
		for (Application application : applications) {
			totalEndDeviceDensity += application.getDeviceDensity();
		}
		
		expectedEndDevices = (int) (area * totalEndDeviceDensity);
		
		double applicationCoverageRate = ((double) N)/expectedEndDevices;
		
		return applicationCoverageRate;
	}
	
	//returns the number of end devices to deploy for each application
	public static Map<Integer, Integer>  computeApplicationEndDevices(List<Application> applications, int N){
		
		double totalEndDeviceDensity = 0.0;
		
		for (Application application : applications) {
			totalEndDeviceDensity += application.getDeviceDensity();
		}
				
		Map<Integer, Integer> applicationEndDevices = new HashMap<>();		
		int appId;
		double applicationDeviceDensity;
		double relativeApplicationDeviceDensity;
		int applicationEndDevice;
		
		for (Application application: applications) {
			appId = application.getAppId();
			applicationDeviceDensity = application.getDeviceDensity();
			relativeApplicationDeviceDensity = applicationDeviceDensity/totalEndDeviceDensity;
			applicationEndDevice = (int) (N * relativeApplicationDeviceDensity);
			applicationEndDevices.put(appId, applicationEndDevice);
		}
		
		return applicationEndDevices;
		
	}

	public static Map<Integer, Integer> computeApplicationPackets(List<Application> applications, int time) {
		
				
		Map<Integer, Integer> applicationPackets = new HashMap<>();		
		int appId;
		int packetsNumber;
		double intervalsNumber;
		
		for (Application application: applications) {
			appId = application.getAppId();
			if(application.getMessages() == -1){
				//application that send periodic messages
				packetsNumber = (int) ((time * 60) / (application.getAppInterval()));
			}
			else{
				//application that send random messages
				intervalsNumber = ((double)(time * 60)) / (application.getAppInterval());
				packetsNumber = (int) intervalsNumber * application.getMessages();
				
			}
			applicationPackets.put(appId, packetsNumber);
		}
		
		return applicationPackets;
	}
	
}
