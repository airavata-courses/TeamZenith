package org.airavata.teamzenith.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.airavata.teamzenith.config.PbsConfig;
import org.airavata.teamzenith.config.SSHPropertyHandler;

public class PbsGen {
	public String generateScript(PbsConfig pconf) throws IOException {
		SSHPropertyHandler sph=new SSHPropertyHandler();
		Properties pr=sph.getPropertyMap();
		String filePath=pr.getProperty("scriptdirectory");
		String fileName = "PBS_Script_" + System.currentTimeMillis() + ".pbs";
		PrintWriter pwr = new PrintWriter(filePath+fileName, "UTF-8");
		String lFlag = PbsConstants.pbsPrefix + " -l " + " nodes=" + pconf.getNodes() + ":ppn=" + pconf.getPpn()
				+ ",walltime=" + pconf.getWallTime();
		String nFlag = PbsConstants.pbsPrefix + " -N "+ pconf.getJobName();
		String executeCmd="./"+pconf.getFilePath();
		
		pwr.write(PbsConstants.hashBang+"\n");
		pwr.write(lFlag+"\n");
		pwr.write(nFlag+"\n");
		pwr.write(executeCmd+"\n");
		System.out.println("File generation successful");
		pwr.close();
		return fileName;
	}

}
