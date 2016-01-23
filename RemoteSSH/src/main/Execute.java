package main;

import com.jcraft.jsch.Session;

public class Execute {
	
	public static void main(String[] args){
		
		/* Create SSH Session*/
		SSHManager ssh = new SSHManager();
		Session session = ssh.createSession();
		ssh.sessionStart(session);
		
		/*Transfer related files to remote machine */
		String scriptSource = "C:\\Users\\Anuj\\workspace\\RemoteSSH\\src\\script\\bash.sh";
		String scriptDest = "/N/u/anujbhan/Karst/bash.sh";
		String inFileSource = "C:\\Users\\Anuj\\workspace\\RemoteSSH\\src\\script\\test.c";
		String inFileDest = "/N/u/anujbhan/Karst/test.c";
		if(ssh.ScpTo(session, scriptSource, scriptDest)!=true){
			System.out.println("Script file copy failed");
		}
		if(ssh.ScpTo(session, inFileSource, inFileDest)!=true){
			System.out.println("Input file copy failed");
		}
		/* File Transfer success */
		
		/*Compile the input File*/
		String compileCommand = "mpicc "+inFileDest+" -o bin.out";
		if(ssh.executeCommand(session, compileCommand) != true){
			System.out.println("Input file compilation failed");
		}
		/* Compile success */
		
		/* Schedule job */
		String qsubCommand = "qsub "+scriptDest;
		if(ssh.executeCommand(session, qsubCommand) != true){
			System.out.println("Job Scheduling Failed");
		}
		/* Job Scheduled successfully */
		
		
		ssh.sessionStop(session);
		/* Session End */
	}

}
