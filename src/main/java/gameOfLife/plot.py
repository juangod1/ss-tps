import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import curve_fit
import statistics

### PLOT INPUT VS OUTPUT 2D ###

# values = {0: [], 1: [], 2: [], 3: [], 4: [], 5: []}
#
# def exponential(x, a, b):
#     return a*np.exp(b*x)
#
# for i in range(239):
#     data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table2d{}".format(i), names=["Generation","Cells Alive"], header=None)
#     param, param_cov = curve_fit(exponential, np.array(data["Generation"]), np.array(data["Cells Alive"]),[71.65,0.07835])
#     values[i%6].append(param[1])
#
# a = [0,1,2]
# b = [statistics.mean(values[0]), statistics.mean(values[1]), statistics.mean(values[2])]
# c = [statistics.stdev(values[0]), statistics.stdev(values[1]), statistics.stdev(values[2])]
# plt.errorbar(a,b,yerr=c, linestyle="None", mfc='red', fmt='-o')
# plt.xlabel('N')
# plt.ylabel('b')
# plt.xticks([0,1,2])
# plt.show()

### PLOT EXAMPLES OF BOARDS 2D ###

# data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table2d{}".format(180), names=["Generation","Cells Alive"], header=None)
# ax1 = data.plot(x="Generation", y="Cells Alive")
# data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table2d{}".format(181), names=["Generation","Cells Alive"], header=None)
# ax2 = data.plot(x="Generation", y="Cells Alive")
# data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table2d{}".format(182), names=["Generation","Cells Alive"], header=None)
# ax3 = data.plot(x="Generation", y="Cells Alive")
# data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table2d{}".format(183), names=["Generation","Cells Alive"], header=None)
# ax1 = data.plot(x="Generation", y="Cells Alive")
# data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table2d{}".format(184), names=["Generation","Cells Alive"], header=None)
# ax2 = data.plot(x="Generation", y="Cells Alive")
# data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table2d{}".format(185), names=["Generation","Cells Alive"], header=None)
# ax3 = data.plot(x="Generation", y="Cells Alive")
#
# plt.show()

### PLOT RADIUS EVOLUTION ###