package org.airavata.teamzenith.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.airavata.teamzenith.config.SSHPropertyHandler;
import org.airavata.teamzenith.exceptions.ExceptionHandler;

public class FileUtils {
	public boolean checkFile(String filepath) throws IOException, ExceptionHandler{
		SSHPropertyHandler sph=new SSHPropertyHandler();
		Properties pr = sph.getPropertyMap();
		String scriptSource = pr.getProperty("scriptdirectory") ;
		File f=new File(scriptSource+filepath);
		System.out.println(filepath);
		if(f.exists())
			return true;
		else
			return false;
	}
	
	public boolean checkDir(String dirpath) throws IOException, ExceptionHandler{
		SSHPropertyHandler sph=new SSHPropertyHandler();
		Properties pr = sph.getPropertyMap();
		File f=new File(dirpath);
		if(f.exists())
			return true;
		else
			return false;
	}
}
