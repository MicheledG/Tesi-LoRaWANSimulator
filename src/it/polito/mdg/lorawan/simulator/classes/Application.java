package it.polito.mdg.lorawan.simulator.classes;

public class Application {

	private int appId;
	private double deviceDensity; //number of end device per km^2
	private int payloadSize; //typical application payload size for the specific application
	private int CR; //specify the Code Rate to use in the packet encoding/decoding (1 -> 4)
	private int IH; //specify to use the IMPLICIT HEADER mode (IH=1) or not (IH=0)
	private int Npp; //specify the number of symbols to use in the programmable portion of the preamble
	private int CRC; //specify if the CRC of the PHYPayload is present (CRC=1) or not (CRC=0) in the packet
	private double appInterval; //specify the interval (in s) between to message
	
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
	
}
