import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import curve_fit
from scipy.stats import linregress
import statistics

### PLOT FP VS T - N VARIATION ###

data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cien/table{}".format(0), names=["Time", "FP", "Pressure","Temp"], header=None)
plt.plot(data['Time'], data['FP'], label="N=100")
data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cincuenta/table{}".format(0), names=["Time", "FP", "Pressure","Temp"], header=None)
plt.plot(data['Time'], data['FP'], label="N=50")
data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/veinticinco/table{}".format(0), names=["Time", "FP", "Pressure","Temp"], header=None)
plt.plot(data['Time'], data['FP'], label="N=25")
plt.xlabel("Time [s]")
plt.ylabel("fp")
plt.legend(loc='best')
plt.show()

### PLOT INPUT VS OUTPUT T of FP ~ 0.5 VS N ###

values = {25: [], 50: [], 100: []}

def exponential(x, a, b):
    return a*np.exp(b*x)

for i in range(20):
    data25 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/veinticinco/table{}".format(i), names=["Time", "FP", "Pressure","Temp"], header=None)
    values[25].append(data25['Time'].max())
    data50 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cincuenta/table{}".format(i), names=["Time", "FP", "Pressure","Temp"], header=None)
    values[50].append(data50['Time'].max())
    data100 = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/cien/table{}".format(i), names=["Time", "FP", "Pressure","Temp"], header=None)
    values[100].append(data100['Time'].max())

a = [25,50,100]
b = [statistics.mean(values[25]), statistics.mean(values[50]), statistics.mean(values[100])]
c = [statistics.stdev(values[25]), statistics.stdev(values[50]), statistics.stdev(values[100])]
plt.errorbar(a,b,yerr=c, linestyle="None", mfc='red', fmt='-o')
plt.xlabel('N')
plt.ylabel('T estacionario [s]')
plt.xticks([25,50,100])
plt.show()


# ax = data.plot(x="Time", y="Pressure")
# ax.set_xlim(data["Time"].min(), data["Time"].max())
# ax = data.plot(x="Time", y="Temp")
# plt.plot(data['Time'], data['Temp'])
# plt.xlim((data["Time"].min(), data["Time"].max()))
# # plt.ylim((0.0000001*data["Temp"].min(), data["Temp"].max()))