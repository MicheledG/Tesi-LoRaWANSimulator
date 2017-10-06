package it.polito.mdg.lorawan.simulator.util;

import java.util.List;
import java.util.Map;

import it.polito.mdg.lorawan.simulator.modules.physical.EndDevice;

public class EndDeviceScheduler {
	
	public static void scheduleEndDevices(Map<Integer, Integer> applicationPackets, List<EndDevice> endDevices){
		
		//let each end device send all the packets that it must send
		int appId = 0;
		int packetsToSend = 0;
		for (EndDevice endDevice: endDevices) {
			appId = endDevice.getAppId();
			packetsToSend = applicationPackets.get(appId);
			endDevice.sendNextNPacket(packetsToSend);
		}
		
	}
	
}
