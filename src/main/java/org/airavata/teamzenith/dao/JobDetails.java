package org.airavata.teamzenith.dao;

public class JobDetails {
	private String source;
	private int numNodes;
	private String wallTime;
	private int numCores;
	private boolean isCompileReqd;
	private int processorPerNode;
	private String jobName;
	private String[] jobFile;
	private String jobId;
	private String jobType;
	private String execEnv;
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
	public int getProcessorPerNode() {
		return processorPerNode;
	}
	public void setProcessorPerNode(int processorPerNode) {
		this.processorPerNode = processorPerNode;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String[] getJobFile() {
		return jobFile;
	}
	public void setJobFile(String[] jobFile) {
		this.jobFile = jobFile;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public String getExecEnv() {
		return execEnv;
	}
	public void setExecEnv(String execEnv) {
		this.execEnv = execEnv;
	}
}
