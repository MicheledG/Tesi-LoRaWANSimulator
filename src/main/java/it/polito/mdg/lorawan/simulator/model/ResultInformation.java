package it.polito.mdg.lorawan.simulator.model;

public class ResultInformation {
	
	private Object resultSet;
	private int sentPackets;
	private int receivedPackets;
//	private double receivedRatio;
	private int decodedPackets;
//	private double decodedRatio;
//	private double dataExtractionRatio;
	public ResultInformation(){};
	
	public Object getResultSet() {
		return resultSet;
	}
	public void setResultSet(Object resultSet) {
		this.resultSet = resultSet;
	}
	public int getSentPackets() {
		return sentPackets;
	}
	public void setSentPackets(int sentPackets) {
		this.sentPackets = sentPackets;
	}
	public int getReceivedPackets() {
		return receivedPackets;
	}
	public void setReceivedPackets(int receivedPackets) {
		this.receivedPackets = receivedPackets;
	}
	public double getReceivedRatio() {
		return (((double)receivedPackets)/((double)sentPackets))*100;
	}
	public int getDecodedPackets() {
		return decodedPackets;
	}
	public void setDecodedPackets(int decodedPackets) {
		this.decodedPackets = decodedPackets;
	}
	public double getDecodedRatio() {
		return (((double)decodedPackets)/((double)receivedPackets))*100;
	}
	public double getDataExtractionRatio() {
		return (((double)decodedPackets)/((double)sentPackets))*100;
	}
}
