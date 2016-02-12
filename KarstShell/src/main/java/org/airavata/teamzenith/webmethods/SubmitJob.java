package org.airavata.teamzenith.webmethods;

import java.io.IOException;
import java.sql.SQLException;

import org.airavata.teamzenith.dao.JobDetails;
import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.drivers.FileManagementImpl;
import org.airavata.teamzenith.drivers.JobManagement;
import org.airavata.teamzenith.drivers.JobManagementImpl;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.ssh.SSHConnectionHandler;
import org.airavata.teamzenith.utils.PbsConstants;
import org.airavata.teamzenith.utils.ScriptGenUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SubmitJob {
	private static final Logger LOGGER = LogManager.getLogger(SubmitJob.class);

	public boolean submit(JobDetails jd, UserDetails uDetail) throws IOException, ExceptionHandler, JSchException{
		String userName=uDetail.getUserName();
		FileManagementImpl fm=new FileManagementImpl();
		JobManagementImpl jm=new JobManagementImpl();
		SSHConnectionHandler sch=new SSHConnectionHandler();
		try{
		//UserDetails uDetail=dm.fetchUserData(userName);
		ScriptGenUtil sgu=new ScriptGenUtil();
		String scriptFile=sgu.generateScript(jd, uDetail);
		//String mailScript=sgu.modifyMailArgs(scriptFile, uDetail.getKeyPath());
		Session session=sch.createSession(uDetail); //TODO how to get session here
		sch.sessionStart(session);
		fm.putFile(session, scriptFile, uDetail.getTargetPath());
		fm.putFile(session, jd.getJobFile(), uDetail.getTargetPath());
		//fm.putFile(session, mailScript, uDetail.getTargetPath());
		if(jd.isCompileReqd()){
			fm.compileFile(session, "C", jd.getJobFile(),uDetail.getTargetPath()); 		
		}
		String commandRes=jm.submitJob(session, uDetail.getTargetPath()+scriptFile);
		String opToken[]=commandRes.split("\\.");
		jd.setJobId(opToken[0]);
		return true;
		}
		
				catch(IOException e){
			LOGGER.error("IO ERROR: PBS script not found in submit Job");
			throw new IOException("IO ERROR: PBS script not found in submit Job "+e.getMessage(),e);
		}
		catch(ExceptionHandler e){
			e.printStackTrace();
			LOGGER.error("Excep ERROR: PBS script not found in submit Job");
			throw new IOException("Excep ERROR: PBS script not found in submit Job ",e);
		}
		catch(JSchException e){
			e.printStackTrace();
			LOGGER.error("Jsch ERROR: PBS script not found in submit Job");
			throw new JSchException("Jch ERROR: PBS script not found in submit Job "+e.getMessage(),e);
		}
	}
}
