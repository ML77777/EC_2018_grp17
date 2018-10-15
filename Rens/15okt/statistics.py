# -*- coding: utf-8 -*-
"""
Created on Sun Oct 14 13:51:15 2018

@author: Rens
"""

import matplotlib.pyplot as plt
import sys
import os
import numpy as np
x=list()



#with open('output.txt', 'r') as f:
#    x = f.readlines()
#print(sys.argv)

if (len(sys.argv) < 2):
    print ("Usage: python %s <function B/S/K required> <Parameter value> <Parameter name> <Parameter type>" % (sys.argv[0]))
    sys.exit(1)
function_name = sys.argv[1]
if "B" in function_name:
    function_name = "BentCigar"
elif "S" in  function_name:
    function_name = "SchaffersEvaluation"
elif "K" in function_name:
    function_name = "KatsuuraEvaluation"

#Optional in  case of testing the usage
parameter_name = ""
parameter_type = ""
parameter_value = -1
if (len(sys.argv) > 2):
   parameter_value = sys.argv[2]
   parameter_value = sys.argv[2]
if (len(sys.argv) > 2):
   parameter_name = sys.argv[3]
   parameter_type = sys.argv[4]
#   print("Parameter name: {}".format(parameter_name))
#   print("Parameter version: {}".format(parameter_type))
#print("Parameter value: \t {}".format(parameter_value))
#print("Function name {}".format(function_name))

all_sum = 0.0
seed = 1;

#Store every seed result into all_seeds.csv file, Seperated with the column names
seeds_results = open('all_seeds.csv','a')
#if(not os.path.isfile("./all_seeds.csv")):
seeds_line = "Function,Parameter name,Parameter type,Parameter tune value,Seed,score,scoreTill1,scoreTill2,scoreTill3,scoreTill4,scoreTill5,scoreTill6,scoreTill7,scoreTill8,scoreTill9,scoreTill10\n"
seeds_results.write(seeds_line)



fitnessList = []
rankingList = [[] for i in range(10)]
runNumber = 0
def getStatistics(fitnessList):
    found10 = False
    found9 = False
    found8 = False
    found7 = False
    found6 = False
    found5 = False
    found4 = False
    found3 = False
    found2 = False
    found1 = False 
    done = False
    i = 0
    
    while not done:
      if fitnessList[i] >= 10  and not found10:
          found10 = True
          rankingList[9].append(i)
          done = True  
      if fitnessList[i] >= 9  and not found9:
          found9 = True
          rankingList[8].append(i)
      if fitnessList[i] >= 8 and not found8:
          found8 = True
          rankingList[7].append(i)
          
      if fitnessList[i] >= 7 and not found7:
          found7 = True
          rankingList[6].append(i)
      if fitnessList[i] >= 6 and not found6:
          found6 = True
          rankingList[5].append(i)
      if fitnessList[i] >= 5 and not found5:
          found5 = True
          rankingList[4].append(i)
      if fitnessList[i] >= 4 and not found4:
          found4 = True
          rankingList[3].append(i)
      if fitnessList[i] >= 3 and not found3:
          found3 = True
          rankingList[2].append(i)
      if fitnessList[i] >= 2 and not found2:
          found2 = True
          
          rankingList[1].append(i)
      if fitnessList[i] >= 1 and not found1:
          found1 = True
          rankingList[0].append(i)   
          
      if (i == len(fitnessList) - 1):
            done  = True
            if not found1:
                rankingList[0].append(-1)
            if not found2:
                rankingList[1].append(-1)
            if not found3:
                rankingList[2].append(-1)
            if not found4:
                rankingList[3].append(-1)
            if not found5:
                rankingList[4].append(-1)
            if not found6:
                rankingList[5].append(-1)
            if not found7:
                rankingList[6].append(-1)
            if not found8:
                rankingList[7].append(-1)
            if not found9:
                rankingList[8].append(-1)
            if not found10:
                rankingList[9].append(-1)

      i += 1

    
for line in sys.stdin.readlines():
    if ("Current Fitness" in line):
        splittedline = line.split(":")
        parameter_value = splittedline[1]
        parameter_value = parameter_value.strip().strip("\n")
        fitnessList.append(float(parameter_value))
       
    if ("Variable" in line):
        splittedline = line.split(":")
        parameter_value = splittedline[1]
        parameter_value = parameter_value.strip().strip("\n")
        parameter_value = float(parameter_value) + 1
        seed = 1
        rankingList = [[] for i in range(10)]
    if ("Score" in line):
        splittedline = line.split(":")
        score = splittedline[1]
        score = score.strip().strip("\n")
        score = float(score)
        all_sum += score
        #print("Score of seed {} : {}".format(seed,score))
        getStatistics(fitnessList)
        seeds_line = function_name + ","+ str(parameter_name) + "," + str(parameter_type) + "," + str(parameter_value)+ "," + str(seed) + ","+ str(score) + ","+ str(rankingList[0][runNumber]) + ","+ str(rankingList[1][runNumber]) + ","+ str(rankingList[2][runNumber]) + ","+ str(rankingList[3][runNumber]) + ","+ str(rankingList[4][runNumber]) + ","+ str(rankingList[5][runNumber]) + ","+ str(rankingList[6][runNumber]) + ","+ str(rankingList[7][runNumber]) + ","+ str(rankingList[8][runNumber]) + ","+ str(rankingList[9][runNumber]) + "\n"
        seeds_results.write(seeds_line)
        seed += 1
        runNumber += 1
        #print("Type:"+str(type(score)))
        fitnessList = []
        
        
        x.append(score)

mean = all_sum/len(x)
#print("Length {}".format(len(x)))
#print("Sum: {}".format(all_sum))
#print("Mean: {}".format(mean))

if(not os.path.isfile("./only_means.csv")):
    means_results = open('only_means.csv','a')
    means_line = "Function,Parameter name,Parameter type,Parameter tune value,Amount seeds,Mean score\n"
    means_results.write(means_line)
else: means_results = open('only_means.csv','a')
means_line = function_name + ","+ str(parameter_name) + "," + str(parameter_type) + "," + str(parameter_value)+ "," + str(seed-1) + ","+ str(mean) + "\n"
means_results.write(means_line)



"""
"""