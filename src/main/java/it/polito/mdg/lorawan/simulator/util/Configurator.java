package it.polito.mdg.lorawan.simulator.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.mdg.lorawan.simulator.model.Config;

public class Configurator {
	
	private final static String DEFAULT_CONFIG_FILE_FOLDER ="config";
	private final static String DEFAULT_CONFIG_FILE_NAME_PREFIX ="config";
	private final static String DEFAULT_CONFIG_FILE_NAME_SUFFIX =".json";	
	
	public static Config configSimulator(String configFileName) throws FileNotFoundException, JsonParseException, JsonMappingException, IOException{
		
		//create the config path
		String configPath = DEFAULT_CONFIG_FILE_FOLDER + "/";
		if(configFileName != null){
			configPath += configFileName;	
		}
		else{
			configPath += DEFAULT_CONFIG_FILE_NAME_PREFIX;
		}
		configPath += DEFAULT_CONFIG_FILE_NAME_SUFFIX;
		
		//read the .json file with the configurations
		FileReader jsonFileReader = new FileReader(configPath);	
		//create ObjectMapper to parse the configuration json file
		ObjectMapper objectMapper = new ObjectMapper();		
		//extract Java Object from the jsonFileReader (InputStream)
		Config config = objectMapper.readValue(jsonFileReader, Config.class);
	
		return config;
		
	}
	
}
