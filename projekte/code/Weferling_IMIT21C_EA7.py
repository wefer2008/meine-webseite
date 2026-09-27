# die Importe
from time import sleep
from sense_hat import SenseHat
import matplotlib.pyplot as plt


 
# die Funktion wird regelmäßig aufgerufen
# sie erhält zwei Listen als Argumente und hängt die aktuellen Werte an
def messen(temperaturen, luftdruecke):
    # die Temperatur beschaffen und auf zwei Stellen runden
    temperatur = round(sense.get_temperature(), 2)
    # den Luftdruck beschaffen und auf zwei Stellen runden
    luftdruck = round(sense.get_pressure(), 2)
    # die Werte an die Listen anhängen
    temperaturen.append(temperatur)
    luftdruecke.append(luftdruck)
    # und ausgeben
    print("Temperatur:", temperatur, "°C  |  Luftdruck:", luftdruck, "Millibar")
 
# für die Wartezeit
wartezeit = 2
# für die Aufrufe
aufrufe = 0
maximale_aufrufe = 10
# für die Steuerung
stop = False
 
# die Instanz für das Sense HAT erzeugen
sense = SenseHat()
 
# die leeren Listen
temperaturen = []
luftdruecke = []
 
# der Aufruf
while stop == False:
    aufrufe = aufrufe + 1
    messen(temperaturen, luftdruecke)
    # warten
    sleep(wartezeit)
    if aufrufe == maximale_aufrufe:
        stop = True
 
print(temperaturen)
print(luftdruecke)

 
# über matplotlib zeichnen – beide Kurven in einem Fenster
fig, ax1 = plt.subplots()

ax1.plot(temperaturen, color="red")
ax1.set_ylabel("Temperatur (°C)", color="red")
ax1.set_xlabel("Messung")

ax2 = ax1.twinx()
ax2.plot(luftdruecke, color="blue")
ax2.set_ylabel("Luftdruck (Millibar)", color="blue")

plt.title("Wetterstation - Messwerte")
plt.show()


 
# Temperaturwerte in eine Textdatei schreiben
datei = open("messwerte_temperatur.txt", "w")
for temperatur in temperaturen:
    datei.write(str(temperatur) + "\n")
datei.close()
 
# Luftdruckwerte in eine eigene Textdatei schreiben
datei = open("messwerte_luftdruck.txt", "w")
for luftdruck in luftdruecke:
    datei.write(str(luftdruck) + "\n")
datei.close()