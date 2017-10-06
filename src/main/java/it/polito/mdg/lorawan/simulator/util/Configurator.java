package it.polito.mdg.lorawan.simulator.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.mdg.lorawan.simulator.model.Config;

public class Configurator {
	
	private final static String DEFAULT_CONFIG_FILE_NAME = "config.json";
	
	public static Config configSimulator(String configFileName) throws FileNotFoundException, JsonParseException, JsonMappingException, IOException{
		
		if(configFileName == null){
			configFileName = DEFAULT_CONFIG_FILE_NAME;
		}
		
		//read the .json file with the configurations
		FileReader jsonFileReader = new FileReader(configFileName);	
		//create ObjectMapper to parse the configuration json file
		ObjectMapper objectMapper = new ObjectMapper();		
		//extract Java Object from the jsonFileReader (InputStream)
		Config config = objectMapper.readValue(jsonFileReader, Config.class);
	
		return config;
		
	}
	
}
