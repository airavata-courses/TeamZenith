package org.airavata.teamzenith.webmethods;

import java.io.IOException;

import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.drivers.FileManagementImpl;
import org.airavata.teamzenith.ssh.SSHConnectionHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class UserSession {
	
	private static final Logger LOGGER = LogManager.getLogger(FileManagementImpl.class);
	
	private Session session;
	private String userName;
	
	
	public Session getUserSession() {
		return session;
	}
	
	public String getUserName() {
		return userName;
	}
	/*default constructor */
	UserSession(){
		
	}
	/*
	 * For internal Use only
	 */
	
	UserSession(Session session, String userName){
		this.userName = userName;
		this.session = session;
	}
	
	public static UserSession login(UserDetails object){
		try {
			Session session = SSHConnectionHandler.createSession(object);
			UserSession userSessionObject = new UserSession(session, object.getUserName());
			SSHConnectionHandler.sessionStart(userSessionObject.session);
			return userSessionObject;
		} catch (IOException e) {
			LOGGER.error("Auth Error: User Authentication Failed"+e.getMessage());
            return null;
		}
		catch(JSchException e){
			LOGGER.error("Auth Error: User Authentication Failed"+e.getMessage());
            return null;
		}
		
	}
	
	public static void logout(UserSession userSessionObject){
		SSHConnectionHandler.sessionStop(userSessionObject.session);
	}

}
