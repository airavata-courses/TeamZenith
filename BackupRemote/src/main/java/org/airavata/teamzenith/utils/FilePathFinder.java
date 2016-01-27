package org.airavata.teamzenith.utils;
public class FilePathFinder {
	private final static String delimiter=":";
	public String splitPath(String path){
		return path.split(delimiter)[1];
		
	}
	
	public static void main(String args[]){
		FilePathFinder g=new FilePathFinder();
		System.out.println(g.splitPath("/home/atul/file:/home/atul/.m2/repository/BackupRemote/BackupRemote/0.0.1-SNAPSHOT/BackupRemote-0.0.1-SNAPSHOT-jar-with-dependencies.jar!/puttyKey.ppk"));
	}
}
