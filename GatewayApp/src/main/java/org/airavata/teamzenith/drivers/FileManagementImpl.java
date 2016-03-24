package org.airavata.teamzenith.drivers;

import java.io.IOException;

import org.airavata.teamzenith.ssh.SshUtil;
import org.airavata.teamzenith.utils.PbsConstants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class FileManagementImpl implements FileManagement{

	private static final Logger LOGGER = LogManager.getLogger(FileManagementImpl.class);

	public boolean putFile(Session session, String localFile, String remotePath) throws IOException, JSchException {
		SshUtil ssh = new SshUtil();
		try
		{
			/* Transfer related files to remote machine */

			if (localFile == null || remotePath == null){
				LOGGER.error("FILE ERROR: File path is null");
				return false;
			}

			if (ssh.ScpTo(session, localFile, remotePath) != true) {
				LOGGER.error("Script file copy failed");
			}

			return true;
		}catch(IOException e){
			LOGGER.error("FILE ERROR: File not found in putFile");
			throw new IOException("FILE ERROR: File not found in putFile",e);
		}catch(JSchException e){
			LOGGER.error("SSH ERROR: problem with SSH session object in putFile");
			throw new JSchException("SSH ERROR: problem with SSH session object in putFile",e);
		}

	}

	public boolean getFile(Session session, String localPath, String remoteFile) throws IOException, JSchException {
		SshUtil ssh = new SshUtil();
		try
		{
			/* Transfer related files to remote machine */

			if (localPath == null || remoteFile == null){
				LOGGER.error("FILE ERROR: File path is null");
				return false;
			}

			if (ssh.ScpFrom(session, remoteFile,localPath) != true) {
				LOGGER.error("Script file copy failed");
			}
			LOGGER.error("File download successful");

			return true;
		}catch(IOException e){
			LOGGER.error("FILE ERROR: File not found in putFile");
			e.printStackTrace();
			throw new IOException("FILE ERROR: File not found in putFile",e);
		}catch(JSchException e){
			LOGGER.error("SSH ERROR: problem with SSH session object in putFile");
			throw new JSchException("SSH ERROR: problem with SSH session object in putFile",e);
		}		
	}

	public boolean compileFile(Session session, String language, String artifact,String path) throws IOException, JSchException {
		SshUtil ssh = new SshUtil();
		try {
			String destSource = new StringBuffer(path).append(artifact).toString();
			String compileCommand = new StringBuffer(PbsConstants.compileCmd).append(" ").append(destSource)
				.append(" -o ").append(destSource).append(".out").toString();
			if(LOGGER.isInfoEnabled())
				LOGGER.info("Compile command is " + compileCommand);
			
			String cmdOut=ssh.executeCommand(session, compileCommand);
			
			if (cmdOut.equals("")) {
				return true;
			}
			return false;
		} catch(IOException e){
			LOGGER.error("FILE ERROR: Source Code file not found");
			throw new IOException("FILE ERROR: Source Code file not found",e);
		} catch(JSchException e){
			LOGGER.error("SSH ERROR: problem with session object");
			throw new IOException("SSH ERROR: problem with session object",e);
		}
	}
}
