package it.polito.mdg.lorawan.simulator.modules.logical;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Application implements Comparable<Application> {

	private int appId;
	private double deviceDensity; //number of end device per km^2
	private int payloadSize; //typical application payload size for the specific application
	private int CR; //specify the Code Rate to use in the packet encoding/decoding (1 -> 4)
	private int IH; //specify to use the IMPLICIT HEADER mode (IH=1) or not (IH=0)
	private int Npp; //specify the number of symbols to use in the programmable portion of the preamble
	private int CRC; //specify if the CRC of the PHYPayload is present (CRC=1) or not (CRC=0) in the packet
	private double appInterval; //if no "messages" is specified indicates the the interval (in s) between to messages
	private int messages = -1; //indicates the number of messages to send in the appInterval
	
	public Application(){};
	
	public Application(int appId, double deviceDensity, int payloadSize, int CR, int IH, int Npp, int CRC,
			double appInterval, int messages) {
		this.appId = appId;
		this.deviceDensity = deviceDensity;
		this.payloadSize = payloadSize;
		this.CR = CR;
		this.IH = IH;
		this.Npp = Npp;
		this.CRC = CRC;
		this.appInterval = appInterval;
		this.messages = messages;
	}
	
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
	//public double getPacketInterval() {
	//	return appInterval;
	//}
	
	@JsonProperty("CR")
	public int getCR() {
		return CR;
	}
	
	@JsonProperty("CR")
	public void setCR(int CR) {
		this.CR = CR;
	}
	
	@JsonProperty("IH")
	public int getIH() {
		return IH;
	}
	
	@JsonProperty("IH")
	public void setIH(int IH) {
		this.IH = IH;
	}
	
	@JsonProperty("Npp")
	public int getNpp() {
		return Npp;
	}
	
	@JsonProperty("Npp")
	public void setNpp(int Npp) {
		this.Npp = Npp;
	}
	
	@JsonProperty("CRC")
	public int getCRC() {
		return CRC;
	}
	
	@JsonProperty("CRC")
	public void setCRC(int CRC) {
		this.CRC = CRC;
	}

	public double getAppInterval() {
		return appInterval;
	}

	public void setAppInterval(double appInterval) {
		this.appInterval = appInterval;
	}

	public int getMessages() {
		return messages;
	}

	public void setMessages(int messages) {
		this.messages = messages;
	}

	@Override
	public int compareTo(Application o) {
		Integer thisAppId = this.appId;
		Integer thatAppId = o.getAppId();		
		return thisAppId.compareTo(thatAppId);
	}
	
	
}
