import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import curve_fit
from scipy.stats import linregress
import statistics

### PLOT P VS T ###

data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/gasDiffusion/output/table{}".format(0), names=["Time", "FP", "Pressure","Temp"], header=None, skiprows=4)
ax = data.plot(x="Time", y="FP")
ax.set_xlim(data["Time"].min(), data["Time"].max())
ax = data.plot(x="Time", y="Pressure")
ax.set_xlim(data["Time"].min(), data["Time"].max())
ax = data.plot(x="Time", y="Temp")
ax.set_xlim(data["Time"].min(), data["Time"].max())
ax = data.plot(x="Temp", y="Pressure")
ax.set_xlim(data["Temp"].min(), data["Temp"].max())

plt.show()