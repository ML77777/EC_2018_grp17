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
seeds_line = "Function,Parameter name,Parameter type,Parameter tune value,Seed,score\n"
seeds_results.write(seeds_line)


for line in sys.stdin.readlines():
    if ("Score" in line):
        splittedline = line.split(":")
        score = splittedline[1]
        score = score.strip().strip("\n")
        score = float(score)
        all_sum += score
        #print("Score of seed {} : {}".format(seed,score))
        seeds_line = function_name + ","+ str(parameter_name) + "," + str(parameter_type) + "," + str(parameter_value)+ "," + str(seed) + ","+ str(score) + "\n"
        seeds_results.write(seeds_line)
        seed += 1
        #print("Type:"+str(type(score)))
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
