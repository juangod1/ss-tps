import pandas as pd
import matplotlib.pyplot as plt

### PLOT DISTANCE VS DAY v0=8 ###

data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/Mars/graficos/closestDistanceV8", names=["day", "distance",], header=None)
plt.plot(data['day'], data['distance'], "o", linestyle="None", color="blue", markersize=2)
plt.yscale('log')
plt.xlabel("Días transcurridos desde 6/4/2020")
plt.ylabel("Distancia de la nave a Marte [m]")
plt.grid()
plt.show()

### PLOT DISTANCE VS DAY v0=13 ###

data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/Mars/graficos/closestDistanceV13", names=["day", "distance",], header=None)
plt.plot(data['day'], data['distance'], "o", linestyle="None", color="blue", markersize=2)
plt.yscale('log')
plt.xlabel("Días transcurridos desde 6/4/2020")
plt.ylabel("Distancia de la nave a Marte [m]")
plt.grid()
plt.show()

### PLOT DISTANCE VS DAY ZOOM v0=8 ###

data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/Mars/graficos/closestDistanceV8Every16Minutes", names=["day", "distance",], header=None)
plt.plot(data['day'], data['distance'], "o", linestyle="None", color="blue", markersize=2)
plt.yscale('log')
plt.xlabel("Minutos transcurridos a partir de las 00:00hs del 10/7/2020")
plt.ylabel("Distancia de la nave a Marte [m]")
plt.grid()
plt.show()

### PLOT DISTANCE VS DAY ZOOM v0=13 ###

data = pd.read_csv("/home/agusosimani/Documents/ss-tp1/src/main/java/Mars/graficos/closestDistanceV13Every16Minutes", names=["day", "distance",], header=None)
plt.plot(data['day'], data['distance'], "o", linestyle="None", color="blue", markersize=2)
plt.yscale('log')
plt.xlabel("Minutos transcurridos a partir de las 00:00hs del 14/7/2020")
plt.ylabel("Distancia de la nave a Marte [m]")
plt.grid()
plt.show()