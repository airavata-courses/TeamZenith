package org.airavata.teamzenith.drivers;

import java.io.IOException;

import org.airavata.teamzenith.ssh.SshUtil;
import org.airavata.teamzenith.utils.PbsConstants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JobManagementImpl implements JobManagement {

	private static final Logger LOGGER = LogManager.getLogger(JobManagement.class);

	@Override
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

	@Override
	public String getJobStatus(Session session, String jobNumber) throws IOException, JSchException {
		SshUtil ssh = new SshUtil();
		String Command = new StringBuffer("qstat -f ").append(jobNumber).append(" |grep job_state").toString();
		String op=ssh.executeCommand(session, Command);
		String opTokens[]=op.split("= ");
		if(opTokens.length > 1){
			String status=PbsConstants.statusMap.get(opTokens[1].trim());
			return status;
		}
		return "";
		
	}
	@Override
	public String getCancelJob(Session session, String jobNumber) throws IOException, JSchException {
		SshUtil ssh = new SshUtil();
		String Command = new StringBuffer("qdel ").append(jobNumber).toString();
		ssh.executeCommand(session, Command);
		return null;

	}
}
