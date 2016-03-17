package org.airavata.teamzenith.ssh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshUtil {
	private static final Logger LOGGER = LogManager.getLogger(SshUtil.class);

	public boolean ScpTo(Session session, String source, String dest) throws JSchException, IOException {

		FileInputStream fis=null;
		boolean ptimestamp = true;
		try{
			String command="scp " + (ptimestamp ? "-p" :"") +" -t "+dest;
			Channel channel=session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out=channel.getOutputStream();
			InputStream in=channel.getInputStream();

			channel.connect();

			if(checkAck(in)!=0){
				System.exit(0);
			}

			File _lfile = new File(source);

			if(ptimestamp){
				command="T"+(_lfile.lastModified()/1000)+" 0";
				// The access time should be sent here,
				// but it is not accessible with JavaAPI ;-<
				command+=(" "+(_lfile.lastModified()/1000)+" 0\n"); 
				out.write(command.getBytes()); out.flush();
				if(checkAck(in)!=0){
					System.exit(0);
				}
			}

			// send "C0644 filesize filename", where filename should not include '/'
			long filesize=_lfile.length();
			command="C0644 "+filesize+" ";
			if(source.lastIndexOf('/')>0){
				command+=source.substring(source.lastIndexOf('/')+1);
			}
			else{
				command+=source;
			}
			command+="\n";
			out.write(command.getBytes()); out.flush();
			if(checkAck(in)!=0){
				System.exit(0);
			}

			// send a content of lfile
			fis=new FileInputStream(source);
			byte[] buf=new byte[1024];
			while(true){
				int len=fis.read(buf, 0, buf.length);
				if(len<=0) break;
				out.write(buf, 0, len); //out.flush();
			}
			fis.close();
			fis=null;
			// send '\0'
			buf[0]=0; out.write(buf, 0, 1); out.flush();
			if(checkAck(in)!=0){
				System.exit(0);
			}
			out.close();

			channel.disconnect();

			System.out.println("Scp of file " + source+"successful !");
			LOGGER.info("Scp of file " + source+" successful !");

			return true;
		}
		catch(JSchException e){
			throw new JSchException("SSH ERROR : problem with ssh connection found in ScpTO"+e.getMessage(), e);
		}
		catch(IOException e){
			throw new IOException("FILE ERROR : cannot access source file in ScpTo");
		}
	}
	
	public boolean ScpFrom(Session session, String rfile, String lfile) throws JSchException, IOException {
		   
	    FileOutputStream fos=null;
	    try{

	      String prefix=null;
	      if(new File(lfile).isDirectory()){
	        prefix=lfile+File.separator;
	      }
	      LOGGER.info("prefix is"+prefix);
	      // exec 'scp -f rfile' remotely
	      String command="scp -f "+rfile;
	      Channel channel=session.openChannel("exec");
	      ((ChannelExec)channel).setCommand(command);

	      // get I/O streams for remote scp
	      OutputStream out=channel.getOutputStream();
	      InputStream in=channel.getInputStream();

	      channel.connect();

	      byte[] buf=new byte[1024];

	      // send '\0'
	      buf[0]=0; out.write(buf, 0, 1); out.flush();
	      String strg=new String(buf);
	      LOGGER.info("Buffer is"+strg);
	      while(true){
		int c=checkAck(in);
	        if(c!='C'){
		  break;
		}

	        // read '0644 '
	        in.read(buf, 0, 5);

	        long filesize=0L;
	        while(true){
	          if(in.read(buf, 0, 1)<0){
	            // error
	            break; 
	          }
	          if(buf[0]==' ')break;
	          filesize=filesize*10L+(long)(buf[0]-'0');
	        }

	        String file=null;
	        for(int i=0;;i++){
	          in.read(buf, i, 1);
	          if(buf[i]==(byte)0x0a){
	            file=new String(buf, 0, i);
	            break;
	  	  }
	        }
	        LOGGER.info("File is"+file);

		//System.out.println("filesize="+filesize+", file="+file);

	        // send '\0'
	        buf[0]=0; out.write(buf, 0, 1); out.flush();

	        // read a content of lfile
	        fos=new FileOutputStream(prefix==null ? lfile : prefix+file);
	        int foo;
	        while(true){
	          if(buf.length<filesize) foo=buf.length;
		  else foo=(int)filesize;
	          foo=in.read(buf, 0, foo);
	          if(foo<0){
	            // error 
	            break;
	          }
	          fos.write(buf, 0, foo);
	          filesize-=foo;
	          if(filesize==0L) break;
	        }
	        fos.close();
	        fos=null;

		if(checkAck(in)!=0){
		  System.exit(0);
		}

	        // send '\0'
	        buf[0]=0; out.write(buf, 0, 1); out.flush();
	      }

	      session.disconnect();

	      return true;	    }
	    catch(Exception e){
	    	LOGGER.error("error while downloading file");
	    	e.printStackTrace();
	      System.out.println(e);
	      try{if(fos!=null)fos.close();}catch(Exception ee){}
	    }
		return true;
	  }
	static int checkAck(InputStream in) throws IOException{
		int b=in.read();
		// b may be 0 for success,
		//          1 for error,
		//          2 for fatal error,
		//          -1
		if(b==0) return b;
		if(b==-1) return b;

		if(b==1 || b==2){
			StringBuffer sb=new StringBuffer();
			int c;
			do {
				c=in.read();
				sb.append((char)c);
			}
			while(c!='\n');
			if(b==1){ // error
				System.out.print(sb.toString());
			}
			if(b==2){ // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}
	public String executeCommand(Session session, String command) throws JSchException, IOException {

		try{

			Channel channel=session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);

			// X Forwarding
			// channel.setXForwarding(true);

			//channel.setInputStream(System.in);
			channel.setInputStream(null);

			//channel.setOutputStream(System.out);

			//FileOutputStream fos=new FileOutputStream("/tmp/stderr");
			//((ChannelExec)channel).setErrStream(fos);
			((ChannelExec)channel).setErrStream(System.err);

			InputStream in=channel.getInputStream();

			channel.connect();

			byte[] tmp=new byte[1024];
			String cmdOutput="";
			while(true){
				while(in.available()>0){
					int i=in.read(tmp, 0, 1024);
					if(i<0)break;
					String temp=new String(tmp, 0, i);
					cmdOutput=new StringBuffer().append(cmdOutput).append(temp).toString();
					//System.out.print(new String(tmp, 0, i));
				}
				if(channel.isClosed()){
					if(in.available()>0) continue;
					System.out.println("exit-status: "+ channel.getExitStatus());
					break;
				}
				try{Thread.sleep(1000);}catch(Exception ee){}
			}
			channel.disconnect();
			LOGGER.info(command +": Executed successfully !!!");
			return cmdOutput;
			//return true;
		}
		catch(JSchException e){
			LOGGER.error("SSH ERROR: SSH session invalid in execute command");
			throw new JSchException("SSH ERROR : problem with ssh connection found in Execute command", e);
		}
		catch(IOException e){
			LOGGER.error("Stream ERROR : input stream error in Execute Command");
			throw new IOException("Stream ERROR : input stream error in Execute Command");
		}
	}

}