package it.polito.mdg.lorawan.simulator.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.mdg.lorawan.simulator.model.ResultInformation;
import it.polito.mdg.lorawan.simulator.modules.logical.Application;
import it.polito.mdg.lorawan.simulator.modules.logical.DataRate;
import it.polito.mdg.lorawan.simulator.modules.logical.Packet;

public class ResultInformationCrawler {

	public static List<ResultInformation> crawlResutlInformation(
			List<Packet> sentPackets,
			List<Packet> receivedPackets,
			List<Packet> decodedPackets,
			List<Application> applications,
			List<DataRate> dataRates,
			int channelNumbers
			){
		
		
		List<ResultInformation> results = new ArrayList<>();
		
		//collect by applications
		Map<Integer, ResultInformation> applicationsResults = new HashMap<>();
		Map<Integer, Set<Integer>> applicationsEndDevices = new HashMap<>();
		for(Application application: applications){
			ResultInformation result = new ResultInformation();
			result.setResultSet(application);
			applicationsResults.put(application.getAppId(), result);
			applicationsEndDevices.put(application.getAppId(), new HashSet<>());
		}
		
		int appId;
		int devId;
		int totPackets;
		Set<Integer> applicationEndDevices;
		//analyze sent packets
		for (Packet sentPacket : sentPackets) {
			appId = sentPacket.getAppId();
			devId = sentPacket.getDevId();
			totPackets = applicationsResults.get(appId).getSentPackets() + 1;
			applicationsResults.get(appId).setSentPackets(totPackets);
			applicationEndDevices = applicationsEndDevices.get(appId);
			if(!applicationEndDevices.contains(devId)){
				applicationEndDevices.add(devId);
			}
		}
		
		int numberEndDevicesPerApp;
		for (Map.Entry<Integer, Set<Integer>> applicationsEndDevicesEntry: applicationsEndDevices.entrySet()) {
			appId = applicationsEndDevicesEntry.getKey();
			numberEndDevicesPerApp = applicationsEndDevicesEntry.getValue().size();
			applicationsResults.get(appId).setEndDevices(numberEndDevicesPerApp);
		}
		
		//analyze received packets
		for (Packet receivedPacket : receivedPackets) {
			appId = receivedPacket.getAppId();
			totPackets = applicationsResults.get(appId).getReceivedPackets() + 1;
			applicationsResults.get(appId).setReceivedPackets(totPackets);
		}
		
		//analyze decoded packets
		for (Packet decodedPacket : decodedPackets) {
			appId = decodedPacket.getAppId();
			totPackets = applicationsResults.get(appId).getDecodedPackets() + 1;
			applicationsResults.get(appId).setDecodedPackets(totPackets);
		}
		
		for (Map.Entry<Integer, ResultInformation> applicationsResultsEntry: applicationsResults.entrySet()) {
			results.add(applicationsResultsEntry.getValue());
		}
		
		//collect by data rates
		Map<DataRate, ResultInformation> dataRatesResults = new HashMap<>();
		Map<DataRate, Set<Integer>> dataRatesEndDevices = new HashMap<>();
		for(DataRate dataRate: dataRates){
			ResultInformation result = new ResultInformation();
			result.setResultSet(dataRate);
			dataRatesResults.put(dataRate, result);
			dataRatesEndDevices.put(dataRate, new HashSet<>());
		}
		
		DataRate dataRate;
		Set<Integer> dataRateEndDevices;
		//analyze sent packets
		for (Packet sentPacket : sentPackets) {
			dataRate = sentPacket.getDr();
			devId = sentPacket.getDevId();
			totPackets = dataRatesResults.get(dataRate).getSentPackets() + 1;
			dataRatesResults.get(dataRate).setSentPackets(totPackets);
			dataRateEndDevices = dataRatesEndDevices.get(dataRate);
			if(!dataRateEndDevices.contains(devId)){
				dataRateEndDevices.add(devId);
			}
		}
		
		int numberEndDevicesPerDataRate;
		for (Map.Entry<DataRate, Set<Integer>> dataRatesEndDevicesEntry: dataRatesEndDevices.entrySet()) {
			dataRate = dataRatesEndDevicesEntry.getKey();
			numberEndDevicesPerDataRate= dataRatesEndDevicesEntry.getValue().size();
			dataRatesResults.get(dataRate).setEndDevices(numberEndDevicesPerDataRate);
		}
		
		//analyze received packets
		for (Packet receivedPacket : receivedPackets) {
			dataRate = receivedPacket.getDr();
			totPackets = dataRatesResults.get(dataRate).getReceivedPackets() + 1;
			dataRatesResults.get(dataRate).setReceivedPackets(totPackets);
		}
		
		//analyze decoded packets
		for (Packet decodedPacket : decodedPackets) {
			dataRate = decodedPacket.getDr();
			totPackets = dataRatesResults.get(dataRate).getDecodedPackets() + 1;
			dataRatesResults.get(dataRate).setDecodedPackets(totPackets);
		}
		
		for (Map.Entry<DataRate, ResultInformation> dataRatesResultsEntry: dataRatesResults.entrySet()) {
			results.add(dataRatesResultsEntry.getValue());
		}
		
		//collect by channels
		Map<Integer, ResultInformation> channelsResults = new HashMap<>();
		Map<Integer, Set<Integer>> channelsEndDevices = new HashMap<>();
		for(int i = 0; i < channelNumbers; i++){
			ResultInformation result = new ResultInformation();
			result.setResultSet("channel "+i);
			channelsResults.put(i, result);
			channelsEndDevices.put(i, new HashSet<>());
		}	
		
		
		int channel;
		Set<Integer> channelEndDevices;
		//analyze sent packets
		for (Packet sentPacket : sentPackets) {
			channel = sentPacket.getChannel();
			devId = sentPacket.getDevId();
			totPackets = channelsResults.get(channel).getSentPackets() + 1;
			channelsResults.get(channel).setSentPackets(totPackets);
			channelEndDevices = channelsEndDevices.get(channel);
			if(!channelEndDevices.contains(devId)){
				channelEndDevices.add(devId);
			}
		}
		
		int numberEndDevicesPerChannel;
		for (Map.Entry<Integer, Set<Integer>> channelsEndDevicesEntry: channelsEndDevices.entrySet()) {
			channel = channelsEndDevicesEntry.getKey();
			numberEndDevicesPerChannel = channelsEndDevicesEntry.getValue().size();
			channelsResults.get(channel).setEndDevices(numberEndDevicesPerChannel);
		}
		
		//analyze received packets
		for (Packet receivedPacket : receivedPackets) {
			channel = receivedPacket.getChannel();
			totPackets = channelsResults.get(channel).getReceivedPackets() + 1;
			channelsResults.get(channel).setReceivedPackets(totPackets);
		}
		
		//analyze decoded packets
		for (Packet decodedPacket : decodedPackets) {
			channel = decodedPacket.getChannel();
			totPackets = channelsResults.get(channel).getDecodedPackets() + 1;
			channelsResults.get(channel).setDecodedPackets(totPackets);
		}
		
		for (Map.Entry<Integer, ResultInformation> channelsResultsEntry: channelsResults.entrySet()) {
			results.add(channelsResultsEntry.getValue());
		}
		
		return results;
	}
	
}
