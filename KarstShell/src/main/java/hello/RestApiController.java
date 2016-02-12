package hello;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

@Controller
public class RestApiController {

	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public @ResponseBody String provideUploadInfo() {
		return "You can upload a file by posting to this same URL.";
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
				sub.submit(jd, ud);

				return "Job submitted successfully with job Id "+jd.getJobId();
				//return "You successfully uploaded " + file.getOriginalFilename() + "to "+ System.getProperty("user.dir");
			} catch (Exception e) {
				return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
		}
	}
	
	@RequestMapping(value="/monitor", method=RequestMethod.GET)
    public @ResponseBody String MonitorJobEndPointInfo() {
        return "Use this link to get Job status, params: @username, @passPhrase, @jobNumber, @ppkFile";
    }
    
    @RequestMapping(value="/monitor", method=RequestMethod.POST)
    public @ResponseBody String MonitorJobEndPoint(@RequestParam("username") String name,
    		@RequestParam(name = "passPhrase", defaultValue = "null") String passPhrase, 
    		@RequestParam("jobNumber") String jobNumber, @RequestParam("ppkFile") MultipartFile file){
    	UserDetails userObject = new UserDetails();
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
                return new StringBuffer("the job status is").append(job.getJobStatus(userObject, jobNumber)).toString()	;
                
                //return "You successfully uploaded " + name + "!";
            } catch (IOException e) {
                return "Could not get the status for job:" + jobNumber + " ERROR => " + e.getMessage();
            } catch (JSchException e){
            	return "Could not get the status for job:" + jobNumber + " ERROR => " + e.getMessage();
            }
        } else {
            return "Private Key file for user:  " + name + " is empty";
        }
    }
	@RequestMapping(value="/cancel", method=RequestMethod.GET)
    public @ResponseBody String CancelJobEndPointInfo() {
        return "Use this link to cancel Job, params: @username, @passPhrase, @jobNumber, @ppkFile";
    }
    
    @RequestMapping(value="/cancel", method=RequestMethod.POST)
    public @ResponseBody String CancelJobEndPoint(@RequestParam("username") String name,@RequestParam(name = "passPhrase", defaultValue = "null") String passPhrase, @RequestParam("jobNumber") String jobNumber, @RequestParam("ppkFile") MultipartFile file){
    	UserDetails userObject = new UserDetails();
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
                
                CancelJob job = new CancelJob();
                job.getCancelJob(userObject, jobNumber);
                return "job:" + jobNumber + " Cancelled successfully";
                //return "You successfully uploaded " + name + "!";
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
