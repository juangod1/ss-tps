import pandas as pd
import matplotlib.pyplot as plt
from scipy.stats import linregress
import numpy as np

slopes = []

for i in range(30):
    data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table2d{}".format(i), names=["Generation","Cells Alive"], header=None)
    data.plot(x="Generation", y="Cells Alive")

    log_y_data = np.log(data["Cells Alive"])
    curve_fit = np.polyfit(data["Generation"], log_y_data, 1)
    print(curve_fit)
plt.show()
print(slopes)
