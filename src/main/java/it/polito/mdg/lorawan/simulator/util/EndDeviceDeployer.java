package it.polito.mdg.lorawan.simulator.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.polito.mdg.lorawan.simulator.modules.logical.Application;
import it.polito.mdg.lorawan.simulator.modules.logical.DataRate;
import it.polito.mdg.lorawan.simulator.modules.physical.EndDevice;

public class EndDeviceDeployer {

	public static List<EndDevice> deployEndDevices(
			List<Application> applications,
			Map<Integer, Integer> applicationEndDevices,
			int channelsNumber,
			int rssiMax,
			double maxPathLoss,
			double dc,
			List<DataRate> availableDataRates
			) throws Exception {
		
		//create random distribution of end devices in the area and random channel assignments
		int N = 0;
		for (Map.Entry<Integer, Integer> applicationEntry: applicationEndDevices.entrySet()) {
			N += applicationEntry.getValue();
		}
		
		double rssi[] = new double[N];
		int channel[] = new int[N];
		
		double deltaRssi = rssiMax - maxPathLoss;
		for(int i = 0; i < N; i++){
			rssi[i] = (Math.random() * deltaRssi) - maxPathLoss;
			channel[i] = (int) (Math.random() * channelsNumber); //be aware of approximation of interval to integer
		}
		
		List<EndDevice> deployedEndDevices = new ArrayList<>();		
		int devId = 0;
		int endDevicesNumber;
		for (Application application: applications) {
			endDevicesNumber = applicationEndDevices.get(application.getAppId());
			for(int i = 0; i < endDevicesNumber; i++){
				EndDevice endDeviceToDeploy = configureEndDevice(
						devId,
						application,
						channel[devId],
						dc,
						rssi[devId],
						availableDataRates
						);
				deployedEndDevices.add(endDeviceToDeploy);
				devId++;
			}
		}	
		
		return deployedEndDevices;
	}
	
	private static EndDevice configureEndDevice(
			int devId,
			Application application,
			int channel,
			double dc,
			double rssi,
			List<DataRate> availableDataRates
			) throws Exception{
		
		//sort the available sensitivities from the one that guarantees the shortest range
		//to the one that guarantees longest range
		Collections.sort(availableDataRates);
		
		//choose the data rate to minimize air time (but still guarantees coverage)
		DataRate choosenDr = null;
		for (DataRate dataRate : availableDataRates) {
			if(rssi > dataRate.getMinSensitivity()){
				choosenDr = dataRate;
				break;
			}
		}
		
		if(choosenDr == null){
			throw new Exception("impossible to assign a suitable data rate for end device with rssi: "+rssi);
		}
		
		EndDevice endDevice = new EndDevice(devId, application, channel, choosenDr, dc, rssi, Math.random());
		
		return endDevice;
	}
	
	
	
}
