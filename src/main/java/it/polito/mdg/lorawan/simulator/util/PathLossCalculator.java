package it.polito.mdg.lorawan.simulator.util;

public class PathLossCalculator {
	
	//the applied model is the Hata-Okumura for a large town! (Wikipedia)
	public static double computeHataOkumura(
			double heightGw, /* gateway antenna height in m */
			double heightEd, /* end device antenna height in m */
			double f,	/* carrier frequency in MHz */
			double d /* distance between the gw antenna and the end device antenna in km */
			) {
		
		double heightEdCorrection; //antenna height correction factor in dB
		
		if(f <= 200){
			heightEdCorrection = 8.29*Math.pow((Math.log10(1.54*heightEd)), 2.0) - 1.1;
		}
		else{
			heightEdCorrection = 3.2*Math.pow((Math.log10(11.75*heightEd)), 2.0) - 4.97;
		}
		
		
		double pathLoss = 69.55 + 26.16*Math.log10(f) - 13.82*Math.log10(heightGw) -
				heightEdCorrection + Math.log10(d)*(44.9 - 6.55*Math.log10(heightGw));
		
	
		return pathLoss; //in dB
		
	}

}
