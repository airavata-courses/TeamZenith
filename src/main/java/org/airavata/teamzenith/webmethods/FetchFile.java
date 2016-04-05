package org.airavata.teamzenith.webmethods;

import java.io.IOException;

import org.airavata.teamzenith.dao.JobDetails;
import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.drivers.FileManagementImpl;
import org.airavata.teamzenith.drivers.JobManagementImpl;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.ssh.SSHConnectionHandler;
import org.airavata.teamzenith.utils.ScriptGenUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class FetchFile {
	private static final Logger LOGGER = LogManager.getLogger(SubmitJob.class);

	public String fetch(JobDetails jd, UserDetails uDetail) throws IOException, JSchException{

		FileManagementImpl fm=new FileManagementImpl();
		SSHConnectionHandler sch=new SSHConnectionHandler();
		try{
			Session session=sch.createSession(uDetail); 
			
			sch.sessionStart(session);
			String zipPath=new StringBuffer(uDetail.getTargetPath()).append(jd.getJobName()).append(".zip").toString();
			String zipName=new StringBuffer(jd.getJobName()).append(".zip").toString();
			System.out.println("zip pth is "+zipPath);
			/*
			 * Transfer PBS script file to Karst
			 */
			fm.getFile(session, zipName,zipPath);
			return zipName;
}
		catch(IOException e){
			LOGGER.error("IO ERROR: During Fetch file");
			throw new IOException("IO ERROR: During fetch file",e);
		}
		
		catch(JSchException e){
			LOGGER.error("Jsch ERROR: During fetch file");
			e.printStackTrace();
			throw new JSchException("Jch ERROR: During fetch file",e);
		}
	}}
