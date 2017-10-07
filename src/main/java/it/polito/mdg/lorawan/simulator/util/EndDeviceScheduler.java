package it.polito.mdg.lorawan.simulator.util;

import java.util.List;
import java.util.Map;

import it.polito.mdg.lorawan.simulator.modules.physical.EndDevice;

public class EndDeviceScheduler {
	
	public static void scheduleEndDevices(Map<Integer, Integer> applicationPackets, List<EndDevice> endDevices){
		
		double scheduledEndDeviceRatio;
		int difference;
		int scheduledEndDevicePercent = 0;
		int i = 0;
		int totEndDevices = endDevices.size();
		
		System.out.println("======================");		
		System.out.println("SCH: number of end devices to schedule: "+totEndDevices);
		System.out.println("SCH: scheduling phase starting!");
		System.out.println("======================");
		
		//let each end device send all the packets that it must send
		int appId = 0;
		int packetsToSend = 0;		
		
		for (EndDevice endDevice: endDevices) {
			appId = endDevice.getAppId();
			packetsToSend = applicationPackets.get(appId);
			endDevice.sendNextNPacket(packetsToSend);
			//notify the user with the progress
			scheduledEndDeviceRatio = ((double)(i+1))/((double)(totEndDevices));
			difference = (int) (scheduledEndDeviceRatio * 100) - scheduledEndDevicePercent;
			if(difference > 0){
				scheduledEndDevicePercent += difference;
				System.out.println("======================");		
				System.out.println("SCH: scheduled end devices percent: "+scheduledEndDevicePercent+"%");
				System.out.println("======================");
			}
			i++;
		}
		

		System.out.println("======================");		
		System.out.println("SCH: scheduling phase completed!");
		System.out.println("SCH: number of scheduled end devices: "+i);		
		System.out.println("======================");
		
	}
}
