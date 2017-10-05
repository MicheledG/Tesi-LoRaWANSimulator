package it.polito.mdg.lorawan.simulator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.mdg.lorawan.simulator.util.Config;

public class Simulator {

	public static void main(String[] args) {
		
		//read the .json file with the configurations
		byte[] jsonData;
		try {
			jsonData = Files.readAllBytes(Paths.get("config.json"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		//create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();
		
		//convert json string to object
		Config config;
		try {
			config = objectMapper.readValue(jsonData, Config.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		System.out.println("Config Object\n"+config);
		
		
	}

}
