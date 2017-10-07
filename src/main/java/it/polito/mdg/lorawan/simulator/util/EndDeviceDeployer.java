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
			double txPower,
			double maxDistance,
			double heightGw,
			double heightEd,
			double frequency,
			double dc,
			List<DataRate> availableDataRates
			) throws Exception {
		
		
		//create random distribution of end devices in the area and random channel assignments
		int N = 0;
		for (Map.Entry<Integer, Integer> applicationEntry: applicationEndDevices.entrySet()) {
			N += applicationEntry.getValue();
		}
		
		System.out.println("======================");		
		System.out.println("DEP: deployment phase starting!");
		System.out.println("DEP: number of end devices to deploy: "+N);
		System.out.println("======================");
		
		System.out.println("======================");		
		System.out.println("DEP: creating random parameters of distance, rssi and channel for end devices!");
		System.out.println("======================");
		
		//sort the available sensitivities from the one that guarantees the shortest range
		//to the one that guarantees longest range
		Collections.sort(availableDataRates);
		
		double distances[] = new double[N];
		double rssi[] = new double[N];
		int channels[] = new int[N];
		
		for(int i = 0; i < N; i++){
			distances[i] = (Math.random() * maxDistance);
			rssi[i] = txPower + PathLossCalculator
					.computeHataOkumura(heightGw, heightEd, frequency, distances[i]);
			channels[i] = (int) (Math.random() * channelsNumber); //be aware of approximation of interval to integer
		}
		
		System.out.println("======================");		
		System.out.println("DEP: parameters creation completed!");
		System.out.println("DEP: end devices configuration starting!");
		System.out.println("======================");
		
		double deployedEndDeviceRatio;
		int difference;
		int deployedEndDevicePercent = 0;
		
		List<EndDevice> deployedEndDevices = new ArrayList<>();		
		int devId = 0;
		int endDevicesNumber;
		for (Application application: applications) {
			endDevicesNumber = applicationEndDevices.get(application.getAppId());
			for(int i = 0; i < endDevicesNumber; i++){
				EndDevice endDeviceToDeploy = configureEndDevice(
						devId,
						application,
						channels[devId],
						dc,
						distances[devId],
						rssi[devId],
						availableDataRates
						);
				deployedEndDevices.add(endDeviceToDeploy);
				
				//notify the user with the progress
				deployedEndDeviceRatio = ((double)(devId+1))/((double)(N));
				difference = (int) (deployedEndDeviceRatio * 100) - deployedEndDevicePercent;
				if(difference > 0){
					deployedEndDevicePercent += difference;
					System.out.println("======================");		
					System.out.println("DEP: deployed end devices percent: "+deployedEndDevicePercent+"%");
					System.out.println("======================");
				}
				
				devId++;
			}
		}
		
		System.out.println("======================");		
		System.out.println("DEP: deployment phase completed!");
		System.out.println("DEP: number of deployed end devices: "+devId);		
		System.out.println("======================");
		
		
		return deployedEndDevices;
	}
	
	private static EndDevice configureEndDevice(
			int devId,
			Application application,
			int channel,
			double dc,
			double distance,
			double rssi,
			List<DataRate> availableDataRates //WARNING!!! MUST BE ALREADY SORTED
			) throws Exception{
				
		
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
		
		EndDevice endDevice = new EndDevice(devId, application, channel, choosenDr, dc, distance, rssi, Math.random());
		
		return endDevice;
	}
	
	
	
}
