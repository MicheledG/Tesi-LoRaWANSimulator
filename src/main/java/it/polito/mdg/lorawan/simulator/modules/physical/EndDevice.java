package it.polito.mdg.lorawan.simulator.modules.physical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.polito.mdg.lorawan.simulator.modules.logical.Application;
import it.polito.mdg.lorawan.simulator.modules.logical.DataRate;
import it.polito.mdg.lorawan.simulator.modules.logical.Packet;

public class EndDevice {
	
	private int devId;
	private Application application; 
	private int channel; //RF channel used by the end device
	private DataRate dr; //Data Rate configuration used by the end device
	private double dc; //Duty cycle imposed to the end device
	private double distance; //the distance from the gateway in km
	private double rssi; //Received Signal Strength Indication (in dB) of the packet sent by this end device and received by a Gateway
	private double shiftRate; //random value between 0 and 1 used to shift the communications of the several end device deployed for a single application
	private double packetAirTime;
	private double packetInterval;
	private List<Packet> sentPackets;
	private int messageCount;
	private boolean periodicEndDevice;
	
	public EndDevice(int devId, Application application, int channel, DataRate dr, double dc, double distance, double rssi, double shiftRate) {
		
		//set the parameters received by the external entities
		this.devId = devId;
		this.application = application;
		this.channel = channel;
		this.dr = dr;
		this.dc = dc;
		this.distance = distance;
		this.rssi = rssi;
		this.shiftRate = shiftRate;
		
		//auto-compute all the other parameters used for the transmission
		this.computePacketParameters();
	
		//set packet list to start
		this.sentPackets = new ArrayList<>();
		this.messageCount = 0;
	}
	
	public void sendNextPeriodicPacket(){
		
		//compute the starting time of the packet
		double startingTime = 0.0;
		
		if(this.sentPackets.isEmpty()){
			//first message to send
			startingTime = this.packetInterval * this.shiftRate;
		}
		else{
			//following messages
			double lastMessageStartingTime = this.sentPackets.get(messageCount-1).getStartingTime();
			startingTime = lastMessageStartingTime + this.packetAirTime + this.packetInterval;
		}
		
		//update the message count
		this.messageCount++;
		
		Packet packet = new Packet(this.devId,
				this.application.getAppId(),
				this.messageCount,
				this.channel,
				this.dr,
				this.distance,
				this.rssi,
				startingTime,
				this.packetAirTime);
		
		//"send the packet" => inserting it in the list
		this.sentPackets.add(packet);
		
		
	};
	
	public void sendNextRandomPacket(double randomValue, int simulationTime){
		
		//starting time of the packet is a random value contained into the time of the simulation
		double startingTime = randomValue * (simulationTime * 60); //simulationTime is expressed in minutes				
		
		if(!this.sentPackets.isEmpty()){		
			//there are already sent packets
			double lastMessageEndTime = this.sentPackets.get(messageCount-1).getStartingTime() + this.getPacketAirTime();
			if(startingTime <= lastMessageEndTime + this.packetInterval){
				//the message cannot be sent because duty cycle violation
				return;
			}
		}				

		//update the message count
		this.messageCount++;
		
		Packet packet = new Packet(this.devId,
				this.application.getAppId(),
				messageCount, 
				this.channel,
				this.dr,
				this.distance,
				this.rssi,
				startingTime,
				this.packetAirTime);
		
		//"send the packet" => inserting it in the list
		this.sentPackets.add(packet);
		
		
	};
	
	public void sendNextNPeriodicPacket(int N){			
		for(int i = 0; i < N; i++){
			this.sendNextPeriodicPacket();
		}
	}
	
	public void sendNextNRandomPacket(int N, int simulationTime){				
		double randomValues[] = new double[N];		
		for(int i = 0; i < N; i++){
			randomValues[i] = Math.random();
		}
		
		Arrays.sort(randomValues);
		
		//there will be sent at most N packets
		for(int i = 0; i < N; i++){
			this.sendNextRandomPacket(randomValues[i], simulationTime);
		}
		
	}

	public int getDevId() {
		return devId;
	}

	public int getAppId() {
		return this.application.getAppId();
	}

	public int getChannel() {
		return channel;
	}

	public DataRate getDr() {
		return dr;
	}

	public double getDc() {
		return dc;
	}

	public double getDistance() {
		return distance;
	}

	public double getRssi() {
		return rssi;
	}

	public double getShiftRate() {
		return shiftRate;
	}

	public double getPacketAirTime() {
		return packetAirTime;
	}

	public double getPacketInterval() {
		return packetInterval;
	}

	public List<Packet> getSentPackets() {
		return sentPackets;
	};
	
	public int getMessageCount() {
		return messageCount;
	};
	
	public boolean isPeriodicEndDevice() {
		return periodicEndDevice;
	}

	public void setPeriodicEndDevice(boolean periodicEndDevice) {
		this.periodicEndDevice = periodicEndDevice;
	}

	private void computePacketParameters(){
		
		//compute airtime of the packet to send according to the datarate and the packet size
		//see LoRa calculator and Semtech design guide as references for the following computation
		int CR = this.application.getCR();
		int CRC = this.application.getCRC();
		int IH = this.application.getIH();
		int Npp = this.application.getNpp();
		int SF = this.dr.getSf();
		int BW = this.dr.getBw();
		int DE = 0;
		if(SF > 10){
			DE = 1;
		}
		
		int MACPayloadSize = application.getPayloadSize() + 8;
		int PHYPayloadSize = MACPayloadSize + 5;
		int PL = PHYPayloadSize;
		
		double numerator = 8*PL-4*SF+28+16*CRC-20*IH;
		double denominator = 4*(SF-2*DE);
		double ceiling = Math.ceil(numerator/denominator);
		double packetSymbols = (Npp + 4.25) + 8 + Math.max(ceiling*(CR+4),0);
		double symbolTime = Math.pow(2, SF)/BW; //s
		
		this.packetAirTime = packetSymbols*symbolTime;
		
		//compute the interval between 2 packet choosing between the duty cycle constrained interval and the application interval
		double dcPacketInterval = this.packetAirTime * (1 - this.dc)/(this.dc);
		
		if(this.application.getMessages() == -1){
			//periodic messages
			this.packetInterval = Math.max(dcPacketInterval, this.application.getPacketInterval());
			this.periodicEndDevice = true;
		}
		else{
			//random messages
			this.packetInterval = dcPacketInterval;
			this.periodicEndDevice = false;
		}
		
		
	}
	
}
