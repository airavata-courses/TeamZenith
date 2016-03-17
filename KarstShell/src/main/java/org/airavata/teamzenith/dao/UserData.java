package org.airavata.teamzenith.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "TZ_USER_DATA")
public class UserData {

	// The entity fields (private)  

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long UserId;

	@NotNull
	private String UserName;

	@NotNull
	private String Email;

	// Public methods

	public UserData() { }

	public UserData(long id) { 
		this.UserId = id;
	}

	public UserData(String name, String email) {
		this.Email = email;
		this.UserName = name;
	}

	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

}