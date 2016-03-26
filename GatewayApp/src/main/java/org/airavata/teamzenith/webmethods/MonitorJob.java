package org.airavata.teamzenith.webmethods;

import java.io.IOException;

import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.drivers.JobManagementImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;

public class MonitorJob {
	
	private static final Logger LOGGER = LogManager.getLogger(MonitorJob.class);
	
	public String getJobStatus(UserDetails user, String jobNumber) throws IOException, JSchException{
		
		
		UserSession getSession = UserSession.login(user);
        JobManagementImpl job = new JobManagementImpl();
        try{
        	System.out.println(jobNumber);
        	String status=job.getJobStatus(getSession.getUserSession(), jobNumber);
    		return status;

        }catch(JSchException e){
        	LOGGER.error("SSH ERROR: SSH session invalid in monitor job");
        	throw new JSchException("SSH ERROR: SSH session invalid in monitor job",e);
        }catch(IOException e){
        	LOGGER.error("I/O ERROR: Problem with I/O stream in job monitoring");
        	throw new IOException("I/O ERROR: Problem with I/O stream in job monitoring",e);
        }
        
		
	
	}

}
