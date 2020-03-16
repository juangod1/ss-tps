import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

slopes = []

for i in range(10):
    if i==0:
        continue
    print("/home/agusosimani/Documents/ss-tp1/table3d{}".format(i))
    data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/table3d{}".format(i), names=["Generation","Cells Alive"], header=None)
    data.plot(x="Generation", y="Cells Alive")

    log_y_data = np.log(data["Cells Alive"])
    curve_fit = np.polyfit(data["Generation"], log_y_data, 1)
    print(curve_fit)
plt.show()
print(slopes)
