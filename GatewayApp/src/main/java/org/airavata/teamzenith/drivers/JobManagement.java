package org.airavata.teamzenith.drivers;

import java.io.IOException;
import java.util.Properties;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public interface JobManagement {
	
	public String submitJob(Session session, String artifact) throws IOException, JSchException;
    public String getJobStatus(Session session, String jobNumber) throws IOException, JSchException;
	String getCancelJob(Session session, String jobNumber) throws IOException, JSchException;
}
