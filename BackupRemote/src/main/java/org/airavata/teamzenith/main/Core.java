package org.airavata.teamzenith.main;

import java.io.IOException;
import org.airavata.teamzenith.config.PbsConfig;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.input.InputMain;
import org.airavata.teamzenith.ssh.SshUtil;
import org.airavata.teamzenith.ssh.SshManager;
import org.airavata.teamzenith.utils.PbsConstants;
import org.airavata.teamzenith.utils.PbsGen;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


import org.apache.log4j.Logger;
 
public class Core {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, ExceptionHandler, JSchException {
		Logger log = Logger.getLogger("org.airavata.teamzenith.log");
		System.out.println("Welcome to Remote job submission on Karst");
		boolean moreInput=true;
		InputMain im=new InputMain();
		PbsConfig pbsConf=new PbsConfig();
		PbsGen pGen=new PbsGen();
		SshManager ssm=new SshManager();
		ExceptionHandler exObj;
		SshUtil ssh = new SshUtil();
		
		
		try{

		while(moreInput){
			moreInput=false;
			pbsConf=im.fetchInput();
			Session session = ssh.createSession(pbsConf);
			ssh.sessionStart(session);
			/* Transfer the executable or source file to Karst */
			if(!ssm.transferFile(session, pbsConf.getFilePath(),pbsConf.getWorkSpace(),null)){
				log.error("Source file transfer failed");
				return;
			}
			log.debug(pbsConf.getFilePath() +" transferred to Karst");
			
			/* Check if compilation is required */
			if(pbsConf.isCompile().equals("Y")){
				log.info("Compilation required");
				ssm.compileSource(session, "C", pbsConf.getFilePath());
				pbsConf.setFilePath(pbsConf.getFilePath()+".out");
				}
			
			String pbsScript=pGen.generateScript(pbsConf);
			
			/* Transfer the generated script to Karst */
			if(!ssm.transferFile(session, pbsScript,pbsConf.getWorkSpace(),null)){
				log.error("PBS Script transfer failed");
				return;
			}
			log.debug(pbsScript +" transferred to Karst");
			
			/* Modify and transfer script for sendmail*/
			
			pGen.replaceProcessName(pbsScript,pbsConf.getWorkSpace());
			if(!ssm.transferFile(session, PbsConstants.mailScriptDest,pbsConf.getWorkSpace(),null)){
				log.error("Sendmail file transfer failed");
				return;
			}
			log.debug(PbsConstants.mailScriptDest +" transferred to Karst");
			
			if(!ssm.submitJob(session, pbsScript)){
				log.error("Sendmail file transfer failed");
				return;
			}
			log.debug(pbsScript +" transferred to Karst");
			
			System.out.println("Job submitted successfully");
			ExceptionHandler.cleanUp(ssh, session);

		}	
		} catch (Exception e){

			exObj = new ExceptionHandler(e);
			log.error(e);

		} finally{
		}

	}
}