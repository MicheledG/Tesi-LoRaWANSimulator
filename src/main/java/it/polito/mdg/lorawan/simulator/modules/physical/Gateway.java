package it.polito.mdg.lorawan.simulator.modules.physical;

import java.util.ArrayList;
import java.util.List;

import it.polito.mdg.lorawan.simulator.modules.logical.Packet;

public class Gateway {

	private int decodingPathNumber;
	private Packet[] decodingPath;
	
	public Gateway(int decodingPathNumber) {
		this.decodingPathNumber = decodingPathNumber;
		decodingPath = new Packet[this.decodingPathNumber];
		
		Packet defaultPacket = 
				new Packet(
						0, 
						0, 
						0, 
						0, 
						null,
						0,
						0, 
						-1.0, 
						0.0);
		
		for(int i=0; i < decodingPathNumber; i++){			
			decodingPath[i] = defaultPacket;
		}
	}

	public List<Packet> receivePackets(List<Packet> sentPackets){
		
		List<Packet> receivedPackets = new ArrayList<>();
		
		int totSentPackets = sentPackets.size();
		System.out.println("======================");		
		System.out.println("GW: number of sent packets by end devices: "+totSentPackets);
		System.out.println("GW: receiving phase starting!");
		System.out.println("======================");
		
		double analyzedPacketRatio = 0; 
		int analyzedPacketPercent = 0;
		int difference = 0;
		//detect if a packet collides with others
		for(int i = 0; i < totSentPackets; i++){
			//for each packet sent check the collision with all the others
			Packet interfered = sentPackets.get(i);
			boolean collision = false;
			for(int j = 0; j < totSentPackets && !collision; j++){
				if(j!=i){
					Packet interferer = sentPackets.get(j);
					collision = this.checkCollision(interfered, interferer);
				}
			}
			
			if(!collision){
				//the packet under test didn't collided with the other packets => RECEIVED
				receivedPackets.add(interfered);
			}
			
			//notify the user with the progress
			analyzedPacketRatio = ((double)(i+1))/((double)(totSentPackets));
			difference = (int) (analyzedPacketRatio * 100) - analyzedPacketPercent;
			if(difference > 0){
				analyzedPacketPercent += difference;
				System.out.println("======================");		
				System.out.println("GW: analyzed packet percent: "+analyzedPacketPercent+"%");
				System.out.println("======================");
			}
			
		}
		
		int totReceivedPackets = receivedPackets.size();
		int receivedRatio = (int)((((double) totReceivedPackets) / ((double)totSentPackets)) * 100);
		
		System.out.println("======================");		
		System.out.println("GW: receiving phase completed!");
		System.out.println("GW: number of received packets: "+totReceivedPackets);		
		System.out.println("GW: received ratio: "+receivedRatio+"%");
		System.out.println("======================");		
		
		return receivedPackets;
	};
	
	private boolean checkCollision(Packet interfered, Packet interferer) {
		
		
		if(interfered.getChannel() == interferer.getChannel() &&
				interfered.getDr().equals(interferer.getDr())){
			
			//the interfered and the interfere are on the same "virtual channel" 
			//(same physical channel and same data rate)
			
			//extract useful parameters
			double interferedStartingTime = interfered.getStartingTime();
			double interferedEndTime = interfered.getEndTime();
			
			double interfererStartingTime = interferer.getStartingTime();
			double interfererEndTime = interferer.getEndTime();
			
			// Apply collision detection algorithm
			if(interfered.getRssi() >= interferer.getRssi()){
				if(interfererStartingTime <= interferedStartingTime &&
						interferedStartingTime <= interfererEndTime){
					//collision
					return true;
				}
			}
			else{
				if(interfererStartingTime <= interferedStartingTime &&
						interferedStartingTime <= interfererEndTime){
					//collision
					return true;
				}
				else if(interfererStartingTime <= interferedEndTime &&
						interferedEndTime<= interfererEndTime){
					//collision
					return true;
				}
			}
			
			//no collision has been detected
			return false;
		}
		else{
			//no collision is possible because the two packets are on different virtual channels
			return false;
		}
	}

	public List<Packet> decodePackets(List<Packet> receivedPackets){
		
		List<Packet> decodedPackets = new ArrayList<>();
		
		int totReceivedPackets = receivedPackets.size();
		
		System.out.println("======================");		
		System.out.println("GW: number of received packets by end devices: "+totReceivedPackets);
		System.out.println("GW: decoding phase starting!");
		System.out.println("======================");		
		
		//sort the received packets depending on the starting time
		java.util.Collections.sort(receivedPackets);		
		
		//decoded each received message finding a suitable decoding path in the gateway
		//if no free path is found the packet is discarded
		int i = 0;
		double analyzedPacketRatio = 0; 
		int analyzedPacketPercent = 0;
		int difference = 0;
		for (Packet packet : receivedPackets) {
			boolean decoded = decodePacket(packet);
			if(decoded){
				decodedPackets.add(packet);
			}
			
			//notify the user with the progress
			analyzedPacketRatio = ((double)(i+1))/((double)(totReceivedPackets));
			difference = (int) (analyzedPacketRatio * 100) - analyzedPacketPercent;
			if(difference > 0){
				analyzedPacketPercent += difference;
				System.out.println("======================");		
				System.out.println("GW: analyzed packet percent: "+analyzedPacketPercent+"%");
				System.out.println("======================");
			}
			i++;
		}
		
		//sort the decoded packets on the starting time
		java.util.Collections.sort(decodedPackets);		
		int totDecodedPackets = decodedPackets.size();
		int decodedRatio = (int)((((double)totDecodedPackets)/((double)totReceivedPackets))*100);
		
		System.out.println("======================");		
		System.out.println("GW: decoding phase completed!");
		System.out.println("GW: number of decoded packets: "+totDecodedPackets);		
		System.out.println("GW: decoded ratio: "+decodedRatio+"%");
		System.out.println("======================");
		
		return decodedPackets;
	}

	private boolean decodePacket(Packet packet) {
		
		boolean decoded = false;
		for(int i = 0; i < this.decodingPathNumber && !decoded; i++){
			if(packet.getStartingTime() > decodingPath[i].getEndTime()){
				//the packet can be decoded on this path
				decoded = true;
				//put the new packet inside the path
				decodingPath[i] = packet;
			}
		}
		
		return decoded;
	}
	
	
	
	
}
