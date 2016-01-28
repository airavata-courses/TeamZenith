package org.airavata.teamzenith.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Properties;

public class Manager {

	public Manager() {

	}

	public boolean transferFile(String fileName) throws IOException{
			fileName.replace('!', ' ');
			File f=new File("jar:file:/home/atul/.m2/repository/MavenTest/MavenTest/0.0.1-SNAPSHOT/MavenTest-0.0.1-SNAPSHOT.jar/public.ppk");
			FileOutputStream wq=new FileOutputStream(f);
			wq.write("Hello".getBytes());
//			FileOutputStream pwr = new FileOutputStream(getClass().getResource("/"+fileName).getFile());
//			pwr.write("Sample data".getBytes());
//			FileInputStream fis = new FileInputStream(getClass().getResource("/"+fileName).getFile());
//	        System.out.println(fis.read());
			return true;
		}
		

	}