package org.airavata.teamzenith.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class Manager {

	public Manager() {

	}

	public boolean transferFile(String fileName) throws IOException{
			FileOutputStream pwr = new FileOutputStream(getClass().getResource("/"+fileName).getFile());
			pwr.write("Sample data".getBytes());
			FileInputStream fis = new FileInputStream(getClass().getResource("/"+fileName).getFile());
	        System.out.println(fis.read());
			return true;
		}
		

	}