package org.airavata.teamzenith.main;

import java.io.IOException;
import org.airavata.teamzenith.config.PbsConfig;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.input.InputMain;
import org.airavata.teamzenith.ssh.SSHUtil;
import org.airavata.teamzenith.ssh.SshManager;
import org.airavata.teamzenith.utils.PbsConstants;
import org.airavata.teamzenith.utils.PbsGen;

import com.jcraft.jsch.Session;

public class Core {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Remote job submission on Karst");
		boolean moreInput=true;
		InputMain im=new InputMain();
		PbsConfig pbsConf=new PbsConfig();
		PbsGen pGen=new PbsGen();
		SshManager ssm=new SshManager();
		ExceptionHandler exObj;
		SSHUtil ssh = new SSHUtil();
		Session session = ssh.createSession();
		
		try{
			ssh.sessionStart(session);
			while(moreInput){
				moreInput=false;
				pbsConf=im.fetchInput();
				if(!ssm.transferFile(session, PbsConstants.mailScript,null))
					return;
				if(!ssm.transferFile(session, pbsConf.getFilePath(),null))
					return;
				if(pbsConf.isCompile().equals("Y")){
					ssm.compileSource(session,"C", pbsConf.getFilePath());
					pbsConf.setFilePath(pbsConf.getFilePath()+".out");
				}
				
				String pbsScript=pGen.generateScript(pbsConf);
				
				if(!ssm.transferFile(session,pbsScript,null))
					return;
				System.out.println("File transmission complete");
				if(!ssm.submitJob(session,pbsScript))
					return;
				System.out.println("Job submitted successfully");
				
			}
		} catch (Exception e){
			exObj = new ExceptionHandler(e);
			System.err.println(e);
		} finally{
			ExceptionHandler.cleanUp(ssh, session);
		}

	}

}
