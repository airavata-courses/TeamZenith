package org.airavata.teamzenith.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TestRun {

	public static void main(String[] args) throws IOException {
		
		Manager m =new Manager();
		// TODO Auto-generated method stub
		m.transferFile("public.ppk");
	}
//	public static void main(String[] args) throws URISyntaxException, IOException {
//	    File file = new File(getResourcePath() + "/abc.txt");
//	    FileOutputStream fos=new FileOutputStream(file);
//	    fos.write("hello".getBytes());
//System.out.println(file.lastModified());
//
//}
//	private static String getResourcePath() {
//	    try {	
//	    	
//	        URI resourcePathFile = System.class.getResource("/RESOURCE_PATH").toURI();
//	        Map<String, String> env = new HashMap<String,String>(); 
//	    	env.put("create", "true");
//	    	FileSystem zipfs = FileSystems.newFileSystem(resourcePathFile, env);
//	        System.out.println(resourcePathFile.toString());
//	        String resourcePath = Files.readAllLines(Paths.get(resourcePathFile),Charset.defaultCharset()).get(0);
//	        URI rootURI = new File("").toURI();
//	        URI resourceURI = new File(resourcePath).toURI();
//	        URI relativeResourceURI = rootURI.relativize(resourceURI);
//	        return relativeResourceURI.getPath();
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    	return null;
//	    }
//	}
}


