import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pruefung_Weferling_Aufgabe_19 extends JFrame {
	
	private static final long serialVersionUID = 4489476382474399657L;
	
	// Roemische Ziffern und ihre Dezimalwerte
    // Reihenfolge ist wichtig: groeßte Werte zuerst!
    // Auch Sonderfaelle wie IV, IX, XL usw. sind enthalten
    private static final int[]    WERTE   = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] ZIFFERN = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

    // Eingabefeld und Ausgabe
    private JTextField eingabe;
    private JLabel     labelErgebnis;

    //Konstruktor
    public Pruefung_Weferling_Aufgabe_19() {
        setTitle("Roemische <-> Arabische Zahlen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        //Eingabe-Panel (oben)
        JPanel eingabePanel = new JPanel(new GridLayout(3, 1, 5, 5));
        eingabePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        eingabePanel.add(new JLabel("Zahl eingeben (arabisch oder römisch):"));
        eingabe = new JTextField();
        eingabe.setFont(new Font("Arial", Font.PLAIN, 16));
        eingabePanel.add(eingabe);

        labelErgebnis = new JLabel("Ergebnis: ");
        labelErgebnis.setFont(new Font("Arial", Font.BOLD, 16));
        eingabePanel.add(labelErgebnis);
        
        //Button-Panel (unten)
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        JButton buttonUmrechnen = new JButton("Umrechnen");
        JButton buttonReset     = new JButton("Reset");

        buttonPanel.add(buttonUmrechnen);
        buttonPanel.add(buttonReset);

        add(eingabePanel, BorderLayout.CENTER);
        add(buttonPanel,  BorderLayout.SOUTH);
        
        //Button-Listener
        buttonUmrechnen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                umrechnen();
            }
        });

        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eingabe.setText("");
                labelErgebnis.setText("Ergebnis: ");
            }
        });

        setSize(400, 220);
    }
    
    // Entscheidet ob die Eingabe arabisch oder roemisch ist
    // und ruft die passende Methode auf
    private void umrechnen() {
        String eingabeText = eingabe.getText().trim().toUpperCase();

        if (eingabeText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Bitte eine Zahl eingeben!",
                "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Wenn die Eingabe nur Ziffern enthaelt -> arabisch -> nach roemisch umrechnen
        if (eingabeText.matches("[0-9]+")) {
            int zahl = Integer.parseInt(eingabeText);

            // Gueltigkeitsbereich pruefen (roemische Zahlen: 1 bis 3999)
            if (zahl < 1 || zahl > 3999) {
                JOptionPane.showMessageDialog(this,
                    "Bitte eine Zahl zwischen 1 und 3999 eingeben!",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            labelErgebnis.setText("Ergebnis: " + arabischZuRoemisch(zahl));
            
        // Wenn die Eingabe nur roemische Zeichen enthält -> nach arabisch umrechnen
        } else if (eingabeText.matches("[IVXLCDM]+")) {
            int ergebnis = roemischZuArabisch(eingabeText);

            if (ergebnis == -1) {
                JOptionPane.showMessageDialog(this,
                    "Ungueltige roemische Zahl!",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            labelErgebnis.setText("Ergebnis: " + ergebnis);

        } else {
            JOptionPane.showMessageDialog(this,
                "Ungueltige Eingabe! Bitte arabische oder roemische Zahl eingeben.",
                "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Rechnet eine arabische Zahl in eine roemische um
    private String arabischZuRoemisch(int zahl) {
        StringBuilder ergebnis = new StringBuilder();

        // Für jeden Wert in der Tabelle so oft wie moeglich subtrahieren
        for (int i = 0; i < WERTE.length; i++) {
            while (zahl >= WERTE[i]) {
                ergebnis.append(ZIFFERN[i]);
                zahl -= WERTE[i];
            }
        }
        return ergebnis.toString();
    }
    
    // Rechnet eine roemische Zahl in eine arabische um
    private int roemischZuArabisch(String roemisch) {
        int ergebnis = 0;
        int i = 0;

        while (i < roemisch.length()) {
            // Pruefung ob ein zweistelliger Sonderfall vorliegt (CM, IX usw.)
            if (i + 1 < roemisch.length()) {
                String zweiZeichen = roemisch.substring(i, i + 2);
                int indexZwei = findeIndex(zweiZeichen);
                if (indexZwei != -1) {
                    ergebnis += WERTE[indexZwei];
                    i += 2; // zwei Zeichen ueberspringen
                    continue;
                }
            }
            
            // Einstellige Ziffer verarbeiten
            String einZeichen = roemisch.substring(i, i + 1);
            int indexEin = findeIndex(einZeichen);
            if (indexEin == -1) {
                return -1; // ungueltige Ziffer gefunden
            }
            ergebnis += WERTE[indexEin];
            i++;
        }
        return ergebnis;
    }
    
    // Hilfsmethode: sucht einen Wert in der ZIFFERN-Tabelle
    // gibt den Index zurueck, oder -1 wenn nicht gefunden
    private int findeIndex(String ziffer) {
        for (int i = 0; i < ZIFFERN.length; i++) {
            if (ZIFFERN[i].equals(ziffer)) {
                return i;
            }
        }
        return -1;
    }




	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            Pruefung_Weferling_Aufgabe_19 fenster = new Pruefung_Weferling_Aufgabe_19();
            fenster.setVisible(true);
        });

	}

}
