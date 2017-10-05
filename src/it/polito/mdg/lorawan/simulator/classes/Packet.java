package it.polito.mdg.lorawan.simulator.classes;

import java.time.Duration;
import java.time.Instant;

public class Packet implements Comparable<Packet> {
		
	private int devId;
	private int appId;
	private int count;
	private int channel;
	private DataRate dr;
	private double rssi;
	private Instant startingTime;
	private Duration airTime;
	
	public int getDevId() {
		return devId;
	}
	public void setDevId(int devId) {
		this.devId = devId;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public double getRssi() {
		return rssi;
	}
	public void setRssi(double rssi) {
		this.rssi = rssi;
	}
	public DataRate getDr() {
		return dr;
	}
	public void setDr(DataRate dr) {
		this.dr = dr;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public Instant getStartingTime() {
		return startingTime;
	}
	public void setStartingTime(Instant startingTime) {
		this.startingTime = startingTime;
	}
	public Duration getAirTime() {
		return airTime;
	}
	public void setAirTime(Duration airTime) {
		this.airTime = airTime;
	}
	@Override
	public int compareTo(Packet o) {
		return this.startingTime.compareTo(o.getStartingTime());
	}
}
