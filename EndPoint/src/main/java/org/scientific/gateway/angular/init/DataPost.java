package org.scientific.gateway.angular.init;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
public class DataPost implements Serializable {
	
	 private String username;
	    private String name;
	    private String email;
	     
	    
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

}
