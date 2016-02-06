#!/bin/bash 
#PBS -l nodes=1:ppn=16 
#correct this #PBS -l walltime=00:10:00 
# no need to specify a queue on Karst, except if you need the debug or interactive queue 
#load the mpi module 
#cd to working directory 
cd /N/dc2/scratch/atumohan/job-submission 
mpirun -n 16 ./a.out 
