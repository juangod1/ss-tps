import pandas as pd
import matplotlib.pyplot as plt

errorsBeeman = []
errorsVerlet = []
errorsGear = []

dataAnalytical = pd.read_csv("/home/agusosimani/Documents/ss-tp1/cinco/tableAnalytical", names=["Time", "A"], header=None)
dataBeeman = pd.read_csv("/home/agusosimani/Documents/ss-tp1/cinco/tableBeeman", names=["Time", "A"], header=None)
dataVerlet = pd.read_csv("/home/agusosimani/Documents/ss-tp1/cinco/tableVerlet", names=["Time", "A"], header=None)
dataGear = pd.read_csv("/home/agusosimani/Documents/ss-tp1/cinco/tableGearPredictorCorrector", names=["Time", "A"], header=None)

errorBeeman = 0
errorVerlet = 0
errorGear = 0

for i in range(len(dataAnalytical)):
    errorBeeman += pow(dataAnalytical["A"][i] - dataBeeman["A"][i], 2)/len(dataAnalytical)
    errorVerlet += pow(dataAnalytical["A"][i] - dataVerlet["A"][i], 2)/len(dataAnalytical)
    errorGear += pow(dataAnalytical["A"][i] - dataGear["A"][i], 2)/len(dataAnalytical)

errorsBeeman.append(errorBeeman)
errorsVerlet.append(errorVerlet)
errorsGear.append(errorGear)

dataAnalytical = pd.read_csv("/home/agusosimani/Documents/ss-tp1/cuatro/tableAnalytical", names=["Time", "A"], header=None)
dataBeeman = pd.read_csv("/home/agusosimani/Documents/ss-tp1/cuatro/tableBeeman", names=["Time", "A"], header=None)
dataVerlet = pd.read_csv("/home/agusosimani/Documents/ss-tp1/cuatro/tableVerlet", names=["Time", "A"], header=None)
dataGear = pd.read_csv("/home/agusosimani/Documents/ss-tp1/cuatro/tableGearPredictorCorrector", names=["Time", "A"], header=None)

errorBeeman = 0
errorVerlet = 0
errorGear = 0

for i in range(len(dataAnalytical)):
    errorBeeman += pow(dataAnalytical["A"][i] - dataBeeman["A"][i], 2)/len(dataAnalytical)
    errorVerlet += pow(dataAnalytical["A"][i] - dataVerlet["A"][i], 2)/len(dataAnalytical)
    errorGear += pow(dataAnalytical["A"][i] - dataGear["A"][i], 2)/len(dataAnalytical)

errorsBeeman.append(errorBeeman)
errorsVerlet.append(errorVerlet)
errorsGear.append(errorGear)

dataAnalytical = pd.read_csv("/home/agusosimani/Documents/ss-tp1/tres/tableAnalytical", names=["Time", "A"], header=None)
dataBeeman = pd.read_csv("/home/agusosimani/Documents/ss-tp1/tres/tableBeeman", names=["Time", "A"], header=None)
dataVerlet = pd.read_csv("/home/agusosimani/Documents/ss-tp1/tres/tableVerlet", names=["Time", "A"], header=None)
dataGear = pd.read_csv("/home/agusosimani/Documents/ss-tp1/tres/tableGearPredictorCorrector", names=["Time", "A"], header=None)

errorBeeman = 0
errorVerlet = 0
errorGear = 0

for i in range(len(dataAnalytical)):
    errorBeeman += pow(dataAnalytical["A"][i] - dataBeeman["A"][i], 2)/len(dataAnalytical)
    errorVerlet += pow(dataAnalytical["A"][i] - dataVerlet["A"][i], 2)/len(dataAnalytical)
    errorGear += pow(dataAnalytical["A"][i] - dataGear["A"][i], 2)/len(dataAnalytical)

errorsBeeman.append(errorBeeman)
errorsVerlet.append(errorVerlet)
errorsGear.append(errorGear)

dataAnalytical = pd.read_csv("/home/agusosimani/Documents/ss-tp1/dos/tableAnalytical", names=["Time", "A"], header=None)
dataBeeman = pd.read_csv("/home/agusosimani/Documents/ss-tp1/dos/tableBeeman", names=["Time", "A"], header=None)
dataVerlet = pd.read_csv("/home/agusosimani/Documents/ss-tp1/dos/tableVerlet", names=["Time", "A"], header=None)
dataGear = pd.read_csv("/home/agusosimani/Documents/ss-tp1/dos/tableGearPredictorCorrector", names=["Time", "A"], header=None)

errorBeeman = 0
errorVerlet = 0
errorGear = 0

for i in range(len(dataAnalytical)):
    errorBeeman += pow(dataAnalytical["A"][i] - dataBeeman["A"][i], 2)/len(dataAnalytical)
    errorVerlet += pow(dataAnalytical["A"][i] - dataVerlet["A"][i], 2)/len(dataAnalytical)
    errorGear += pow(dataAnalytical["A"][i] - dataGear["A"][i], 2)/len(dataAnalytical)

errorsBeeman.append(errorBeeman)
errorsVerlet.append(errorVerlet)
errorsGear.append(errorGear)

times = [0.00001, 0.0001, 0.001, 0.01]
ax = plt.axes(xscale='log', yscale='log')
plt.plot(times, errorsVerlet, "o", linestyle="None", color="red", label="Verlet")
plt.plot(times, errorsBeeman, "o", linestyle="None", color="green", label="Beeman")
plt.plot(times, errorsGear, "o", linestyle="None", color="blue", label="Gear")
plt.xlabel("Paso de integración [s]")
plt.ylabel("Error cuadrático medio [m^2]")
plt.legend(loc='best')
plt.grid()
plt.show()