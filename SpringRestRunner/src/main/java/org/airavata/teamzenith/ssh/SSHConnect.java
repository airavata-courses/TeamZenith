package org.airavata.teamzenith.ssh;

import java.io.IOException;
import java.util.Properties;

import org.airavata.teamzenith.config.PbsConfig;
import org.airavata.teamzenith.config.SSHPropertyHandler;
import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class SSHConnect {
	
	private static final Logger LOGGER = LogManager.getLogger(SSHConnect.class);
	
	public Session createSession(UserDetails ud) throws IOException, JSchException {
		try{
			JSch jsch=new JSch();
			
			String privateKeyFile= ud.getKeyPath();
			jsch.addIdentity(privateKeyFile);

			Session session = jsch.getSession(ud.getUserName(), SSHConstants.hostName, SSHConstants.port);
			//System.out.println("session created.");
			session.setConfig("StrictHostKeyChecking", "no");
			return session;
		}
		catch(JSchException e){
			throw new JSchException("SSH ERROR: Unable to create session", e);
		}
	}

	public void sessionStart(Session session) throws JSchException{
		try {
			session.connect();
			LOGGER.info("Session connected successfully !!!");
		} 
		catch(JSchException e){
			throw new JSchException("SSH ERROR: Unable to connect session", e);
		}
	}


	public void sessionStop(Session session) {
		
			session.disconnect();
			LOGGER.info("Session disconnected !!!");
		
	}

}
