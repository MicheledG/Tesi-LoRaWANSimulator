package it.polito.mdg.lorawan.simulator.util.statistics;

import java.util.Arrays;

public class StatisticsCalculator {
	
	public static double computeAverage(double[] values){
		
		int size = values.length;
		double sum = 0.0;		
        for(double value : values){
        	sum += value;
        }    	
        double average = sum / size;
        return average;
		
	};
	
    public static double computeMedian(double[] values){ 
       
    	int size = values.length;
    	Arrays.sort(values);
    	double median = 0.0;    	
		if (size % 2 == 0) {
			median = (values[(size/2) - 1] + values[size/2]) / 2.0;
		}
		else{
			median = values[size/2];
		}
    	return median;
    
    }
	
    public static double computeVariance(double[] values){
        
    	int size = values.length;
    	double average = StatisticsCalculator.computeAverage(values);
    	double temp = 0;
        for(double value :values){
        	temp += Math.pow((value-average), 2);
        }
        double variance = temp/(size-1);          
        return variance;
    }

    public static double computeStdDev(double[] values)
    {
        double variance = StatisticsCalculator.computeVariance(values);
    	double stdDev = Math.sqrt(variance);
        return stdDev;
    }	
	
}
