package org.airavata.teamzenith.drivers;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.airavata.teamzenith.dao.UserDetails;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;


public class DataManagementImpl implements DataManagement{
	private static final Logger LOGGER = LogManager.getLogger(DataManagementImpl.class);

	private PreparedStatement statement = null;
	private ResultSet userResult = null;

	public Connection initializeConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");

		Connection connect = DriverManager
				.getConnection("jdbc:mysql://" + host + "/gateway?"
						+ "user=" + user + "&password=" + passwd );

		return connect;
	}
	
	@Override
	   public void persistUser(HashMap<String,String> userInput) throws SQLException, ClassNotFoundException{
		String uniqueID = UUID.randomUUID().toString();
		DataManagementImpl dmi=new DataManagementImpl();
		UserDetails ud=new UserDetails();
		Connection c=null;
		try{
		c=dmi.initializeConnection();
		String query= "INSERT INTO zenith_user_session(user_id,user_name,directory_path) values "
				+ "(?,?,?)";
		statement=c.prepareStatement(query);
		statement.setString(1, uniqueID);
		statement.setString(2, userInput.get("UserName"));
		statement.setString(3, userInput.get("Directory"));
		statement.executeUpdate();
		LOGGER.info("User record added to database");
		}
		catch(SQLException e){
			LOGGER.error("SQL Exception during query execution");
			dmi.close();
			throw new SQLException("SQL Exception during query execution",e);
		}catch(ClassNotFoundException e){
			LOGGER.error("ERROR Driver class not found");
			dmi.close();
			throw new ClassNotFoundException("ERROR Driver class not found",e);
		}
		finally{
			dmi.close();
		}

	}
	@Override
	public UserDetails fetchUserData(String userName) throws ClassNotFoundException, SQLException{
		DataManagementImpl dmi=new DataManagementImpl();
		UserDetails ud=new UserDetails();
		Connection c=null;
		try{
		c=dmi.initializeConnection();
		String query= "SELECT user_id,user_name,directory_path FROM zenith_user_session where user_name= ?";
		statement=c.prepareStatement(query);
		statement.setString(1, userName);
		userResult=statement.executeQuery();
		if(userResult.next()){
			ud.setUserName(userResult.getString(2));
			ud.setEmail(userResult.getString(3));
			ud.setKeyPath(userResult.getString(4));
		}
		}
		catch(SQLException e){
			LOGGER.error("SQL Exception during query execution");
			dmi.close();
			throw new SQLException("SQL Exception during query execution",e);
		}catch(ClassNotFoundException e){
			LOGGER.error("ERROR Driver class not found");
			dmi.close();
			throw new ClassNotFoundException("ERROR Driver class not found",e);
		}
		finally{
			dmi.close();
		}
		return ud;

		}
	
	
	// You need to close the resultSet
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (SQLException e) {

		}
	}


}
