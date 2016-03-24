package org.airavata.teamzenith.init.controller;

import java.io.BufferedOutputStream;
import org.airavata.teamzenith.dao.JobData;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.airavata.teamzenith.dao.JobData;
import org.airavata.teamzenith.dao.JobDataDao;
import org.airavata.teamzenith.dao.JobDetails;
import org.airavata.teamzenith.dao.UserData;
import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.dao.UserJobData;
import org.airavata.teamzenith.dao.UserJobDataDao;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.webmethods.CancelJob;
import org.airavata.teamzenith.webmethods.FetchFile;
import org.airavata.teamzenith.webmethods.MonitorJob;
import org.airavata.teamzenith.webmethods.SubmitJob;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.airavata.teamzenith.dao.UserData;
import org.airavata.teamzenith.dao.UserDataDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcraft.jsch.JSchException;

import scala.annotation.meta.setter;
/*
 * This is the main controller class which provides the following three REST APIs
 * upload- Accepts user and job details in order to submit a job on Karst
 * monitor- Accepts user details and job ID which is used to fetch the status of the job
 * cancel - Accepts user details and job ID which is used to cancel the job
 */
@Controller
public class RestController {


    @RequestMapping("/")
    String home() {
        return "home";
    }
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public @ResponseBody String provideUploadInfo() {
		return "A Karst job can be submitted by POSTing to this URL.";
	}

	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("path") String tPath,
			@RequestParam("filejob[]") MultipartFile[] file, @RequestParam("username") String userName,
			@RequestParam("jobname") String jobName, @RequestParam("noofnodes") String nodes,
			@RequestParam("noofppn") String ppn, @RequestParam("walltime") String wallTime, 
			@RequestParam("compreq") String isComp, @RequestParam("email") String emailId, 
			@RequestParam("file") MultipartFile ppk, @RequestParam(name = "pass", defaultValue = "null") String pass,
			@RequestParam("jType") String jobType, @RequestParam("execEnv") String env){

		if (!file[0].isEmpty()) {
			try {
				UserDetails ud=new UserDetails();
				JobDetails jd=new JobDetails();
				SubmitJob sub=new SubmitJob();

				ud.setUserName(userName);
				ud.setEmail(emailId);
				System.out.println("TPATH is"+tPath);
				if(tPath.charAt(tPath.length()-1)!='/')
					tPath=tPath+"/";
				ud.setTargetPath(tPath);
				ud.setPassphrase(pass);
				/*
				 * Write job file
				 */
				byte[] bytes = file[0].getBytes();
				for(int i=0;i<file.length;i++){
					
					BufferedOutputStream stream = 
							new BufferedOutputStream(new FileOutputStream(new File(file[i].getOriginalFilename())));
					stream.write(bytes);
					stream.close();
				}
				/*
				 * Write private key
				 */
				byte[] ppkBytes = ppk.getBytes();
				BufferedOutputStream ppkStream = 
						new BufferedOutputStream(new FileOutputStream(new File(ppk.getOriginalFilename())));
				ppkStream.write(ppkBytes);
				ppkStream.close();

				ud.setKeyPath(ppk.getOriginalFilename());
				System.out.println("ENVIRONMENT IS "+env);
				ud.setHostName(env);
				String fileNames[]=new String[file.length];
				for(int i=0;i<file.length;i++)
					fileNames[i]=file[i].getOriginalFilename();
				jd.setJobFile(fileNames);
				jd.setNumNodes(Integer.parseInt(nodes));
				jd.setProcessorPerNode(Integer.parseInt(ppn));
				jd.setWallTime(wallTime);
				jd.setJobName(jobName);
				jd.setJobType(jobType);
				jd.setExecEnv(env);
				if(isComp.equals("yes")&&jobType.equals("cust"))
					jd.setCompileReqd(true);
				else
					jd.setCompileReqd(false);

				if(sub.submit(jd, ud,userjobDao,jobDao)){
					return "Job submitted successfully with job Id "+jd.getJobId();
				}
				return "Job submission failed";
				
			} catch (IOException e) {
				return "You failed to upload " + file[0].getOriginalFilename() + " => " + e.getMessage();
			} catch (JSchException e){
				return "You failed to upload Authentication failure, Error message is => " + e.getMessage();
			} catch (ExceptionHandler e){
				return "You failed to upload Session is down, Error message is => " + e.getMessage();
			}
		} else {
			return "Upload of file" + file[0].getOriginalFilename() + " failed because the file was empty.";
		}
	}

	@RequestMapping(value="/monitor", method=RequestMethod.GET)
	public @ResponseBody String MonitorJobEndPointInfo() {
		return "A Karst job can be monitored by POSTing to this URL";
	}

	@RequestMapping(value="/monitor", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody DataPost MonitorJobEndPoint(@RequestParam("username") String name,
			@RequestParam(name = "passPhrase", defaultValue = "null") String passPhrase, 
			@RequestParam("size") String jobNumber, @RequestParam("file") MultipartFile file,@RequestParam("execEnv") String env){
//		Properties prop = new Properties();
		String value = null;
		UserDetails userObject = new UserDetails();
		DataPost dp = new DataPost();
		if (!file.isEmpty()) {
			try {
				/*
				 * Write private key
				 */
				byte[] ppkBytes = file.getBytes();
				BufferedOutputStream ppkStream = 
						new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
				ppkStream.write(ppkBytes);
				ppkStream.close();
				userObject.setKeyPath(file.getOriginalFilename());
				userObject.setUserName(name);
				userObject.setPassphrase(passPhrase);
				userObject.setHostName(env);

				MonitorJob job = new MonitorJob();
				String jStatus=job.getJobStatus(userObject, jobNumber);
				if(!jStatus.equals("")){
					
					dp.setBean(jStatus);
					//InputStream stream = new ByteArrayInputStream(jStatus.getBytes(StandardCharsets.UTF_8));
//					pr//op.load(stream);
//					System.out.println(prop.getProperty("Resource_List.nodect"));
					return dp	;

				}
				else{
//					return "Job Monitoring failed";
					return dp;
				}
				
			} catch (IOException e) {
//				return "Could not get the status for job:" + jobNumber + " ERROR => " + e.getMessage();
				dp.setMessage("Could not get the status for job:" + jobNumber + " ERROR => " + e.getMessage());
				return dp;
			} catch (JSchException e){
				dp.setMessage("Could not get the status for job:" + jobNumber + " ERROR => " + e.getMessage());
				return dp;
			}
		} else {
			dp.setMessage("Could not get the status for job:" + jobNumber + " ERROR => The private key file is invalid");
			return dp;
		}
	}
	
	@RequestMapping(value="/cancel", method=RequestMethod.GET)
	public @ResponseBody String CancelJobEndPointInfo() {
		return "A Karst job can be cancelled by POSTing to this URL";
	}

	@RequestMapping(value="/cancel", method=RequestMethod.POST)
	public @ResponseBody DataPost CancelJobEndPoint(@RequestParam("username") String name,
			@RequestParam(name = "passPhrase", defaultValue = "null") String passPhrase, 
			@RequestParam("jobnumber") String jobNumber, @RequestParam("file") MultipartFile file, @RequestParam("execEnv") String env){
		UserDetails userObject = new UserDetails();
		CancelJob job = new CancelJob();
		DataPost dpc = new DataPost();
		if (!file.isEmpty()) {
			try {
				/*
				 * Write private key
				 */
				byte[] ppkBytes = file.getBytes();
				BufferedOutputStream ppkStream = 
						new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
				ppkStream.write(ppkBytes);
				ppkStream.close();
				
				userObject.setKeyPath(file.getOriginalFilename());
				userObject.setUserName(name);
				userObject.setPassphrase(passPhrase);
				userObject.setHostName(env);
				job.getCancelJob(userObject, jobNumber);
				dpc.setMessage("Job:" + jobNumber + " Cancelled successfully");
				return dpc;

			} catch (IOException e) {
				
				dpc.setMessage("Could not cancel the job:" + jobNumber + " ERROR => " + e.getMessage());
				return dpc;
//				return "Could not cancel the job:" + jobNumber + " ERROR => " + e.getMessage();
			} catch (JSchException e){
//				return "Could not cancel the job:" + jobNumber + " ERROR => " + e.getMessage();
				dpc.setMessage("Could not cancel the job:" + jobNumber + " ERROR => " + e.getMessage());
				return dpc;
			}
		} else {
			dpc.setMessage("Private Key file for user:  " + name + " is empty");
			return dpc;
//			return "Private Key file for user:  " + name + " is empty";
		}
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.POST, produces = "application/zip")
	public @ResponseBody ResponseEntity<InputStreamResource> downloadPDFFile(@RequestParam("username") String name,
			@RequestParam(name = "passPhrase", defaultValue = "null") String passPhrase, 
			@RequestParam("jobName") String jobName, @RequestParam("workPath") String workPath,
			@RequestParam("ppkFile") MultipartFile file, @RequestParam("execEnv") String env)
	        throws IOException {

		try {
			UserDetails ud=new UserDetails();
			JobDetails jd=new JobDetails();
			FetchFile ff=new FetchFile();
			ud.setUserName(name);
			ud.setTargetPath(workPath);
			ud.setPassphrase(passPhrase);
			ud.setHostName(env);
			jd.setJobName(jobName);
			/*
			 * Write job file
			 *
			 * Write private key
			 */
			byte[] ppkBytes = file.getBytes();
			BufferedOutputStream ppkStream = 
					new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
			ppkStream.write(ppkBytes);
			ppkStream.close();

			ud.setKeyPath(file.getOriginalFilename());
			String outputZip=ff.fetch(jd, ud);

		File dwnldFile= 
				new File(outputZip);
	    return ResponseEntity
	            .ok()
	            .contentLength(dwnldFile.length())
	            .contentType(
	                    MediaType.parseMediaType("application/zip"))   //octet-stream
	            .header("Content-disposition", "attachment; filename="+ outputZip)
	            .body(new InputStreamResource(new FileInputStream(dwnldFile)));

	}
		catch(IOException e){
			System.out.println("Failed during download");
			e.printStackTrace();
			return null;
		}
		catch(JSchException e){
			System.out.println("Failed during jsch download");
			return null;
		}


}  
	

	@RequestMapping(value = "/fetchjob", method = RequestMethod.GET)
	public @ResponseBody List<JobData> fetchJobHistory(@RequestParam("username") String name)
	        throws IOException {

		try {
			System.out.println("USERNAME IS"+name);
			List <JobData> lst=jobDao.getByUser(name);
			return lst;
			//return res.getJobName();
			

	}
		catch(Exception e){
			System.out.println("Failed during jsch download");
			e.printStackTrace();
			return null;
		}


}  

		@Autowired
		  private UserJobDataDao userjobDao;
		@Autowired
		  private JobDataDao jobDao;
}
