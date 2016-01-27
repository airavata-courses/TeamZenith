package org.airavata.teamzenith.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SSHPropertyHandler {
        public Properties getPropertyMap() throws IOException {
            InputStream fis = (InputStream) getClass().getResourceAsStream("/config.properties");
        	//InputStream fis = (InputStream) getClass().getClassLoader().getResourceAsStream("/config.properties");
        	Properties prop = new Properties();
                prop.load(fis);
                return prop;

        }

}

