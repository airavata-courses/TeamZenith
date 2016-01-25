package org.airavata.teamzenith.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionHandler extends Exception{
	/**
	 * Main Exception handling class
	 */
	private static final long serialVersionUID = 1L;
	

	public ExceptionHandler(IOException e){
		super(e);
	}
	
	public ExceptionHandler(FileNotFoundException e){
		super(e);
	}

}
