package org.airavata.teamzenith.drivers;

import java.io.IOException;
import java.util.Properties;

//import org.airavata.teamzenith.config.SSHPropertyHandler;
import org.airavata.teamzenith.exceptions.ExceptionHandler;
import org.airavata.teamzenith.ssh.SSHConnectionHandler;
import org.airavata.teamzenith.ssh.SshUtil;
import org.airavata.teamzenith.utils.PbsConstants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class FileManagementImpl implements FileManagement{
	
	private static final Logger LOGGER = LogManager.getLogger(FileManagementImpl.class);

	@Override
	public boolean putFile(Session session, String localFile, String remotePath) throws IOException, JSchException {
		// TODO Auto-generated method stub
		SshUtil ssh = new SshUtil();
		try
		{
		//	SSHPropertyHandler sph;
		//	sph = new SSHPropertyHandler();
			/* Transfer related files to remote machine */
			
			if (localFile == null || remotePath == null){
				LOGGER.error("FILE ERROR: file path given null in putFile");
				return false;
			}
				
				
			if (ssh.ScpTo(session, localFile, remotePath) != true) {
				LOGGER.error("Script file copy failed");
			}

			return true;
		}catch(IOException e){
			LOGGER.error("FILE ERROR: File not found in putFile");
			throw new IOException("FILE ERROR: File not found in putFile"+e.getMessage(),e);
		}catch(JSchException e){
			LOGGER.error("SSH ERROR: problem with SSH session object in putFile");
			throw new JSchException("SSH ERROR: problem with SSH session object in putFile"+e.getMessage(),e);
		}
		
	}

	@Override
	public boolean getFile(String localPath, String remoteFile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean compileFile(Session session, String language, String artifact,String path) throws IOException, JSchException {
		SshUtil ssh = new SshUtil();
		try {
		//	SSHPropertyHandler sph;
			Properties pr;
		//	sph = new SSHPropertyHandler();
			String destSource = new StringBuffer(path).append(artifact).toString();
			String compileCommand = PbsConstants.compileCmd + " " + destSource + " -o " + destSource + ".out";
			System.out.println("Compile command is " + compileCommand);
			String cmdOut=ssh.executeCommand(session, compileCommand);
			if (cmdOut.equals("")) {
				System.out.println("Input file compilation failed");
				return false;
			}

			return true;
	        } catch(IOException e){
	        	LOGGER.error("FILE ERROR: Source Code file not found");
	        	throw new IOException("FILE ERROR: Source Code file not found",e);
	        } catch(JSchException e){
	        	LOGGER.error("SSH ERROR: problem with session object");
	        	throw new IOException("SSH ERROR: problem with session object",e);
	        }
	}
}
