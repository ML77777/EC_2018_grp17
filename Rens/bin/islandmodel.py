# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

import matplotlib.pyplot as plt
import sys
import numpy as np
x=list()
#with open('output.txt', 'r') as f:
#    x = f.readlines()


for line in sys.stdin.readlines():
    x.append(line)

plotData = []
plotData.append([])
maxFit = 0
for i in range(0,(len(x)-2)):
    
    splittedLine = x[i].split("|")
    if len(plotData) > int(splittedLine[1]) :
       
        plotData[int(splittedLine[1])].append(float(splittedLine[0]))
        if maxFit < float(splittedLine[0]):
            maxFit = float(splittedLine[0])
    else:
       
        plotData.append([])
        plotData[int(splittedLine[1])].append(float(splittedLine[0]))
        if maxFit < float(splittedLine[0]):
            maxFit = float(splittedLine[0])
        
#plotData = [float(num) for num in x[:-2]]

for data in plotData:
    
    plt.plot(data)

plt.title("Maximum fitness achieved: "+str(maxFit))
plt.yticks(np.arange(min(plotData[0]),10.5,0.5))
plt.show()