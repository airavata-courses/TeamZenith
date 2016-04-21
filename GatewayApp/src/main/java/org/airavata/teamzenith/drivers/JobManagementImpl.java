package org.airavata.teamzenith.drivers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.airavata.teamzenith.ssh.SshUtil;
import org.airavata.teamzenith.utils.PbsConstants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JobManagementImpl implements JobManagement {

	private static final Logger LOGGER = LogManager.getLogger(JobManagement.class);

	public String submitJob(Session session, String artifact) throws IOException, JSchException {
		SshUtil ssh = new SshUtil();
		try {

			String qsubCommand = PbsConstants.torqueCmd + " "+ artifact;
			System.out.println("Command is " + qsubCommand);
			String cmdResult=ssh.executeCommand(session, qsubCommand);
			if (cmdResult.equals("")) {
				LOGGER.error("Job Scheduling Failed");
				return cmdResult;
			}
			LOGGER.info("Command Executed with response : "+ cmdResult);
			return cmdResult;
			
		} 
		catch(IOException e){
			LOGGER.error("SCRIPT ERROR: PBS script not found in submit Job");
			throw new IOException("SCRIPT ERROR: PBS script not found in submit Job ",e);
		}
		catch(JSchException e){
			LOGGER.error("SSH ERROR: Problem with session object");
			throw new JSchException("SSH ERROR: Problem with session object");
		}
	}

	public String getJobStatus(Session session, String jobNumber) throws IOException, JSchException {
		SshUtil ssh = new SshUtil();
//		String Command = new StringBuffer("qstat -f ").append(jobNumber).append(" |grep job_state").toString();
		String Command = new StringBuffer("qstat -f ").append(jobNumber).append(" |grep \'Job_Name\\|job_state\\|Resource_List.nodect\\|Job_Owner\\|Resource_List.walltime\\|euser\'").toString();
		String op=ssh.executeCommand(session, Command);
		System.out.println(op);
		Properties prop = new Properties();
		InputStream stream = new ByteArrayInputStream(op.getBytes(StandardCharsets.UTF_8));
		prop.load(stream);
		System.out.println(prop.getProperty("Resource_List.nodect"));
//		String opTokens[]=op.split("= ");
//		if(opTokens.length > 1){
//			String status=PbsConstants.statusMap.get(opTokens[1].trim());
//			return status;
//		}
		return op;
//		String Command = new StringBuffer("qstat -f ").append(jobNumber).append(" |grep \'Job_Name\b|job_state\b|Resource_List.nodect\b|Resource_List.walltime\b|euser\'").toString();
	}
	public String getCancelJob(Session session, String jobNumber) throws IOException, JSchException {
		SshUtil ssh = new SshUtil();
		String Command = new StringBuffer("qdel ").append(jobNumber).toString();
		ssh.executeCommand(session, Command);
		return null;

	}
}
