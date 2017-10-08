package it.polito.mdg.lorawan.simulator.model;

import java.util.List;

import it.polito.mdg.lorawan.simulator.modules.logical.Application;
import it.polito.mdg.lorawan.simulator.modules.logical.DataRate;

public class Config {

	private int time; //simulation time in minutes
	private double range; //circle area range in km
	private double gwHeight; //height of the gateway antenna in m
	private double edHeight; //height of the end device antennas in m
	private double frequency; //carrier frequency expressed in MHz
	private double txPower; //transmission power of the end devices
	private double dutyCycle; //the max DC imposed to the end device for the transmissions
	private int channelNumber; //the number of channel used for the simulation
	private List<DataRate> dataRates; //list of allowed data rates (SF plus BW)
	private int decodingPath; //the number of parallel decoding path of the gateway
	private List<Application> applications; //list of all the applications
	private int endDeviceNumber; //the number of end device to deploy in the area
	private int collisionAlgorithm; //set the collision algorithm: 0 = LoRa; 1 or others = Aloha
	
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
	public double getFrequency() {
		return frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public int getEndDeviceNumber() {
		return endDeviceNumber;
	}
	public void setEndDeviceNumber(int endDeviceNumber) {
		this.endDeviceNumber = endDeviceNumber;
	}
	public double getTxPower() {
		return txPower;
	}
	public void setTxPower(double txPower) {
		this.txPower= txPower;
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
	public int getDecodingPath() {
		return decodingPath;
	}
	public void setDecodingPath(int decodingPath) {
		this.decodingPath = decodingPath;
	}
	public List<Application> getApplications() {
		return applications;
	}
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	public int getCollisionAlgorithm() {
		return collisionAlgorithm;
	}
	public void setCollisionAlgorithm(int collisionAlgorithm) {
		this.collisionAlgorithm = collisionAlgorithm;
	}
	
}
