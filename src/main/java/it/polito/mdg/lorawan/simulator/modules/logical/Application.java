package it.polito.mdg.lorawan.simulator.modules.logical;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Application {

	private int appId;
	private double deviceDensity; //number of end device per km^2
	private int payloadSize; //typical application payload size for the specific application
	@JsonProperty("CR")
	private int CR; //specify the Code Rate to use in the packet encoding/decoding (1 -> 4)
	@JsonProperty("IH")
	private int IH; //specify to use the IMPLICIT HEADER mode (IH=1) or not (IH=0)
	@JsonProperty("Npp")
	private int Npp; //specify the number of symbols to use in the programmable portion of the preamble
	@JsonProperty("CRC")
	private int CRC; //specify if the CRC of the PHYPayload is present (CRC=1) or not (CRC=0) in the packet
	private double appInterval; //specify the interval (in s) between to message
	
	public Application(){};
	
	public Application(int appId, double deviceDensity, int payloadSize, int CR, int IH, int Npp, int CRC,
			double appInterval) {
		this.appId = appId;
		this.deviceDensity = deviceDensity;
		this.payloadSize = payloadSize;
		this.CR = CR;
		this.IH = IH;
		this.Npp = Npp;
		this.CRC = CRC;
		this.appInterval = appInterval;
	}
	
	public int getAppId() {
		return appId;
	}
	public double getDeviceDensity() {
		return deviceDensity;
	}
	public int getPayloadSize() {
		return payloadSize;
	}
	public void setPayloadSize(int payloadSize) {
		this.payloadSize = payloadSize;
	}
	public double getPacketInterval() {
		return appInterval;
	}

	public int getCR() {
		return CR;
	}

	public int getIH() {
		return IH;
	}

	public int getNpp() {
		return Npp;
	}

	public int getCRC() {
		return CRC;
	}

	public double getAppInterval() {
		return appInterval;
	}

	public void setAppInterval(double appInterval) {
		this.appInterval = appInterval;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public void setDeviceDensity(double deviceDensity) {
		this.deviceDensity = deviceDensity;
	}

	public void setCR(int CR) {
		this.CR = CR;
	}

	public void setIH(int IH) {
		this.IH = IH;
	}

	public void setNpp(int Npp) {
		this.Npp = Npp;
	}

	public void setCRC(int CRC) {
		this.CRC = CRC;
	}
	
}
