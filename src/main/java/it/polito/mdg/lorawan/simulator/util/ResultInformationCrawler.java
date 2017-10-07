package it.polito.mdg.lorawan.simulator.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<Integer, ResultInformation> applicationResults = new HashMap<>();
		for(Application application: applications){
			ResultInformation result = new ResultInformation();
			result.setResultSet(application);
			applicationResults.put(application.getAppId(), result);
		}
		
		int appId;
		int totPackets;
		//analyze sent packets
		for (Packet sentPacket : sentPackets) {
			appId = sentPacket.getAppId();
			totPackets = applicationResults.get(appId).getSentPackets() + 1;
			applicationResults.get(appId).setSentPackets(totPackets);
		}
		
		//analyze received packets
		for (Packet receivedPacket : receivedPackets) {
			appId = receivedPacket.getAppId();
			totPackets = applicationResults.get(appId).getReceivedPackets() + 1;
			applicationResults.get(appId).setReceivedPackets(totPackets);
		}
		
		//analyze decoded packets
		for (Packet decodedPacket : decodedPackets) {
			appId = decodedPacket.getAppId();
			totPackets = applicationResults.get(appId).getDecodedPackets() + 1;
			applicationResults.get(appId).setDecodedPackets(totPackets);
		}
		
		for (Map.Entry<Integer, ResultInformation> applicationResultsEntry: applicationResults.entrySet()) {
			results.add(applicationResultsEntry.getValue());
		}
		
		//collect by data rates
		Map<DataRate, ResultInformation> dataRateResults = new HashMap<>();
		for(DataRate dataRate: dataRates){
			ResultInformation result = new ResultInformation();
			result.setResultSet(dataRate);
			dataRateResults.put(dataRate, result);
		}
		
		DataRate dataRate;
		//analyze sent packets
		for (Packet sentPacket : sentPackets) {
			dataRate = sentPacket.getDr();
			totPackets = dataRateResults.get(dataRate).getSentPackets() + 1;
			dataRateResults.get(dataRate).setSentPackets(totPackets);
		}
		
		//analyze received packets
		for (Packet receivedPacket : receivedPackets) {
			dataRate = receivedPacket.getDr();
			totPackets = dataRateResults.get(dataRate).getReceivedPackets() + 1;
			dataRateResults.get(dataRate).setReceivedPackets(totPackets);
		}
		
		//analyze decoded packets
		for (Packet decodedPacket : decodedPackets) {
			dataRate = decodedPacket.getDr();
			totPackets = dataRateResults.get(dataRate).getDecodedPackets() + 1;
			dataRateResults.get(dataRate).setDecodedPackets(totPackets);
		}
		
		for (Map.Entry<DataRate, ResultInformation> dataRateResultsEntry: dataRateResults.entrySet()) {
			results.add(dataRateResultsEntry.getValue());
		}
		
		//collect by channels
		Map<Integer, ResultInformation> channelResults = new HashMap<>();
		for(int i = 0; i < channelNumbers; i++){
			ResultInformation result = new ResultInformation();
			result.setResultSet("channel "+i);
			channelResults.put(i, result);
		}
		
		int channel;
		//analyze sent packets
		for (Packet sentPacket : sentPackets) {
			channel = sentPacket.getChannel();
			totPackets = channelResults.get(channel).getSentPackets() + 1;
			channelResults.get(channel).setSentPackets(totPackets);
		}
		
		//analyze received packets
		for (Packet receivedPacket : receivedPackets) {
			channel = receivedPacket.getChannel();
			totPackets = channelResults.get(channel).getReceivedPackets() + 1;
			channelResults.get(channel).setReceivedPackets(totPackets);
		}
		
		//analyze decoded packets
		for (Packet decodedPacket : decodedPackets) {
			channel = decodedPacket.getChannel();
			totPackets = channelResults.get(channel).getDecodedPackets() + 1;
			channelResults.get(channel).setDecodedPackets(totPackets);
		}
		
		for (Map.Entry<Integer, ResultInformation> channelResultsEntry: channelResults.entrySet()) {
			results.add(channelResultsEntry.getValue());
		}
		
		return results;
	}
	
}
