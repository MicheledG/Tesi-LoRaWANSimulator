package it.polito.mdg.lorawan.simulator.classes;

import java.time.Duration;
import java.util.List;

public class EndDevice {
	
	private int devId;
	private int appId;
	private int channel;
	private DataRate dr;
	private double rssi;
	private double shiftRate;
	private int packetSize;
	private Duration packetAirTime;
	private Duration packetInterval;
	private List<Packet> sentPackets;
	
	public EndDevice(int devId, int appId, int channel, DataRate dr, double rssi, double shiftRate, int packetSize, Duration packetAirTime, Duration packetInterval) {
		super();
		this.devId = devId;
		this.appId = appId;
		this.channel = channel;
		this.dr = dr;
		this.rssi = rssi;
		this.shiftRate = shiftRate;
		this.packetSize = packetSize; 
		this.packetAirTime = packetAirTime;
		this.packetInterval = packetInterval;
	}
	
	public void sendNextPacket(){};
	public void sendNextNPacket(int N){}

	public int getDevId() {
		return devId;
	}

	public int getAppId() {
		return appId;
	}

	public int getChannel() {
		return channel;
	}

	public DataRate getDr() {
		return dr;
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

	public int getPacketSize() {
		return packetSize;
	}

	public Duration getPacketAirTime() {
		return packetAirTime;
	}

	public Duration getPacketInterval() {
		return packetInterval;
	}

	public List<Packet> getSentPackets() {
		return sentPackets;
	};
	
}
