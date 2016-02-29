package org.airavata.teamzenith.init;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.airavata.teamzenith.dao.JobDetails;
import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.webmethods.CancelJob;
import org.airavata.teamzenith.webmethods.MonitorJob;
import org.airavata.teamzenith.webmethods.SubmitJob;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.JSchException;
/*
 * This is the main controller class which provides the following three REST APIs
 * upload- Accepts user and job details in order to submit a job on Karst
 * monitor- Accepts user details and job ID which is used to fetch the status of the job
 * cancel - Accepts user details and job ID which is used to cancel the job
 */
@Controller
public class RestApiController {

	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public @ResponseBody String provideUploadInfo() {
		return "A Karst job can be submitted by POSTing to this URL.";
	}

	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("tpath") String tPath,
			@RequestParam("file") MultipartFile file, @RequestParam("user") String userName,
			@RequestParam("jobName") String jobName, @RequestParam("nodes") String nodes,
			@RequestParam("ppn") String ppn, @RequestParam("walltime") String wallTime, 
			@RequestParam("isCompile") String isComp, @RequestParam("emailId") String emailId, 
			@RequestParam("ppk") MultipartFile ppk, @RequestParam(name = "pass", defaultValue = "null") String pass){

		if (!file.isEmpty()) {
			try {
				UserDetails ud=new UserDetails();
				JobDetails jd=new JobDetails();
				SubmitJob sub=new SubmitJob();

				ud.setUserName(userName);
				ud.setEmail(emailId);
				ud.setTargetPath(tPath);
				ud.setPassphrase(pass);
				/*
				 * Write job file
				 */
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = 
						new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
				stream.write(bytes);
				stream.close();
				/*
				 * Write private key
				 */
				byte[] ppkBytes = ppk.getBytes();
				BufferedOutputStream ppkStream = 
						new BufferedOutputStream(new FileOutputStream(new File(ppk.getOriginalFilename())));
				ppkStream.write(ppkBytes);
				ppkStream.close();

				ud.setKeyPath(ppk.getOriginalFilename());

				jd.setJobFile(file.getOriginalFilename());
				jd.setNumNodes(Integer.parseInt(nodes));
				jd.setProcessorPerNode(Integer.parseInt(ppn));
				jd.setWallTime(wallTime);
				jd.setJobName(jobName);

				if(isComp.equals("Yes"))
					jd.setCompileReqd(true);
				else
					jd.setCompileReqd(false);

				if(sub.submit(jd, ud)){
					return "Job submitted successfully with job Id "+jd.getJobId();
				}
				return "Job submission failed";
				
			} catch (Exception e) {
				return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
			}
		} else {
			return "Upload of file" + file.getOriginalFilename() + " failed because the file was empty.";
		}
	}

	@RequestMapping(value="/monitor", method=RequestMethod.GET)
	public @ResponseBody String MonitorJobEndPointInfo() {
		return "A Karst job can be monitored by POSTing to this URL";
	}

	@RequestMapping(value="/monitor", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody DataPost MonitorJobEndPoint(@RequestParam("username") String name,
			@RequestParam(name = "passPhrase", defaultValue = "null") String passPhrase, 
			@RequestParam("size") String jobNumber, @RequestParam("file") MultipartFile file){
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
				return dp;
			} catch (JSchException e){
//				return "Could not get the status for job:" + jobNumber + " ERROR => " + e.getMessage();
				return dp;
			}
		} else {
//			return "Private Key file for user:  " + name + " is empty";
			return dp;
		}
	}
	
	@RequestMapping(value="/cancel", method=RequestMethod.GET)
	public @ResponseBody String CancelJobEndPointInfo() {
		return "A Karst job can be cancelled by POSTing to this URL";
	}

	@RequestMapping(value="/cancel", method=RequestMethod.POST)
	public @ResponseBody String CancelJobEndPoint(@RequestParam("username") String name,
			@RequestParam(name = "passPhrase", defaultValue = "null") String passPhrase, 
			@RequestParam("jobNumber") String jobNumber, @RequestParam("ppkFile") MultipartFile file){
		UserDetails userObject = new UserDetails();
		CancelJob job = new CancelJob();

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

				job.getCancelJob(userObject, jobNumber);
				return "Job:" + jobNumber + " Cancelled successfully";

			} catch (IOException e) {
				return "Could not cancel the job:" + jobNumber + " ERROR => " + e.getMessage();
			} catch (JSchException e){
				return "Could not cancel the job:" + jobNumber + " ERROR => " + e.getMessage();
			}
		} else {
			return "Private Key file for user:  " + name + " is empty";
		}
	}


}
