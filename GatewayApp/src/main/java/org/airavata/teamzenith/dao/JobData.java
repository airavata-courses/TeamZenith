package org.airavata.teamzenith.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "TZ_JOB_DATA")
public class JobData {

	// The entity fields (private)  

	@NotNull
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long JobDataId;

	@NotNull
	private long JobId;
	
	@NotNull
	private String JobName;

	private long Nodes;

	private String JobPath;
	
	private int Ppn;
	
	private String WallTime;
	
	private String JobType;
	
	private String JobFileName;
	
	private String CompilerFlag;
	
	private String JobStatus;
/*    @ManyToOne
  //  @JoinColumn(name="JobId", nullable=false)
    private UserJobData uData;
		// Public methods
    @ManyToOne
  //  @JoinColumn(name="JobId", nullable=false)
    private UserData usData;
    
	public UserData getUsData() {
		return usData;
	}

	public void setUsData(UserData usData) {
		this.usData = usData;
	}

	public UserJobData getuData() {
		return uData;
	}

	public void setuData(UserJobData uData) {
		this.uData = uData;
	}
*/
	public JobData() { }

	public JobData(long id) { 
		this.JobId = id;
	}
	public JobData(long jobId, String jobName, long nodes, String path, int ppn, 
			String walltime, String jobtype, String jobfilename, String compileflag, String jobStatus) {
		this.JobId = jobId;
		this.JobName=jobName;
		this.Nodes= nodes;
		this.JobPath=path;
		this.Ppn=ppn;
		this.WallTime=walltime;
		this.JobType=jobtype;
		this.JobFileName=jobfilename;
		this.CompilerFlag=compileflag;
		this.JobStatus=jobStatus;
	}


	public long getJobDataId() {
		return JobDataId;
	}

	public void setJobDataId(long jobDataId) {
		JobDataId = jobDataId;
	}

	public long getJobId() {
		return JobId;
	}

	public void setJobId(long jobId) {
		JobId = jobId;
	}

	public String getJobName() {
		return JobName;
	}

	public void setJobName(String jobName) {
		JobName = jobName;
	}

	public long getNodes() {
		return Nodes;
	}

	public void setNodes(long nodes) {
		Nodes = nodes;
	}

	public String getJobPath() {
		return JobPath;
	}

	public void setJobPath(String jobPath) {
		JobPath = jobPath;
	}

	public int getPpn() {
		return Ppn;
	}

	public void setPpn(int ppn) {
		Ppn = ppn;
	}

	public String getWallTime() {
		return WallTime;
	}

	public void setWallTime(String wallTime) {
		WallTime = wallTime;
	}

	public String getJobType() {
		return JobType;
	}

	public void setJobType(String jobType) {
		JobType = jobType;
	}

	public String getJobFileName() {
		return JobFileName;
	}

	public void setJobFileName(String jobFileName) {
		JobFileName = jobFileName;
	}

	public String getCompilerFlag() {
		return CompilerFlag;
	}

	public void setCompilerFlag(String compilerFlag) {
		CompilerFlag = compilerFlag;
	}

	public String getJobStatus() {
		return JobStatus;
	}

	public void setJobStatus(String jobStatus) {
		JobStatus = jobStatus;
	}
	

}