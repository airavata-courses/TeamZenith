package org.airavata.teamzenith.drivers;

import java.io.IOException;
import java.util.Properties;

import org.airavata.teamzenith.config.SSHPropertyHandler;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.ssh.SshUtil;
import org.airavata.teamzenith.utils.PbsConstants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JobManagementImpl implements JobManagement {
	
	private static final Logger LOGGER = LogManager.getLogger(JobManagement.class);

	@Override
	public boolean submitJob(Session session, String artifact) throws IOException, JSchException {
		SshUtil ssh = new SshUtil();
		try {

			String qsubCommand = PbsConstants.torqueCmd + " "+ artifact;
			System.out.println("Command is " + qsubCommand);
			if (ssh.executeCommand(session, qsubCommand) != true) {
				System.out.println("Job Scheduling Failed");
				return false;
			}

			return true;
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



}
