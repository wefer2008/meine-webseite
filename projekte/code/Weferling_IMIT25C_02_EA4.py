import tensorflow as tf
import numpy as np
import csv

# -------------------------------------------------------
# Funktion: Ziffer aus einer CSV-Datei einlesen
# -------------------------------------------------------
def ziffer_einlesen(dateiname):
    """
    Liest eine Ziffer aus einer CSV-Datei ein.
    Die CSV-Datei enthält eine Zeile mit 784 Pixelwerten (28x28).
    Gibt ein numpy-Array im Format (1, 28, 28, 1) zurück.
    """
    with open(dateiname, encoding="utf-8") as datei:
        daten = datei.readlines()
    daten_aufbereitet = daten[0].split(",")
    return daten_aufbereitet


# -------------------------------------------------------
# Funktion: Daten für die Vorhersage aufbereiten
# -------------------------------------------------------
def daten_aufbereiten(rohdaten):
    """
    Wandelt eine Liste von 784 Pixelwerten in ein
    numpy-Array im Format (1, 28, 28, 1) um,
    das direkt für modell.predict() verwendet werden kann.
    """
    matrix = np.asarray(rohdaten, dtype=np.float32)
    matrix = matrix.reshape(28, 28)
    matrix_vorhersage = matrix.reshape(1, 28, 28, 1)
    return matrix_vorhersage

# -------------------------------------------------------
# Funktion: Ziffer erkennen
# -------------------------------------------------------
def ziffer_erkennen(modell, matrix_vorhersage):
    """
    Gibt die erkannte Ziffer (0-9) zurück.
    """
    vorhersage = modell.predict(matrix_vorhersage, verbose=0)
    return vorhersage.argmax()


# -------------------------------------------------------
# MNIST-Daten laden und Modell trainieren
# -------------------------------------------------------
print("Lade MNIST-Daten und trainiere Modell...")

# die Daten über Keras beschaffen
(x_train, y_train), (x_test, y_test) = tf.keras.datasets.mnist.load_data()

# die Werte für die Arrays beschaffen
# für die Trainingsdaten
training_anzahl = x_train.shape[0]
training_matrix_x = x_train.shape[1]
training_matrix_y = x_train.shape[2]
# und für die vierte Dimension zuweisen
training_matrix_z = 1

# und auch für die Testdaten
test_anzahl = x_test.shape[0]
test_matrix_x = x_test.shape[1]
test_matrix_y = x_test.shape[2]
# und für die vierte Dimension zuweisen
test_matrix_z = 1

# zur Kontrolle ausgeben
print("Trainingsdaten:")
print(training_anzahl, training_matrix_x, training_matrix_y, training_matrix_z)
print("Testdaten:")
print(test_anzahl, test_matrix_x, test_matrix_y, test_matrix_z)

# und passend umbauen
# wir benötigen ein Array mit vier Dimensionen
x_train = x_train.reshape(training_anzahl, training_matrix_x, training_matrix_y, training_matrix_z)
x_test = x_test.reshape(test_anzahl, test_matrix_x, test_matrix_y, test_matrix_z)

# das Eingabeformat für Conv2D festlegen
input_shape = (training_matrix_x, training_matrix_y, training_matrix_z)

# das Modell erstellen
modell = tf.keras.models.Sequential()
modell.add(tf.keras.Input(shape=input_shape))  # ← neu: expliziter Input-Layer
modell.add(tf.keras.layers.Conv2D(16, kernel_size=(3, 3), activation="relu"))
modell.add(tf.keras.layers.Conv2D(16, kernel_size=(3, 3), activation="relu"))
modell.add(tf.keras.layers.MaxPooling2D(pool_size=(2, 2)))
# zum "Flatten"
modell.add(tf.keras.layers.Flatten())
modell.add(tf.keras.layers.Dense(10, activation="softmax"))

# das Modell für das Training vorbereiten
modell.compile(optimizer="adam", loss="sparse_categorical_crossentropy", metrics=["accuracy"])

# und trainieren
modell.fit(x=x_train,y=y_train, epochs=1, verbose=1)

# -------------------------------------------------------
# Postleitzahl Ziffer für Ziffer einlesen und erkennen
# -------------------------------------------------------
# Die fünf CSV-Dateien müssen im selben Ordner liegen.
# Benennen Sie Ihre Dateien z.B. plz_0.csv bis plz_4.csv
# (erzeugt mit imit25c_04_01.py für jede Ziffer einzeln).

csv_dateien = ["plz_0.csv", "plz_1.csv", "plz_2.csv", "plz_3.csv", "plz_4.csv"]

erkannte_ziffern = []

for dateiname in csv_dateien:
    # 1) Rohdaten einlesen
    rohdaten = ziffer_einlesen(dateiname)
    # 2) Daten aufbereiten
    matrix_vorhersage = daten_aufbereiten(rohdaten)
    # 3) Ziffer erkennen
    ziffer = ziffer_erkennen(modell, matrix_vorhersage)
    erkannte_ziffern.append(str(ziffer))

# Postleitzahl nebeneinander ausgeben
print("\nErkannte Postleitzahl:", "".join(erkannte_ziffern))
