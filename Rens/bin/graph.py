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

plotData = [float(num) for num in x[:-2]]

plt.plot(plotData)
plt.yticks(np.arange(min(plotData),10,0.5))
plt.show()