package org.airavata.teamzenith.input;

import java.io.IOException;
import java.util.Properties;

import org.airavata.teamzenith.config.SSHPropertyHandler;

public class UnitTestClass {
	UnitTestClass(){
		
	}
	public static void main(String args[]) throws IOException{
		SSHPropertyHandler sph=new SSHPropertyHandler();
		Properties p=sph.getPropertyMap();
		System.out.println(p.getProperty("user"));
		}
}
