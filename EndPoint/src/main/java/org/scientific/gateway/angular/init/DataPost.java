package org.scientific.gateway.angular.init;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;
public class DataPost implements Serializable {
	
		private String username;
	    private String name;
	    private String email;
	    private MultipartFile file;
	    
	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = "Demo";
	    }
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public MultipartFile getFile() {
			return file;
		}
		public void setFile(MultipartFile file) {
			this.file = file;
			System.out.println(file);
		}

}
