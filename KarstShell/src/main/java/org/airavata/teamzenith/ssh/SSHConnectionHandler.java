package org.airavata.teamzenith.ssh;

import java.io.IOException;
import java.util.Properties;

//import org.airavata.teamzenith.config.PbsConfig;
//import org.airavata.teamzenith.config.SSHPropertyHandler;
import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class SSHConnectionHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(SSHConnectionHandler.class);
	
	public static Session createSession(UserDetails ud) throws IOException, JSchException {
		try{
			JSch jsch=new JSch();
			
			String privateKeyFile= ud.getKeyPath();
			jsch.addIdentity(privateKeyFile);
            
			Session session = jsch.getSession(ud.getUserName(), SSHConstants.hostName, SSHConstants.port);
			LOGGER.error("Username is"+ud.getUserName()+"and "+SSHConstants.hostName);
			//System.out.println("session created.");
			session.setConfig("StrictHostKeyChecking", "no");
			return session;
		}
		catch(JSchException e){
			LOGGER.error("SSH ERROR: Unable to create session");
			throw new JSchException("SSH ERROR: Unable to create session"+e.getMessage(), e);
			
		}
	}

	public static void sessionStart(Session session) throws JSchException{
		try {
			session.connect();
			LOGGER.info("Session connected successfully !!!");
		} 
		catch(JSchException e){
			throw new JSchException("SSH ERROR: Unable to connect session", e);
		}
	}


	public static void sessionStop(Session session) {
		
			session.disconnect();
			LOGGER.info("Session disconnected !!!");
		
	}
	

}
