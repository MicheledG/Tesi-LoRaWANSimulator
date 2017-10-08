package it.polito.mdg.lorawan.simulator.model;

import java.util.HashMap;
import java.util.Map;

import it.polito.mdg.lorawan.simulator.modules.logical.Application;
import it.polito.mdg.lorawan.simulator.modules.logical.DataRate;

public class ResultInformation implements Comparable<ResultInformation>{
	
	private static final Map<Class<? extends Object>, Integer> resultSetClassMapping;
	static{
		resultSetClassMapping = new HashMap<>();
		resultSetClassMapping.put(Application.class, 0);
		resultSetClassMapping.put(DataRate.class, 1);
		resultSetClassMapping.put(String.class, 2);
	}
	
	private Object resultSet;
	private int sentPackets;
	private int receivedPackets;
	private int decodedPackets;
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

	@Override
	public int compareTo(ResultInformation o) {
		
		
		int comparisonResult = 0;
		
		Integer thisResultSetClassIndex = resultSetClassMapping.get(this.resultSet.getClass());
		Integer thatResultSetClassIndex = resultSetClassMapping.get(o.getResultSet().getClass());
			
		if(!thisResultSetClassIndex.equals(thatResultSetClassIndex)){
			comparisonResult = thisResultSetClassIndex.compareTo(thatResultSetClassIndex);
		}
		else{
			switch (thisResultSetClassIndex) {
			case 0:
				Application thisResultSetApplication = ((Application) this.resultSet);
				Application thatResultSetApplication = ((Application) o.getResultSet());
				comparisonResult = thisResultSetApplication.compareTo(thatResultSetApplication);
				break;
			case 1:
				DataRate thisResultSetDataRate = ((DataRate) this.resultSet);
				DataRate thatResultSetDataRate = ((DataRate) o.getResultSet());
				comparisonResult = thisResultSetDataRate.compareTo(thatResultSetDataRate);
				break;
			case 2:
				String thisResultSetString = ((String) this.resultSet);
				String thatResultSetString = ((String) o.getResultSet());
				comparisonResult = thisResultSetString.compareTo(thatResultSetString);
				break;
			default:
				break;
			}
		}
		
		return comparisonResult;
	}
}
