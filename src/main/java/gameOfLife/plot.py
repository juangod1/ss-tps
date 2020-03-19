import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import curve_fit
from scipy.stats import linregress
import statistics

### PLOT INPUT VS OUTPUT 2D ###

# values = {0: [], 1: [], 2: [], 3: [], 4: [], 5: []}
#
# def exponential(x, a, b):
#     return a*np.exp(b*x)
#
# for i in range(240):
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

data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/radius3d{}".format(100), names=["Generation","Pattern radius"], header=None)
data = data[:46]
ax1 = data.plot(x="Generation", y="Pattern radius")
data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/radius3d{}".format(101), names=["Generation","Pattern radius"], header=None)
data = data[:46]
ax2 = data.plot(x="Generation", y="Pattern radius")
data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/radius3d{}".format(102), names=["Generation","Pattern radius"], header=None)
data = data[:46]
ax3 = data.plot(x="Generation", y="Pattern radius")
data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/radius3d{}".format(103), names=["Generation","Pattern radius"], header=None)
data = data[:46]
ax1 = data.plot(x="Generation", y="Pattern radius")
data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/radius3d{}".format(104), names=["Generation","Pattern radius"], header=None)
data = data[:46]
ax2 = data.plot(x="Generation", y="Pattern radius")

plt.show()

### PLOT INPUT VS OUTPUT 3D ###

# values = {0: [], 1: [], 2: [], 3: [], 4: []}
#
# for i in range(150):
#     data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/radius3d{}".format(i), names=["Generation","Pattern radius"], header=None)
#     data = data[:45]
#     # data.plot(x="Generation", y="Pattern radius")
#     slope, intercept, r_value, p_value, std_err = linregress(np.array(data["Generation"]), np.array(data["Pattern radius"]))
#     # plt.plot(data["Generation"], intercept + slope*data["Generation"], 'r', label='fitted line')
#     values[i%5].append(slope)
#
# a = [0,1,2,3,4]
# b = [statistics.mean(values[0]), statistics.mean(values[1]), statistics.mean(values[2]), statistics.mean(values[3]), statistics.mean(values[4])]
# c = [statistics.stdev(values[0]), statistics.stdev(values[1]), statistics.stdev(values[2]), statistics.stdev(values[3]), statistics.stdev(values[4])]
# plt.errorbar(a,b,yerr=c, linestyle="None", mfc='red', fmt='-o')
# plt.xlabel('N')
# plt.ylabel('Slope')
# plt.xticks([0,1,2,3,4])
# plt.show()