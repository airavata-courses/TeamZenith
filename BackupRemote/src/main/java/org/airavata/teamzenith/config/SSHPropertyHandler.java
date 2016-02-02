package org.airavata.teamzenith.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.apache.log4j.Logger;

public class SSHPropertyHandler {
	Logger log = Logger.getLogger("org.airavata.teamzenith.log");

	public Properties getPropertyMap() throws IOException, ExceptionHandler {
		try{
		InputStream fis = (InputStream) getClass().getResourceAsStream("/config.properties");
		Properties prop = new Properties();
		prop.load(fis);
		log.info("Properties file loaded");
		return prop;
		}
		catch(IOException e){
			throw new ExceptionHandler(e);
		}
	}

}
