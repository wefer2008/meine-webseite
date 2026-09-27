import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pruefung_Weferling_Aufgabe_17 extends JFrame {
	
	    private static final long serialVersionUID = 4614691324430217462L;

		private JTextField anzeige;

	    // Merkt sich die erste Zahl und den gewaehlten Operator
	    private double ersteZahl = 0;
	    private String aktuellerOperator = "";
	    // true, wenn als naechstes eine neue Zahl beginnt (Anzeige soll geloescht werden)
	    private boolean neueZahlBeginnt = true;

	    public Pruefung_Weferling_Aufgabe_17() {
	        setTitle("Taschenrechner");
	        setSize(300, 400);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new BorderLayout());

	        //Anzeige (oben) 
	        anzeige = new JTextField("0");
	        anzeige.setEditable(false); // Eingabe nur ueber Tasten, nicht ueber Tastatur
	        anzeige.setHorizontalAlignment(JTextField.RIGHT);
	        anzeige.setFont(new Font("Arial", Font.BOLD, 24));
	        add(anzeige, BorderLayout.NORTH);
	        
	        //Panel fuer die Schaltflaechen (Mitte)
	        JPanel tastenPanel = new JPanel(new GridLayout(4, 4, 5, 5));
	        tastenPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        // Zentraler Listener fuer alle Tasten
	        TastenListener listener = new TastenListener();

	        // Reihenfolge der Tasten wie bei einem klassischen Taschenrechner
	        String[] tasten = {
	            "7", "8", "9", "/",
	            "4", "5", "6", "*",
	            "1", "2", "3", "-",
	            "0", "C", "=", "+"
	        };

	        for (String beschriftung : tasten) {
	            JButton button = new JButton(beschriftung);
	            button.setFont(new Font("Arial", Font.PLAIN, 18));
	            button.addActionListener(listener);
	            tastenPanel.add(button);
	        }

	        add(tastenPanel, BorderLayout.CENTER);
	    }
	    

	    // Innere Klasse fuer die Ereignisverarbeitung (zentraler Listener)
	    private class TastenListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String befehl = e.getActionCommand();

	            switch (befehl) {
	                // Ziffern-Tasten
	                case "0": case "1": case "2": case "3": case "4":
	                case "5": case "6": case "7": case "8": case "9":
	                    zifferEingeben(befehl);
	                    break;

	                // Operator-Tasten
	                case "+": case "-": case "*": case "/":
	                    operatorWaehlen(befehl);
	                    break;

	                // Gleich-Taste
	                case "=":
	                    berechnen();
	                    break;

	                // Clear-Taste
	                case "C":
	                    zuruecksetzen();
	                    break;
	            }
	        }
	    }
	    
	 // Fuegt eine Ziffer an die Anzeige an
	    private void zifferEingeben(String ziffer) {
	        if (neueZahlBeginnt) {
	            anzeige.setText(ziffer);
	            neueZahlBeginnt = false;
	        } else {
	            anzeige.setText(anzeige.getText() + ziffer);
	        }
	    }

	    // Speichert die erste Zahl und den gewaehlten Operator
	    private void operatorWaehlen(String operator) {
	        // Falls bereits ein Ergebnis angezeigt wird, gilt es als erste Zahl
	        // (siehe Hinweis der Aufgabe)
	        ersteZahl = Double.parseDouble(anzeige.getText());
	        aktuellerOperator = operator;
	        neueZahlBeginnt = true; // naechste Zifferneingabe beginnt eine neue Zahl
	    }
	    
	    // Fuehrt die eigentliche Berechnung durch
	    private void berechnen() {
	        double zweiteZahl = Double.parseDouble(anzeige.getText());
	        double ergebnis = 0;

	        switch (aktuellerOperator) {
	            case "+":
	                ergebnis = ersteZahl + zweiteZahl;
	                break;
	            case "-":
	                ergebnis = ersteZahl - zweiteZahl;
	                break;
	            case "*":
	                ergebnis = ersteZahl * zweiteZahl;
	                break;
	            case "/":
	                // Division durch 0 abfangen
	                if (zweiteZahl == 0) {
	                    anzeige.setText("Fehler: /0");
	                    neueZahlBeginnt = true;
	                    return;
	                }
	                ergebnis = ersteZahl / zweiteZahl;
	                break;
	        }
	        
	        anzeige.setText(Double.toString(ergebnis));
	        neueZahlBeginnt = true; // naechste Eingabe beginnt eine neue Zahl
	    }

	    // Setzt den Taschenrechner zurueck
	    private void zuruecksetzen() {
	        anzeige.setText("0");
	        ersteZahl = 0;
	        aktuellerOperator = "";
	        neueZahlBeginnt = true;
	    }

	
	    
		public static void main(String[] args) {
			SwingUtilities.invokeLater(() -> {
	            Pruefung_Weferling_Aufgabe_17 fenster = new Pruefung_Weferling_Aufgabe_17();
	            fenster.setVisible(true);
	        });
		}
}
