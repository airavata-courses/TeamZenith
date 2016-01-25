package org.airavata.teamzenith.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.airavata.teamzenith.config.PbsConfig;
import org.airavata.teamzenith.input.InputMain;
import org.airavata.teamzenith.ssh.SshManager;
import org.airavata.teamzenith.utils.PbsConstants;
import org.airavata.teamzenith.utils.PbsGen;

public class Core {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Remote job submission on Karst");
		boolean moreInput=true;
		InputMain im=new InputMain();
		PbsConfig pbsConf=new PbsConfig();
		PbsGen pGen=new PbsGen();
		SshManager ssm=new SshManager();
		
		while(moreInput){
			moreInput=false;
			pbsConf=im.fetchInput();
			if(!ssm.transferFile(PbsConstants.mailScript,null))
				return;
			if(!ssm.transferFile(pbsConf.getFilePath(),null))
				return;
			if(pbsConf.isCompile().equals("Y")){
				ssm.compileSource("C", pbsConf.getFilePath());
				pbsConf.setFilePath(pbsConf.getFilePath()+".out");
				}
			
			String pbsScript=pGen.generateScript(pbsConf);
			if(!ssm.transferFile(pbsScript,null))
				return;
			System.out.println("File transmission complete");
			if(!ssm.submitJob(pbsScript))
				return;
			System.out.println("Job submitted successfully");

		}
		
		
	}

}
