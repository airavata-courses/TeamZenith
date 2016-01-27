package org.airavata.teamzenith.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class Manager {

	public Manager() {

	}

	public boolean transferFile(String fileName) throws IOException{
			FileInputStream pwr = new FileInputStream(getClass().getResource("/"+fileName).getFile());
	        System.out.println(pwr.read());
			return true;
		}
		

	}