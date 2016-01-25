package org.airavata.teamzenith.ssh;

import java.io.IOException;
import java.util.Properties;

import org.airavata.teamzenith.config.SSHPropertyHandler;
import org.airavata.teamzenith.utils.PbsConstants;

import com.jcraft.jsch.Session;

public class SshManager {

	public SshManager() {

	}

	public boolean transferFile(String fileName, String dest) throws IOException {
		SSHUtil ssh = new SSHUtil();
		Session session = ssh.createSession();
		ssh.sessionStart(session);
		SSHPropertyHandler sph;
		sph = new SSHPropertyHandler();
		/* Transfer related files to remote machine */
		Properties pr = sph.getPropertyMap();
		String scriptSource = pr.getProperty("scriptdirectory") + fileName;
		if (dest == null)
			dest = pr.getProperty("destination");
		String scriptDest = dest + fileName;
		if (ssh.ScpTo(session, scriptSource, scriptDest) != true) {
			System.out.println("Script file copy failed");
			return false;
		}
		ssh.sessionStop(session);

		return true;

	}

	public boolean compileSource(String language, String artifact) throws IOException {

		SSHPropertyHandler sph;
		Properties pr;
		sph = new SSHPropertyHandler();
		SSHUtil ssh1 = new SSHUtil();
		Session session1 = ssh1.createSession();
		ssh1.sessionStart(session1);
		pr = sph.getPropertyMap();
		String destSource = pr.getProperty("destination") + artifact;
		String compileCommand = PbsConstants.compileCmd + " " + destSource + " -o " + artifact + ".out";
		System.out.println("Compile command is " + compileCommand);
		if (ssh1.executeCommand(session1, compileCommand) != true) {
			System.out.println("Input file compilation failed");
			return false;
		}
		ssh1.sessionStop(session1);
		return true;

	}

	public boolean submitJob() throws IOException {
		SSHPropertyHandler sph;
		sph = new SSHPropertyHandler();
		Properties pr;
		pr = sph.getPropertyMap();
		sph = new SSHPropertyHandler();
		SSHUtil ssh1 = new SSHUtil();
		Session session1 = ssh1.createSession();
		String qsubCommand = PbsConstants.torqueCmd + " " + pr.getProperty("destination");
		if (ssh1.executeCommand(session1, qsubCommand) != true) {
			System.out.println("Job Scheduling Failed");
			return false;
		}
		ssh1.sessionStop(session1);
		return true;
	}
	// public void terminateSession(){
	// ssh.sessionStop(session);
	// }
	// public static void main(String[] args) throws IOException {
	//
	// /* Create SSH Session */
	// SSHManager ssh = new SSHManager();
	// Session session = ssh.createSession();
	// ssh.sessionStart(session);
	//
	// /* Transfer related files to remote machine */
	//// Properties pr = sph.getPropertyMap();
	//// String scriptSource =
	// "C:\\Users\\Anuj\\workspace\\RemoteSSH\\src\\script\\bash.sh";
	//// String scriptDest = "/N/u/anujbhan/Karst/bash.sh";
	//// String inFileSource =
	// "C:\\Users\\Anuj\\workspace\\RemoteSSH\\src\\script\\test.c";
	//// String inFileDest = "/N/u/anujbhan/Karst/test.c";
	//// if (ssh.ScpTo(session, scriptSource, scriptDest) != true) {
	//// System.out.println("Script file copy failed");
	//// }
	//// if (ssh.ScpTo(session, inFileSource, inFileDest) != true) {
	//// System.out.println("Input file copy failed");
	//// }
	//// /* File Transfer success */
	////
	//// /* Compile the input File */
	//// String compileCommand = "mpicc " + inFileDest + " -o bin.out";
	//// if (ssh.executeCommand(session, compileCommand) != true) {
	//// System.out.println("Input file compilation failed");
	//// }
	//// /* Compile success */
	//
	// /* Schedule job */
	// String qsubCommand = "qsub " + scriptDest;
	// if (ssh.executeCommand(session, qsubCommand) != true) {
	// System.out.println("Job Scheduling Failed");
	// }
	// /* Job Scheduled successfully */
	//
	// ssh.sessionStop(session);
	// /* Session End */
	// }

}
