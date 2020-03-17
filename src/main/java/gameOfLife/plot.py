import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy.optimize import curve_fit

# for i in range(15):
    # if i==0:
    #     continue
data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table2d{}".format(18), names=["Generation","Cells Alive"], header=None)
ax1 = data.plot(x="Generation", y="Cells Alive")

def exponential(x, a, b):
    return a*np.exp(b*x)

param, param_cov = curve_fit(exponential, np.array(data["Generation"]), np.array(data["Cells Alive"]),[71.65,0.07835])
print(param)

data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table2d{}".format(19), names=["Generation","Cells Alive"], header=None)
ax2 = data.plot(x="Generation", y="Cells Alive")
data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table2d{}".format(20), names=["Generation","Cells Alive"], header=None)
ax3 = data.plot(x="Generation", y="Cells Alive")




# log_y_data = np.log(data["Cells Alive"])
    # curve_fit = np.polyfit(data["Generation"], log_y_data, 1)
    # print(curve_fit)
plt.show()
