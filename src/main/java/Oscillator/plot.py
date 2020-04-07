import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import linregress
import statistics

dataAnalytical = pd.read_csv("/home/agusosimani/Documents/ss-tp1/tableAnalytical", names=["Time", "A"], header=None)
dataBeeman = pd.read_csv("/home/agusosimani/Documents/ss-tp1/tableBeeman", names=["Time", "A"], header=None)
dataVerlet = pd.read_csv("/home/agusosimani/Documents/ss-tp1/tableVerlet", names=["Time", "A"], header=None)
dataGear = pd.read_csv("/home/agusosimani/Documents/ss-tp1/tableGearPredictorCorrector", names=["Time", "A"], header=None)
plt.plot(dataAnalytical['Time'],dataAnalytical['A'], color="blue", label="Analítica")
plt.plot(dataBeeman['Time'],dataBeeman['A'], color="green", label="Beeman")
plt.plot(dataVerlet['Time'],dataVerlet['A'], color="red", label="Verlet")
plt.plot(dataGear['Time'],dataGear['A'], color="yellow", label="Gear")
plt.xlabel("Tiempo [s]")
plt.ylabel("Amplitud [m]")
plt.legend(loc='best')
plt.grid()
plt.show()
