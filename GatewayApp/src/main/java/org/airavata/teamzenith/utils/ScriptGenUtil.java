		package org.airavata.teamzenith.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//import org.airavata.teamzenith.config.PbsConfig;
//import org.airavata.teamzenith.config.SSHPropertyHandler;
import org.airavata.teamzenith.dao.JobDetails;
import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ScriptGenUtil {

	private static final Logger LOGGER = LogManager.getLogger(ScriptGenUtil.class);

	public String generateScript(JobDetails job, UserDetails user) throws IOException, ExceptionHandler {
		String filePath="";
		String fileName = new StringBuffer("PBS_Script_").append(System.currentTimeMillis()).append(".pbs").toString();
		if(LOGGER.isInfoEnabled())
			LOGGER.info("PBS Script Name is" + fileName);
		
		try {

			File f = new File(filePath + fileName);
			PrintWriter pwr = new PrintWriter(f, "UTF-8");
			String executeCmd;
			String lFlag = new StringBuffer(PbsConstants.pbsPrefix).append(" -l ").append(" nodes=")
							.append(job.getNumNodes()).append(":ppn=").append(job.getProcessorPerNode()).append(
							",walltime=").append(job.getWallTime()).toString();

			String nFlag = new StringBuffer(PbsConstants.pbsPrefix).append(" -N ").append(job.getJobName()).toString();
			String mailFlag = new StringBuffer(PbsConstants.pbsPrefix).append(" -m abe").toString();
			String recvFlag = new StringBuffer(PbsConstants.pbsPrefix).append(" -M ").append(user.getEmail()).toString();
			String errorFile=new StringBuffer(user.getTargetPath()).append(job.getJobName()).append(".err").toString();
			String errorPath=new StringBuffer(PbsConstants.pbsPrefix).append(" -e ").append(errorFile).toString();

			String outFile=new StringBuffer(user.getTargetPath()).append(job.getJobName()).append(".log").toString();
			String outPath=new StringBuffer(PbsConstants.pbsPrefix).append(" -o ").append(outFile).toString();
			if(job.getJobType().equals(PbsConstants.gromacs)){
				
				executeCmd= new StringBuffer(PbsConstants.gromacsGmx).append(PbsConstants.gromacsGrompp).append(PbsConstants.gromacsMdrun).toString();
			}
			else{
				if(job.isCompileReqd()){
					if(job.getExecEnv().equals("Karst"))
						executeCmd = new StringBuffer("./").append(job.getJobFile()[0]).append(".out").toString();
					else
						executeCmd = new StringBuffer(PbsConstants.bigRedJobRun).append
						(" ").append(job.getJobFile()[0]).append(".out").toString();
				}
			else
				if(job.getExecEnv().equals("Karst"))
					executeCmd = new StringBuffer("./").append(job.getJobFile()[0]).toString();
				else
					executeCmd = new StringBuffer(PbsConstants.bigRedJobRun).append
					(" ").append(job.getJobFile()[0]).toString();
			
			}
				String accessCmd=new StringBuffer(PbsConstants.chmod).append(" ").append(user.getTargetPath()).toString();
	//		String mailFiles="outputFiles=`ls "+job.getJobName()+"*`;";
			String mailCmd = new StringBuffer("echo \"The log files are attached\"|").
					append(PbsConstants.mailCommand).append(" -r Zenith").append(" -s")
					.append("\"Karst execution results\" -a ").append(errorFile).append(" -a ")
					.append(outFile).append(" \"").append(user.getEmail()).append("\"").toString();
			
			String zipCmd=new StringBuffer("zip ").append(job.getJobName()).append(".zip ").append(errorFile).append
					(" ").append(outFile).append(" 1> /dev/null").toString();
			if(LOGGER.isInfoEnabled()){
				LOGGER.info("Mail cmd is " + mailCmd);
			}

			pwr.write(PbsConstants.hashBang + "\n");
			pwr.write(lFlag + "\n");
			pwr.write(nFlag + "\n");
			pwr.write(mailFlag + "\n");
			pwr.write(recvFlag + "\n");
			pwr.write(errorPath + "\n");
			pwr.write(outPath + "\n");
			pwr.write("cd " + user.getTargetPath() + "\n");
			if(job.getJobType().equals(PbsConstants.gromacs))
				pwr.write(PbsConstants.moduleList);
			pwr.write(accessCmd+"\n");
			pwr.write(executeCmd + "\n");
			pwr.write(mailCmd +"\n");
			pwr.write(mailCmd +"\n");
			pwr.write(zipCmd + "\n");
			if(LOGGER.isInfoEnabled()){
				LOGGER.info("PBS Script File generation successful");
			}
			
			pwr.close();
			return fileName;

		} catch (IOException e) {
			LOGGER.error("FILE ERROR: Error opening script file");
			throw new IOException("FILE ERROR: Error opening script file",e);
		} 
	}
   
       public String modifyMailArgs(String pbsFile, String dirPath) throws IOException {
		
		String filePath = pbsFile;
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
