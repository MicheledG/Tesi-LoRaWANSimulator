package it.polito.mdg.lorawan.simulator.util;

import java.util.List;

import it.polito.mdg.lorawan.simulator.modules.logical.Application;
import it.polito.mdg.lorawan.simulator.modules.logical.DataRate;

public class Config {

	private double range; //circle area range in km
	private int endDeviceNumber; //the number of end device to deploy in the area
	private int rssiMax; //the MAX RSSI (a.k.a. minimum distance of and end device from a gateway)
	private double dutyCycle; //the max DC imposed to the end device for the transmissions
	private int channelNumber; //the number of channel used for the simulation
	private List<DataRate> dataRates; //list of allowed data rates (SF plus BW)
	private List<Application> applications; //list of all the applications
	
	public double getRange() {
		return range;
	}
	public void setRange(double range) {
		this.range = range;
	}
	public int getEndDeviceNumber() {
		return endDeviceNumber;
	}
	public void setEndDeviceNumber(int endDeviceNumber) {
		this.endDeviceNumber = endDeviceNumber;
	}
	public int getRssiMax() {
		return rssiMax;
	}
	public void setRssiMax(int rssiMax) {
		this.rssiMax = rssiMax;
	}
	public double getDutyCycle() {
		return dutyCycle;
	}
	public void setDutyCycle(double dutyCycle) {
		this.dutyCycle = dutyCycle;
	}
	public int getChannelNumber() {
		return channelNumber;
	}
	public void setChannelNumber(int channelNumber) {
		this.channelNumber = channelNumber;
	}
	public List<DataRate> getDataRates() {
		return dataRates;
	}
	public void setDataRates(List<DataRate> dataRates) {
		this.dataRates = dataRates;
	}
	public List<Application> getApplications() {
		return applications;
	}
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
}
