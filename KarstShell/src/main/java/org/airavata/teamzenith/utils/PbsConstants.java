package org.airavata.teamzenith.utils;

import java.util.HashMap;
import java.util.Map;

public class PbsConstants {
	public final static String hashBang="#!/bin/bash";
	public final static String pbsPrefix="#PBS";
	public final static String compileCmd="mpicc";
	public final static String execFormat=".out";
	public final static String torqueCmd="qsub";
	public final static String mailScript="mailsend.sh";
	public final static String mailScriptDest="mailsenddest.sh";
	public final static String pbsFormat="<pbs_file_name>";
	public final static String chmod ="chmod 777";
	public final static String mailCommand="mailx";
	public final static String moduleList="module load intel \n module load openmpi/intel \n module load gromacs \n";
	public final static String gromacs="gro";
	public final static String custom="cust";
	public final static String mpirun="mpirun";

	public final static Map<String, String> statusMap;
    static
    {
    	statusMap = new HashMap<String, String>();
    	statusMap.put("Q", "Queued");
    	statusMap.put("C", "Complete");
    	statusMap.put("R", "Running");

    }
}