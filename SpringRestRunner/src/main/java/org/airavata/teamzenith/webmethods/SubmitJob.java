package org.airavata.teamzenith.webmethods;

import java.io.IOException;
import java.sql.SQLException;

import org.airavata.teamzenith.dao.JobDetails;
import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.drivers.DataManagementImpl;
import org.airavata.teamzenith.drivers.FileManagementImpl;
import org.airavata.teamzenith.drivers.JobManagement;
import org.airavata.teamzenith.drivers.JobManagementImpl;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.utils.ScriptGenUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SubmitJob {
	private static final Logger LOGGER = LogManager.getLogger(SubmitJob.class);

	public boolean submit(JobDetails jd) throws ClassNotFoundException, SQLException, IOException, ExceptionHandler, JSchException{
		String userName="to be decided";
		DataManagementImpl dm=new DataManagementImpl();
		FileManagementImpl fm=new FileManagementImpl();
		JobManagementImpl jm=new JobManagementImpl();
		try{
		UserDetails uDetail=dm.fetchUserData(userName);
		ScriptGenUtil sgu=new ScriptGenUtil();
		String scriptFile=sgu.generateScript(jd, uDetail);
		String mailScript=sgu.modifyMailArgs(scriptFile, uDetail.getKeyPath());
		Session session=null; //TODO how to get session here
		fm.putFile(session, scriptFile, uDetail.getTargetPath());
		fm.putFile(session, mailScript, uDetail.getTargetPath());
		fm.compileFile(session, "C", scriptFile); //TODO change scriptfile to file to be compiled
		jm.submitJob(session, scriptFile);
		return true;
		}
		catch(ClassNotFoundException e){
			LOGGER.error("SCRIPT ERROR: PBS script not found in submit Job");
			throw new ClassNotFoundException("SCRIPT ERROR: PBS script not found in submit Job ",e);
		}
		catch(SQLException e){
			LOGGER.error("SCRIPT ERROR: PBS script not found in submit Job");
			throw new SQLException("SCRIPT ERROR: PBS script not found in submit Job ",e);
		}
		catch(IOException e){
			LOGGER.error("SCRIPT ERROR: PBS script not found in submit Job");
			throw new IOException("SCRIPT ERROR: PBS script not found in submit Job ",e);
		}
		catch(ExceptionHandler e){
			LOGGER.error("SCRIPT ERROR: PBS script not found in submit Job");
			throw new IOException("SCRIPT ERROR: PBS script not found in submit Job ",e);
		}
		catch(JSchException e){
			LOGGER.error("SCRIPT ERROR: PBS script not found in submit Job");
			throw new JSchException("SCRIPT ERROR: PBS script not found in submit Job ",e);
		}
	}
}
