package org.airavata.teamzenith.drivers;

import java.io.IOException;

import org.airavata.teamzenith.exceptions.ExceptionHandler;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public interface JobManagement {
	
	public boolean submitJob(Session session, String artifact) throws IOException, JSchException;
    public String getJobStatus(Session session, String jobNumber) throws IOException, JSchException;
}
