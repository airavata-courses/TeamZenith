package org.airavata.teamzenith.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.airavata.teamzenith.config.PbsConfig;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.utils.FileUtils;

public class InputMain {
	private Scanner inputScan = new Scanner(System.in);

	public PbsConfig fetchInput() throws IOException, ExceptionHandler {
		PbsConfig pc = new PbsConfig();
		FileUtils fu=new FileUtils();
		
		System.out.println("Enter the workspace directory:");
		String sourceDir=inputScan.next();
		if(fu.checkDir(sourceDir))
			pc.setWorkSpace(sourceDir);
		else
			throw new FileNotFoundException("Directory not found");
		
		System.out.println("Enter the file with location:");
		String sourceFile=inputScan.next();
		if(fu.checkFile(sourceFile))
			pc.setFilePath(sourceFile);
		else
			throw new FileNotFoundException("File not found");

		System.out.println("Compilation required? (Y/N):");
		pc.setCompile(inputScan.next());

		System.out.println("Enter the job name:");
		pc.setJobName(inputScan.next());

		System.out.println("Enter your email address:");
		pc.setEmailAddress(inputScan.next());

		System.out.println("Enter the number of nodes required:");
		pc.setNodes(inputScan.nextInt());

		System.out.println("Enter the number of processors per node:");
		pc.setPpn(inputScan.nextInt());

		System.out.println("Enter the required walltime");
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