package org.airavata.teamzenith.dao;

public class JobDetails {
private String source;
private int numNodes;
private String wallTime;
private int numCores;
private boolean isCompileReqd;
public String getSource() {
	return source;
}
public void setSource(String source) {
	this.source = source;
}
public int getNumNodes() {
	return numNodes;
}
public void setNumNodes(int numNodes) {
	this.numNodes = numNodes;
}
public String getWallTime() {
	return wallTime;
}
public void setWallTime(String wallTime) {
	this.wallTime = wallTime;
}
public int getNumCores() {
	return numCores;
}
public void setNumCores(int numCores) {
	this.numCores = numCores;
}
public boolean isCompileReqd() {
	return isCompileReqd;
}
public void setCompileReqd(boolean isCompileReqd) {
	this.isCompileReqd = isCompileReqd;
}
}
