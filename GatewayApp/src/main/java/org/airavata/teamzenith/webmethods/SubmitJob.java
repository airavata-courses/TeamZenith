package org.airavata.teamzenith.webmethods;

import java.io.IOException;
import java.util.UUID;

import org.airavata.teamzenith.dao.JobData;
import org.airavata.teamzenith.dao.JobDataDao;
import org.airavata.teamzenith.dao.JobDetails;
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
	

	
	public boolean submit(JobDetails jd, UserDetails uDetail, UserJobDataDao userjobDao,JobDataDao jobDao) throws IOException, ExceptionHandler, JSchException{

		FileManagementImpl fm=new FileManagementImpl();
		JobManagementImpl jm=new JobManagementImpl();
		SSHConnectionHandler sch=new SSHConnectionHandler();
		try{
			ScriptGenUtil sgu=new ScriptGenUtil();
			String scriptFile=sgu.generateScript(jd, uDetail);
			Session session=sch.createSession(uDetail); 
			LOGGER.info("Job type is"+jd.getJobType());
			sch.sessionStart(session);
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
					fm.compileFile(session, "C", jd.getJobFile(),uDetail.getTargetPath()); 		
				}
			}			
			//fm.putFile(session, mailScript, uDetail.getTargetPath());

			
			String commandRes=jm.submitJob(session, uDetail.getTargetPath()+scriptFile);
			String opToken[]=commandRes.split("\\.");
			
			if(opToken.length>0){
				jd.setJobId(opToken[0]);
				//Persist data
				 UserJobData job = new UserJobData(123,jd.getJobId(),jd.getJobName(),"Karst" );
				 userjobDao.create(job);
				
				 JobData jobdata=new JobData(job.getJobId(),jd.getJobName(),jd.getNumNodes(),
						 uDetail.getTargetPath(),jd.getProcessorPerNode(),jd.getWallTime(),jd.getJobType(),
						 jd.getJobFile()[0],(jd.isCompileReqd())?"Y":"N");
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
			LOGGER.error("Jsch ERROR: PBS script not found in submit Job");
			throw new JSchException("Jch ERROR: PBS script not found in submit Job ",e);
		}
	}
}
