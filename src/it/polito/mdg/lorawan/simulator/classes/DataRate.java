package it.polito.mdg.lorawan.simulator.classes;

public class DataRate {
	
	private int drId; //data rate id
	private int sf; //spreading factor
	private int bw; //bandwidth in Hz
	
	public int getDrId() {
		return drId;
	}
	public void setDrId(int drId) {
		this.drId = drId;
	}
	public int getSf() {
		return sf;
	}
	public void setSf(int sf) {
		this.sf = sf;
	}
	public int getBw() {
		return bw;
	}
	public void setBw(int bw) {
		this.bw = bw;
	}
	
}
