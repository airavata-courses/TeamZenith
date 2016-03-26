package org.airavata.teamzenith.webmethods;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import org.airavata.teamzenith.dao.JobData;
import org.airavata.teamzenith.dao.JobDataDao;
import org.airavata.teamzenith.dao.JobDetails;
import org.airavata.teamzenith.dao.UserData;
import org.airavata.teamzenith.dao.UserDataDao;
import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.dao.UserJobData;
import org.airavata.teamzenith.dao.UserJobDataDao;
import org.airavata.teamzenith.drivers.FileManagementImpl;
import org.airavata.teamzenith.drivers.JobManagementImpl;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.ssh.SSHConnectionHandler;
import org.airavata.teamzenith.utils.ScriptGenUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

@Configuration
@ComponentScan("org.airavata.teamzenith.*")
@EnableAutoConfiguration

public class SubmitJob {
	private static final Logger LOGGER = LogManager.getLogger(SubmitJob.class);
	

	
	public boolean submit(JobDetails jd, UserDetails uDetail, UserJobDataDao userjobDao,JobDataDao jobDao,UserDataDao userDao) throws IOException, ExceptionHandler, JSchException{

		FileManagementImpl fm=new FileManagementImpl();
		JobManagementImpl jm=new JobManagementImpl();
		//SSHConnectionHandler sch=new SSHConnectionHandler();
		try{
			ScriptGenUtil sgu=new ScriptGenUtil();
			String scriptFile=sgu.generateScript(jd, uDetail);
			Session session=SSHConnectionHandler.createSession(uDetail); 
			LOGGER.info("Job type is"+jd.getJobType());
			SSHConnectionHandler.sessionStart(session);
			/*
			 * Transfer PBS script file to Karst
			 */
			fm.putFile(session, scriptFile, uDetail.getTargetPath());
			
			/*
			 * Transfer job file to Karst
			 */
			if(jd.getJobType().equals("gro")){
				for(int i=0;i<jd.getJobFile().length;i++)
					fm.putFile(session, jd.getJobFile()[i],  uDetail.getTargetPath());
			}
			else{
				fm.putFile(session, jd.getJobFile()[0], uDetail.getTargetPath());
				if(jd.isCompileReqd()){
					fm.compileFile(session, "C", jd.getJobFile()[0],uDetail.getTargetPath()); 		
				}
			}			
			//fm.putFile(session, mailScript, uDetail.getTargetPath());

			
			String commandRes=jm.submitJob(session, uDetail.getTargetPath()+scriptFile);
			String fname = commandRes;
			int pos = fname.lastIndexOf(".");
			if (pos > 0) {
			    fname = fname.substring(0, pos);
			}
			//String opToken[]=commandRes.split("\\.");
			LOGGER.info("Job ID is :"+ fname);
			
			if(fname.length()>0){
				jd.setJobId(fname);
				//Persist data
				Random rand = new Random();

				int  n = rand.nextInt(50000) + 1;
				UserData usr=new UserData(uDetail.getUserName(),uDetail.getEmail());
				Long uId=userDao.getUserId(uDetail.getUserName());
				UserJobData job = new UserJobData(jd.getJobId(),jd.getJobName(),jd.getExecEnv(),uId );
				 userjobDao.create(job);
				 UserJobData jobDat=userjobDao.getByName(jd.getJobId());
				 LOGGER.info("--------THE job ID is "+jobDat.getJobId());
				JobData jobdata=new JobData(jobDat.getJobId(),jd.getJobName(),jd.getNumNodes(),
						 uDetail.getTargetPath(),jd.getProcessorPerNode(),jd.getWallTime(),jd.getJobType(),
						 jd.getJobFile()[0],(jd.isCompileReqd())?"Y":"N","C");
				 jobDao.create(jobdata);
				
				
				return true;
			}
			
			return false;
		}

		catch(IOException e){
			LOGGER.error("IO ERROR: PBS script not found in submit Job");
			throw new IOException("IO ERROR: PBS script not found in submit Job ",e);
		}
		catch(ExceptionHandler e){
			LOGGER.error("ExceptionHandler: PBS script not found in submit Job");
			throw new IOException("Excep ERROR: PBS script not found in submit Job ",e);
		}
		catch(JSchException e){
			e.printStackTrace();
			LOGGER.error("Jsch ERROR: Authentication failure, check the credentials");
			throw new JSchException("Jsch ERROR: Authentication failure, check the credentials",e);
		}
	}
}
