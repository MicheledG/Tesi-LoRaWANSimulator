package it.polito.mdg.lorawan.simulator.model.statistics;

public class StatisticsResult {

	private Object resultSet;
	private String parameter;
	private int trials;
	private double average;
	private double variance;
	private double stdDeviation;	
	public StatisticsResult(Object resultSet, String parameter, int trials, double average, double variance, double stdDeviation) {
		super();
		this.resultSet = resultSet;
		this.parameter = parameter;
		this.trials = trials;
		this.average = average;
		this.variance = variance;
		this.stdDeviation = stdDeviation;
	}
	public Object getResultSet() {
		return resultSet;
	}
	public void setResultSet(Object resultSet) {
		this.resultSet = resultSet;
	}
	public StatisticsResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public int getTrials() {
		return trials;
	}
	public void setTrials(int trials) {
		this.trials = trials;
	}
	public double getAverage() {
		return average;
	}
	public void setAverage(double average) {
		this.average = average;
	}
	public double getVariance() {
		return variance;
	}
	public void setVariance(double variance) {
		this.variance = variance;
	}
	public double getStdDeviation() {
		return stdDeviation;
	}
	public void setStdDeviation(double stdDeviation) {
		this.stdDeviation = stdDeviation;
	}
	
}
