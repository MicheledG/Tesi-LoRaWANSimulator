package it.polito.mdg.lorawan.simulator.modules.logical;

public class DataRate {
	
	private int sf; //spreading factor
	private int bw; //bandwidth in Hz
	
	public DataRate(){};
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bw;
		result = prime * result + sf;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataRate other = (DataRate) obj;
		if (bw != other.bw)
			return false;
		if (sf != other.sf)
			return false;
		return true;
	}
	
}
