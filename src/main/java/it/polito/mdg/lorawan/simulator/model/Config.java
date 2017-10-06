package it.polito.mdg.lorawan.simulator.model;

import java.util.List;

import it.polito.mdg.lorawan.simulator.modules.logical.Application;
import it.polito.mdg.lorawan.simulator.modules.logical.DataRate;

public class Config {

	private int time; //simulation time in minutes
	private double range; //circle area range in km
	private double gwHeight; //height of the gateway antenna in m
	private double edHeight; //height of the end device antennas in m
	private int rssiMax; //the MAX RSSI (a.k.a. minimum distance of and end device from a gateway)
	private double dutyCycle; //the max DC imposed to the end device for the transmissions
	private int channelNumber; //the number of channel used for the simulation
	private List<DataRate> dataRates; //list of allowed data rates (SF plus BW)
	private List<Application> applications; //list of all the applications
	private int endDeviceNumber; //the number of end device to deploy in the area
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public double getRange() {
		return range;
	}
	public void setRange(double range) {
		this.range = range;
	}
	public double getGwHeight() {
		return gwHeight;
	}
	public void setGwHeight(double gwHeight) {
		this.gwHeight = gwHeight;
	}
	public double getEdHeight() {
		return edHeight;
	}
	public void setEdHeight(double edHeight) {
		this.edHeight = edHeight;
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
	
	public void printConfiguration(){
		//TODO
		
		
	}
	
}
