package org.airavata.teamzenith.drivers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.airavata.teamzenith.dao.UserDetails;

public interface DataManagement {
	 Connection connect = null;
	   PreparedStatement preparedStatement = null;
	   ResultSet resultSet = null;
	   String host = "localhost";
	   String user = "root";
	   String passwd = "root";
	   public UserDetails fetchUserData(String userName) throws SQLException,ClassNotFoundException;;
	   public void close();
	   public void persistUser(HashMap<String,String> userInput) throws SQLException,ClassNotFoundException;

}
