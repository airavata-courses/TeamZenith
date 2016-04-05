package org.airavata.teamzenith.utils;

import java.util.HashMap;
import java.util.Map;

public class PbsConstants {
	public final static String hashBang="#!/bin/bash";
	public final static String pbsPrefix="#PBS";
	public final static String compileCmd="mpicc";
	public final static String bigRedCompileCmd="cc";
	public final static String execFormat=".out";
	public final static String torqueCmd="qsub";
	public final static String mailScript="mailsend.sh";
	public final static String mailScriptDest="mailsenddest.sh";
	public final static String pbsFormat="<pbs_file_name>";
	public final static String chmod ="chmod 777";
	public final static String mailCommand="mailx";
	public final static String moduleList="module load gromacs \nmodule load fftw\n";
	public final static String gromacs="gro";
	public final static String custom="cust";
	public final static String mpirun="mpirun";
	public final static String gromacsGmx="echo -e \"1\\n1\"|pdb2gmx -f pdb1y6l.ent -o pdb1y6l.gro -p pdb1y6l.top -ignh \n";
	public final static String gromacsGrompp="grompp -v -f minim.mdp -c pdb1y6l.gro -p pdb1y6l.top -o pdb1y6l-EM-vacuum.tpr \n";
	public final static String gromacsMdrun="mdrun -v -deffnm pdb1y6l-EM-vacuum.tpr -c pdb1y6l-EM-vacuum.gro \n";
	public final static String bigRedJobRun="aprun -n 16 ";
	
	public final static Map<String, String> statusMap;
    static
    {
    	statusMap = new HashMap<String, String>();
    	statusMap.put("Q", "Queued");
    	statusMap.put("C", "Complete");
    	statusMap.put("R", "Running");

    }
}