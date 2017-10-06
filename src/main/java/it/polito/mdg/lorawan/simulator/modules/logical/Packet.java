package it.polito.mdg.lorawan.simulator.modules.logical;

public class Packet implements Comparable<Packet> {
		
	private int devId;
	private int appId;
	private int count;
	private int channel;
	private DataRate dr;
	private double distance;
	private double rssi;
	private double startingTime;
	private double airTime;
	
	public Packet(int devId, int appId, int count, int channel, DataRate dr, double distance, double rssi, double startingTime,
			double airTime) {
		this.devId = devId;
		this.appId = appId;
		this.count = count;
		this.channel = channel;
		this.dr = dr;
		this.distance = distance;
		this.rssi = rssi;
		this.startingTime = startingTime;
		this.airTime = airTime;
	}
	
	public int getDevId() {
		return devId;
	}
	
	public int getAppId() {
		return appId;
	}
	
	public int getCount() {
		return count;
	}
	
	public double getRssi() {
		return rssi;
	}
	
	public DataRate getDr() {
		return dr;
	}
	
	public double getDistance() {
		return distance;
	}

	public int getChannel() {
		return channel;
	}
	
	public double getStartingTime() {
		return startingTime;
	}
	
	public double getAirTime() {
		return airTime;
	}
	
	public double getEndTime() {
		return this.startingTime+this.airTime;
	}

	@Override
	public int compareTo(Packet o) {
		Double thisStartingTime = this.startingTime;
		Double thatStartingTime = o.getStartingTime();
		
		return thisStartingTime.compareTo(thatStartingTime);
	}
}
