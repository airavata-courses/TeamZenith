package org.airavata.teamzenith.init.controller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.web.multipart.MultipartFile;
public class DataPost implements Serializable {
	
//		private String username;
		private String jobname;
		private String message;
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getJobname() {
			return jobname;
		}

		public void setJobname(String jobname) {
			this.jobname = jobname;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getJobstate() {
			return jobstate;
		}

		public void setJobstate(String jobstate) {
			this.jobstate = jobstate;
		}

		public String getNodect() {
			return nodect;
		}

		public void setNodect(String nodect) {
			this.nodect = nodect;
		}

		public String getWalltime() {
			return walltime;
		}

		public void setWalltime(String walltime) {
			this.walltime = walltime;
		}
		private String user;
		private String jobstate;
		private String nodect;
		private String walltime;		
	    private String name;
	    
	    public String getName() {
	        return name;
	    }
	    
	    public void setBean(String op)
	    {
	    	Properties prop = new Properties();
	    	InputStream stream = new ByteArrayInputStream(op.getBytes(StandardCharsets.UTF_8));
			try {
				prop.load(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(prop.getProperty("Resource_List.nodect"));
			System.out.println(prop.getProperty("Job_Name"));
			System.out.println(prop.getProperty("job_state"));
			System.out.println(prop.getProperty("Resource_List.walltime"));
			System.out.println(prop.getProperty("euser"));
			prop.getProperty("Resource_List.nodect");
			
			setNodect(prop.getProperty("Resource_List.nodect"));
			setJobname(prop.getProperty("Job_Name"));
			setJobstate(prop.getProperty("job_state"));
			setWalltime(prop.getProperty("Resource_List.walltime"));
			setUser(prop.getProperty("euser"));
			setName(prop.getProperty("Job_Owner"));
					
			
	    
	    
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
//		public String getUsername() {
//			return username;
//		}
//		public void setUsername(String username) {
//			this.username = username;
//		}

}
