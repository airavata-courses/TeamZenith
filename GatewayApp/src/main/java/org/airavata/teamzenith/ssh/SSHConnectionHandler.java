package org.airavata.teamzenith.ssh;

import java.io.IOException;

import org.airavata.teamzenith.dao.UserDetails;
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
			
			if(ud.getPassphrase().isEmpty())
				jsch.addIdentity(privateKeyFile);
			else
				jsch.addIdentity(privateKeyFile, ud.getPassphrase());
			Session session ;
			LOGGER.info("Execenv is "+ ud.getHostName());
			if(ud.getHostName().equals("Karst"))
				session = jsch.getSession(ud.getUserName(), SSHConstants.hostName, SSHConstants.port);
			else
				session = jsch.getSession(ud.getUserName(), SSHConstants.bigRedHostName, SSHConstants.port);
			if(LOGGER.isInfoEnabled()){
				LOGGER.info("Username is "+ud.getUserName()+" and "+SSHConstants.hostName);
			}
			 //session.setPassword("best");
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
		if(LOGGER.isInfoEnabled())
			LOGGER.info("Session disconnected !!!");

	}


}
