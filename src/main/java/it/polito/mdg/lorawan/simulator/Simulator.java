package it.polito.mdg.lorawan.simulator;

import java.io.IOException;

import it.polito.mdg.lorawan.simulator.model.Config;
import it.polito.mdg.lorawan.simulator.model.Results;
import it.polito.mdg.lorawan.simulator.util.Configurator;
import it.polito.mdg.lorawan.simulator.util.ResultsWriter;

public class Simulator {

	public static void main(String[] args) {
		
		Config config;
		
		try {
			config = Configurator.configSimulator(args[0]);
		} catch (IOException e) {
			System.err.println("Impossible to configure the simulator because: "+e.getMessage());
			System.err.println("Simulation aborted!");
			return;
		}
		
		Results results = new Results();
		
		try {
			ResultsWriter.writeResults(results, args[1]);
		} catch (IOException e) {
			System.err.println("Impossible to write the results in the designated file because: "+e.getMessage());
			return;
		}
		
		return;
		
	}

}
