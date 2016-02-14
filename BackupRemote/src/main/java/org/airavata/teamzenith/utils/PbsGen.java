package org.airavata.teamzenith.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.airavata.teamzenith.config.PbsConfig;
import org.airavata.teamzenith.config.SSHPropertyHandler;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.apache.log4j.Logger;

public class PbsGen {
	Logger log = Logger.getLogger("org.airavata.teamzenith.log");

	public String generateScript(PbsConfig pconf) throws IOException, ExceptionHandler {
		System.out.println("Start");
		SSHPropertyHandler sph = new SSHPropertyHandler();
		Properties pr = sph.getPropertyMap();
		//String filePath = pr.getProperty("scriptdirectory");
		String filePath=pconf.getWorkSpace();
		String fileName = "PBS_Script_" + System.currentTimeMillis() + ".pbs";
		log.info("PBS Script Name is" + fileName);
		try {

			File f = new File(filePath + fileName);
			PrintWriter pwr = new PrintWriter(f, "UTF-8");

			String lFlag = PbsConstants.pbsPrefix + " -l " + " nodes=" + pconf.getNodes() + ":ppn=" + pconf.getPpn()
					+ ",walltime=" + pconf.getWallTime();

			String nFlag = PbsConstants.pbsPrefix + " -N " + pconf.getJobName();
			String mailFlag = PbsConstants.pbsPrefix + " -m abe";
			String recvFlag = PbsConstants.pbsPrefix + " -M " + pconf.getEmailAddress();
			String executeCmd = "./" + pconf.getFilePath();
			String accessCmd=PbsConstants.chmod+" "+pconf.getFilePath();
			String mailCmd = "sh " + PbsConstants.mailScriptDest + " 2> /dev/null";

			log.info("Mail cmd is " + mailCmd);
			pwr.write(PbsConstants.hashBang + "\n");
			pwr.write(lFlag + "\n");
			pwr.write(nFlag + "\n");
			pwr.write(mailFlag + "\n");
			pwr.write(recvFlag + "\n");
			pwr.write("cd " + pr.getProperty("destination") + "\n");
			pwr.write(accessCmd+"\n");
			pwr.write(executeCmd + "\n");
			pwr.write(mailCmd + "\n");
			log.info("PBS Script File generation successful");

			System.out.println("File generation successful");
			pwr.close();
			return fileName;

		} catch (Exception e) {
			throw new ExceptionHandler(e);
		} finally {
		}
	}

	public void replaceProcessName(String pbsFile,String dirPath) throws IOException, ExceptionHandler {
		
		SSHPropertyHandler sph = new SSHPropertyHandler();
		Properties pr = sph.getPropertyMap();
		String filePath = pr.getProperty("scriptdirectory");
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

			FileWriter writer = new FileWriter(filePath + PbsConstants.mailScriptDest);
			writer.write(modifiedContent);
			writer.close();
		} catch (IOException e) {
			throw new ExceptionHandler(e);

		}
	}

}