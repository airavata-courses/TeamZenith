package org.airavata.teamzenith.input;

import java.util.Scanner;

import org.airavata.teamzenith.config.PbsConfig;

public class InputMain {
	private Scanner inputScan = new Scanner(System.in);

	public PbsConfig fetchInput() {
		PbsConfig pc = new PbsConfig();

		System.out.println("Enter the file with location:");
		pc.setFilePath(inputScan.next());
		
		System.out.println("Compilation required? (Y/N):");
		pc.setCompile(inputScan.next());

		System.out.println("Enter the job name:");
		pc.setJobName(inputScan.next());

		System.out.println("Enter the number of nodes required:");
		pc.setNodes(inputScan.nextInt());

		System.out.println("Enter the number of processors per node:");
		pc.setPpn(inputScan.nextInt());

		System.out.println("Enter the required walltime");
		pc.setWallTime(inputScan.next());
		return pc;

	}
	public void cleanUp(){
		inputScan.close();
	}
	public static void main(String args[]){
		System.out.println("hell");
	}
}
