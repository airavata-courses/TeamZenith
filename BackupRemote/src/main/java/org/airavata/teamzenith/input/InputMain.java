package org.airavata.teamzenith.input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.airavata.teamzenith.config.ConfigurationProperties;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.utils.FileUtils;

public class InputMain {
	private Scanner inputScan = new Scanner(System.in);

	public ConfigurationProperties fetchInput() throws IOException, ExceptionHandler {
		ConfigurationProperties pc = new ConfigurationProperties();
		FileUtils fu=new FileUtils();
		
		System.out.println("Enter the workspace directory:");
		String sourceDir=inputScan.next();
		if(fu.checkDir(sourceDir))
			pc.setWorkSpace(sourceDir);
		else
			throw new FileNotFoundException("Directory not found");
		
		System.out.println("Enter the username:");
		String username=inputScan.next();
		pc.setUserName(username);
		
		System.out.println("Enter the Private key filname:");
		String PrivateKey=inputScan.next();
		pc.setPrivateKeyFileName(PrivateKey);
		
		System.out.println("Enter the PassPhrase for Private key:");
		String passPhrase=inputScan.next();
		pc.setPassPhrase(passPhrase);
		
		
		System.out.println("Enter the source code filename:");
		String sourceFile=inputScan.next();
		pc.setFilePath(sourceFile);
		
		
		System.out.println("Enter the remote workspace Directory:");
		String DestFolder=inputScan.next();
		pc.setDestinationDirectory(DestFolder);
		

		System.out.println("Compilation required? (Y/N):");
		pc.setCompile(inputScan.next());

		System.out.println("Enter the job name:");
		pc.setJobName(inputScan.next());

		System.out.println("Enter your email address to be notified:");
		pc.setEmailAddress(inputScan.next());

		System.out.println("Enter the number of nodes required:");
		pc.setNodes(inputScan.nextInt());

		System.out.println("Enter the number of processors per node:");
		pc.setPpn(inputScan.nextInt());

		System.out.println("Enter the required walltime(MM:SS):");
		pc.setWallTime(inputScan.next());

		System.out.println("Submit more jobs? (Y/N)");
		if (inputScan.next().equals("Y"))
			pc.setHasMoreInput(true);
		else
			pc.setHasMoreInput(false);
		return pc;

	}

	public void cleanUp() {
		inputScan.close();
	}
}