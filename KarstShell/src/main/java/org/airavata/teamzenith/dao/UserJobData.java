package org.airavata.teamzenith.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "TZ_USER_JOB_INFO")
public class UserJobData {

	// The entity fields (private)  

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long JobId;

	@NotNull
	private String JobNumber;

	@NotNull
	private long UserId;

	private String JobName;
	
	private String TargetNode;
	// Public methods

	public UserJobData() { }

	public UserJobData(long id) { 
		this.JobId = id;
	}

	public UserJobData(long jobId, String jobNo, String jobName,String target) {
		this.JobId = jobId;
		this.JobNumber= jobNo;
		this.JobName=jobName;
		this.TargetNode=target;
		this.UserId=1;
	}

	public long getJobId() {
		return JobId;
	}

	public void setJobId(long jobId) {
		JobId = jobId;
	}

	public String getJobNumber() {
		return JobNumber;
	}

	public void setJobNumber(String jobNumber) {
		JobNumber = jobNumber;
	}

	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}

	public String getJobName() {
		return JobName;
	}

	public void setJobName(String jobName) {
		JobName = jobName;
	}

	public String getTargetNode() {
		return TargetNode;
	}

	public void setTargetNode(String targetNode) {
		TargetNode = targetNode;
	}

	

}