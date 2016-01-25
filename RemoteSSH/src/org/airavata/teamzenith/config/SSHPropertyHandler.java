package org.airavata.teamzenith.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SSHPropertyHandler {
	public Properties getPropertyMap() throws IOException {
		FileInputStream fis = new FileInputStream("src/org/airavata/teamzenith/config/config.properties");
		Properties prop = new Properties();
		prop.load(fis);
		return prop;

	}

}
