#!/bin/bash 
#PBS -l nodes=2:ppn=16,walltime=30:00 
cd /N/u/anujbhan/Karst
mpirun -np 32 -machinefile $PBS_NODEFILE /N/u/anujbhan/Karst/bin.out
