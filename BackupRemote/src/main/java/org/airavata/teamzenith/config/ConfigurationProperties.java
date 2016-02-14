package org.airavata.teamzenith.config;

public class ConfigurationProperties {
private int nodes=1;
private int ppn=2;
private String wallTime;
private String jobName;
private String userName;
private String filePath;
private String isCompile;
private String emailAddress;
private boolean hasMoreInput;
private String workSpace;
private String privateKeyFileName;
private String passPhrase;
public String getPassPhrase() {
	return passPhrase;
}
public void setPassPhrase(String passPhrase) {
	this.passPhrase = passPhrase;
}
private String destinationDirectory;

public boolean isHasMoreInput() {
	return hasMoreInput;
}
public void setHasMoreInput(boolean hasMoreInput) {
	this.hasMoreInput = hasMoreInput;
}
public String isCompile() {
	return isCompile;
}
public void setCompile(String isCompile) {
	this.isCompile = isCompile;
}
public int getNodes() {
	return nodes;
}
public void setNodes(int nodes) {
	this.nodes = nodes;
}
public int getPpn() {
	return ppn;
}
public void setPpn(int ppn) {
	this.ppn = ppn;
}
public String getWallTime() {
	return wallTime;
}
public void setWallTime(String wallTime) {
	if(wallTime==null)
		this.wallTime="10:00";
	else
		this.wallTime = wallTime;
}
public String getJobName() {
	return jobName;
}
public void setJobName(String jobName) {
	if(jobName==null)
		this.jobName = "RandomName";
	else
		this.jobName=jobName;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getFilePath() {
	return filePath;
}
public void setFilePath(String filePath) {
	this.filePath = filePath;
}
public String getEmailAddress() {
	return emailAddress;
}
public void setEmailAddress(String emailAddress) {
	this.emailAddress = emailAddress;
}
public String getWorkSpace() {
	return workSpace;
}
public void setWorkSpace(String workSpace) {
	this.workSpace = workSpace;
}
public String getPrivateKeyFileName() {
	return privateKeyFileName;
}
public void setPrivateKeyFileName(String privateKeyFileName) {
	this.privateKeyFileName = privateKeyFileName;
}
public String getDestinationDirectory() {
	return destinationDirectory;
}
public void setDestinationDirectory(String destinationDirectory) {
	this.destinationDirectory = destinationDirectory;
}

}