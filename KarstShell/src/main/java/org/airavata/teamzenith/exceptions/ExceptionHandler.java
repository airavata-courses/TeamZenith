package org.airavata.teamzenith.exceptions;

import org.airavata.teamzenith.ssh.SshUtil;

import com.jcraft.jsch.Session;

public class ExceptionHandler extends Exception{
	/**
	 * Main Exception handling class
	 */
	private static final long serialVersionUID = 1L;



	public ExceptionHandler(Exception e) {
		
		// TODO Auto-generated constructor stub
		super(e);
	}

	public static void cleanUp(SshUtil ssh, Session session) throws ExceptionHandler{
		try{
			//ssh.sessionStop(session);
		}
		finally{
		}
	}
}