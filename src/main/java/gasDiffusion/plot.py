import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import curve_fit
from scipy.stats import linregress
import statistics

### PLOT FP VS T - A VARIATION ###

# data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cerouno/table{}".format(12), names=["Time", "FP", "Pressure","Temp"], header=None)
# plt.plot(data['Time'], data['FP'], label="A=0.01")
# data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cerotres/table{}".format(0), names=["Time", "FP", "Pressure","Temp"], header=None)
# plt.plot(data['Time'], data['FP'], label="A=0.03")
# data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cerocinco/table{}".format(6), names=["Time", "FP", "Pressure","Temp"], header=None)
# plt.plot(data['Time'], data['FP'], label="A=0.05")
# plt.xlabel("Tiempo [s]")
# plt.ylabel("Fracción de partículas en recinto izquierdo")
# plt.legend(loc='best')
# plt.grid()
# plt.show()

### PLOT INPUT VS OUTPUT T of FP ~ 0.5 VS N ###

# values = {0.01: [], 0.02: [], 0.03: [], 0.04: [], 0.05: [], 0.06: []}
#
# def exponential(x, a, b):
#     return a*np.exp(b*x)
#
# for i in range(20):
#     data01 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cerouno/table{}".format(i), names=["Time", "FP", "Pressure","Temp"], header=None)
#     values[0.01].append(data01['Time'].max())
#     data02 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cerodos/table{}".format(i), names=["Time", "FP", "Pressure","Temp"], header=None)
#     values[0.02].append(data02['Time'].max())
#     data03 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cerotres/table{}".format(i), names=["Time", "FP", "Pressure","Temp"], header=None)
#     values[0.03].append(data03['Time'].max())
#     data04 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cerocuatro/table{}".format(i), names=["Time", "FP", "Pressure","Temp"], header=None)
#     values[0.04].append(data04['Time'].max())
#     data05 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cerocinco/table{}".format(i), names=["Time", "FP", "Pressure","Temp"], header=None)
#     values[0.05].append(data05['Time'].max())
#     data06 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/ceroseis/table{}".format(i), names=["Time", "FP", "Pressure","Temp"], header=None)
#     values[0.06].append(data06['Time'].max())
#
# a = [0.01,0.02,0.03,0.04,0.05,0.06]
# b = [statistics.mean(values[0.01]), statistics.mean(values[0.02]), statistics.mean(values[0.03]), statistics.mean(values[0.04]), statistics.mean(values[0.05]), statistics.mean(values[0.06])]
# c = [statistics.stdev(values[0.01]), statistics.stdev(values[0.02]), statistics.stdev(values[0.03]), statistics.stdev(values[0.04]), statistics.stdev(values[0.05]), statistics.stdev(values[0.06])]
# plt.errorbar(a,b,yerr=c, linestyle="None", mfc='red', fmt='-o')
# plt.xlabel('Tamaño de la apertura [m]')
# plt.ylabel('Tiempo estacionario [s]')
# plt.xticks([0.01,0.02,0.03,0.04,0.05,0.06])
# plt.grid()
# plt.show()

### PLOT P VS T ###

values = {0.01: [], 0.05: [], 0.1: [], 0.15: [], 0.2: [], 0.25: []}

for i in range(20):
    data01 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/velocities/cerouno/table{}".format(i), names=["Temp", "Pressure"], header=None)
    values[0.01].append(data01['Pressure'].max())
    data05 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/velocities/cerocinco/table{}".format(i), names=["Temp", "Pressure"], header=None)
    values[0.05].append(data05['Pressure'].max())
    data1 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/velocities/uno/table{}".format(i), names=["Temp", "Pressure"], header=None)
    values[0.1].append(data1['Pressure'].max())
    data15 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/velocities/unocinco/table{}".format(i), names=["Temp", "Pressure"], header=None)
    values[0.15].append(data15['Pressure'].max())
    data2 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/velocities/dos/table{}".format(i), names=["Temp", "Pressure"], header=None)
    values[0.2].append(data2['Pressure'].max())
    data25 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/velocities/doscinco/table{}".format(i), names=["Temp", "Pressure"], header=None)
    values[0.25].append(data25['Pressure'].max())

a = [1.2071626270176518E20,3.0179065675441316E21,1.2071626270176526E22,2.716115910789715E22,4.8286505080706105E22,7.544766418860323E22]
b = [statistics.mean(values[0.01]), statistics.mean(values[0.05]), statistics.mean(values[0.1]), statistics.mean(values[0.15]), statistics.mean(values[0.2]), statistics.mean(values[0.25])]
c = [statistics.stdev(values[0.01]), statistics.stdev(values[0.05]), statistics.stdev(values[0.1]), statistics.stdev(values[0.15]), statistics.stdev(values[0.2]), statistics.stdev(values[0.25])]
plt.errorbar(a,b,yerr=c, mfc='red', fmt='-o')
plt.xlabel('Temperatura [K]')
plt.ylabel('Presión [N/m]')
plt.grid()
plt.show()