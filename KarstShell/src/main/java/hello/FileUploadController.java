package hello;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.airavata.teamzenith.dao.JobDetails;
import org.airavata.teamzenith.dao.UserDetails;
import org.airavata.teamzenith.webmethods.SubmitJob;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

//	@RequestMapping(value="/upload", method=RequestMethod.GET)
//	public @ResponseBody String provideUploadInfo() {
//		return "You can upload a file by posting to this same URL.";
//	}
//
//	@RequestMapping(value="/upload", method=RequestMethod.POST)
//	public @ResponseBody String handleFileUpload(@RequestParam("tpath") String tPath,
//			@RequestParam("file") MultipartFile file, @RequestParam("user") String userName,
//			@RequestParam("jobName") String jobName, @RequestParam("nodes") String nodes,
//			@RequestParam("ppn") String ppn, @RequestParam("walltime") String wallTime, 
//			@RequestParam("emailId") String emailId, @RequestParam("ppk") MultipartFile ppk){
//		if (!file.isEmpty()) {
//			try {
//				UserDetails ud=new UserDetails();
//				JobDetails jd=new JobDetails();
//				SubmitJob sub=new SubmitJob();
//
//				ud.setUserName(userName);
//				ud.setEmail(emailId);
//				ud.setTargetPath(tPath);
//				/*
//				 * Write job file
//				 */
//				byte[] bytes = file.getBytes();
//				BufferedOutputStream stream = 
//						new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
//				stream.write(bytes);
//				stream.close();
//				/*
//				 * Write private key
//				 */
//				byte[] ppkBytes = ppk.getBytes();
//				BufferedOutputStream ppkStream = 
//						new BufferedOutputStream(new FileOutputStream(new File(ppk.getOriginalFilename())));
//				ppkStream.write(ppkBytes);
//				ppkStream.close();
//				
//				ud.setKeyPath(ppk.getOriginalFilename());
//				jd.setJobFile(file.getOriginalFilename());
//				jd.setNumNodes(Integer.parseInt(nodes));
//				jd.setProcessorPerNode(Integer.parseInt(ppn));
//				jd.setWallTime(wallTime);
//				jd.setJobName(jobName);
//				sub.submit(jd, ud);
//
//				return "Job submitted successfully";
//				//return "You successfully uploaded " + file.getOriginalFilename() + "to "+ System.getProperty("user.dir");
//			} catch (Exception e) {
//				return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
//			}
//		} else {
//			return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
//		}
//	}

}
