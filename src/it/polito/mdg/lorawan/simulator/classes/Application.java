package it.polito.mdg.lorawan.simulator.classes;

import java.time.Duration;

public class Application {

	private int appId;
	private double deviceDensity;
	private int payloadSize;
	private Duration appInterval;
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public double getDeviceDensity() {
		return deviceDensity;
	}
	public void setDeviceDensity(double deviceDensity) {
		this.deviceDensity = deviceDensity;
	}
	public int getPayloadSize() {
		return payloadSize;
	}
	public void setPayloadSize(int payloadSize) {
		this.payloadSize = payloadSize;
	}
	public Duration getPacketInterval() {
		return appInterval;
	}
	public void setPacketInterval(Duration appInterval) {
		this.appInterval = appInterval;
	}
	
}
