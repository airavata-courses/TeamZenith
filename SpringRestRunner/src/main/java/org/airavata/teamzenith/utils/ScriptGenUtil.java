package org.airavata.teamzenith.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import org.airavata.teamzenith.config.PbsConfig;
import org.airavata.teamzenith.config.SSHPropertyHandler;
import org.airavata.teamzenith.dao.JobDetails;
import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.drivers.FileManagementImpl;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.webmethods.UserSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ScriptGenUtil {

	private static final Logger LOGGER = LogManager.getLogger(ScriptGenUtil.class);

	public String generateScript(JobDetails job, UserDetails user) throws IOException, ExceptionHandler {
		System.out.println("Start");
		SSHPropertyHandler sph = new SSHPropertyHandler();
		Properties pr = sph.getPropertyMap();
		//String filePath = pr.getProperty("scriptdirectory");
		String filePath="we need a place to store the generated script";
		String fileName = "PBS_Script_" + System.currentTimeMillis() + ".pbs";
		LOGGER.info("PBS Script Name is" + fileName);
		try {

			File f = new File(filePath + fileName);
			PrintWriter pwr = new PrintWriter(f, "UTF-8");

			String lFlag = PbsConstants.pbsPrefix + " -l " + " nodes=" + job.getNumNodes() + ":ppn=" + job.getProcessorPerNode()
			+ ",walltime=" + job.getWallTime();

			String nFlag = PbsConstants.pbsPrefix + " -N " + job.getJobName();
			String mailFlag = PbsConstants.pbsPrefix + " -m abe";
			String recvFlag = PbsConstants.pbsPrefix + " -M " + user.getEmail();
			String executeCmd = "./" + "pconf.getFilePath()-->yet to decide";
			String accessCmd=PbsConstants.chmod+" "+"pconf.getFilePath()---> yet to decide";
			String mailCmd = "sh " + PbsConstants.mailScriptDest + " 2> /dev/null";

			LOGGER.info("Mail cmd is " + mailCmd);
			pwr.write(PbsConstants.hashBang + "\n");
			pwr.write(lFlag + "\n");
			pwr.write(nFlag + "\n");
			pwr.write(mailFlag + "\n");
			pwr.write(recvFlag + "\n");
			pwr.write("cd " + pr.getProperty("destination") + "\n");
			pwr.write(accessCmd+"\n");
			pwr.write(executeCmd + "\n");
			pwr.write(mailCmd + "\n");
			LOGGER.info("PBS Script File generation successful");

			System.out.println("File generation successful");
			pwr.close();
			return fileName;

		} catch (IOException e) {
			LOGGER.error("FILE ERROR: Error opening script file");
			throw new IOException("FILE ERROR: Error opening script file",e);
		} 
	}
   
       public String modifyMailArgs(String pbsFile, String dirPath) throws IOException {
		
		//SSHPropertyHandler sph = new SSHPropertyHandler();
		//Properties pr = sph.getPropertyMap();
		String filePath = "Yet to decide";
		FileWriter writer = null;
		try {
			File f = new File(dirPath + PbsConstants.mailScript);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = "";
			String text = "";
			while ((line = br.readLine()) != null) {
				text += line + "\n";
			}
			br.close();
			// replace a word in a file
			String modifiedContent = text.replaceAll(PbsConstants.pbsFormat, pbsFile);

			writer = new FileWriter(filePath + PbsConstants.mailScriptDest);
			writer.write(modifiedContent);
			return pbsFile;
		} catch (IOException e) {
			writer.close();
			LOGGER.error("SCRIPT ERROR: Error while modifying pbs script");
			throw new IOException("SCRIPT ERROR: Error while modifying pbs script",e);

		}
		finally{
			writer.close();

		}
		
	}
}
