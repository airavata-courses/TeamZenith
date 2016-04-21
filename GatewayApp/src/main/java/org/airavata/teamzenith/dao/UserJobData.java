package org.airavata.teamzenith.dao;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
    /*@ManyToOne
  //  @JoinColumn(name="UserId", nullable=false)
    private UserData uData;
    
    @OneToMany
  //  @JoinColumn(name="JobId", nullable=false)
    private List<JobData> ujData;
	// Public methods

	public List<JobData> getUjData() {
		return ujData;
	}

	public void setUjData(List<JobData> ujData) {
		this.ujData = ujData;
	}

	public UserData getuData() {
		return uData;
	}

	public void setuData(UserData uData) {
		this.uData = uData;
	}
*/
	public UserJobData() { }

	public UserJobData(long id) { 
		this.JobId = id;
	}

	public UserJobData(String jobNo, String jobName,String target,long userId) {
		//this.JobId = jobId;
		this.JobNumber= jobNo;
		this.JobName=jobName;
		this.TargetNode=target;
		this.UserId=userId;
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