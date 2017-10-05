package it.polito.mdg.lorawan.simulator.classes;

import java.util.List;
import java.util.Vector;

public class EndDevice {
	
	private int devId;
	private Application application; 
	private int channel; //RF channel used by the end device
	private DataRate dr; //Data Rate configuration used by the end device
	private double dc; //Duty cycle imposed to the end device
	private double rssi; //Received Signal Strength Indication (in dB) of the packet sent by this end device and received by a Gateway
	private double shiftRate; //random value between 0 and 1 used to shift the communications of the several end device deployed for a single application
	private double packetAirTime;
	private double packetInterval;
	private Vector<Packet> sentPackets;
	private int messageCount;
	
	public EndDevice(int devId, Application application, int channel, DataRate dr, double dc, double rssi, double shiftRate) {
		
		//set the parameters received by the external entities
		this.devId = devId;
		this.application = application;
		this.channel = channel;
		this.dr = dr;
		this.dc = dc;
		this.rssi = rssi;
		this.shiftRate = shiftRate;
		
		//auto-compute all the other parameters used for the transmission
		this.computePacketParameters();
	
		//set packet list to start
		this.sentPackets = new Vector<>();
		this.messageCount = 0;
	}
	
	public void sendNextPacket(){
		
		//compute the starting time of the packet
		double startingTime = 0.0;
		
		if(this.sentPackets.isEmpty()){
			//first message to send
			startingTime = this.packetAirTime * this.shiftRate;
		}
		else{
			//following messages
			double lastMessageStartingTime = this.sentPackets.lastElement().getStartingTime();
			startingTime = lastMessageStartingTime + this.packetAirTime + this.packetInterval;
		}
		
		//update the message count
		this.messageCount++;
		
		Packet packet = new Packet(this.devId,
				this.application.getAppId(),
				this.messageCount,
				this.channel,
				this.dr,
				this.rssi,
				startingTime,
				this.packetAirTime);
		
		//"send the packet" inserting it in the list
		this.sentPackets.addElement(packet);
		
		
	};
	
	public void sendNextNPacket(int N){	
		for(int i = 0; i < N; i++){
			this.sendNextPacket();
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

	public double getRssi() {
		return rssi;
	}

	public void setRssi(double rssi) {
		this.rssi = rssi;
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
		
		double packetSymbols = (Npp + 4.25) + 8 + Math.max((int)((8*PL-4*SF+28+16*CRC-20*IH)/(4*(SF-2*DE)))*(CR+4),0);
		double symbolTime = Math.pow(2, SF)/BW; //s
		
		this.packetAirTime = packetSymbols*symbolTime;
		
		//compute the interval between 2 packet choosing between the duty cycle constrained interval and the application interval
		double dcPacketInterval = this.packetAirTime * (1 - this.dc)/(this.dc);
		this.packetInterval = Math.max(dcPacketInterval, this.application.getPacketInterval());
		
	}
	
}
