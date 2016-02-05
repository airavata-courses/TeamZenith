package org.airavata.teamzenith.drivers;

public interface FileManagement {
	public boolean putFile(String localFile, String remotePath);
	public boolean getFile(String localPath, String remoteFile);
}
