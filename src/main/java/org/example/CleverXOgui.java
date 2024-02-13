package org.example;
import   javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CleverXOgui extends JFrame {
    private final JButton[][] pulsanti;
    private final CleverXO gioco;

    public CleverXOgui() {
        gioco = new CleverXO();
        pulsanti = new JButton[CleverXO.DIMENSIONE][CleverXO.DIMENSIONE];

        setTitle("org.example.CleverXO");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(CleverXO.DIMENSIONE, CleverXO.DIMENSIONE));

        inizializzaPulsanti();
    }

    private void inizializzaPulsanti() {
        for (int i = 0; i < CleverXO.DIMENSIONE; i++) {
            for (int j = 0; j < CleverXO.DIMENSIONE; j++) {
                JButton pulsante = new JButton();
                pulsante.setFont(new Font("Arial", Font.PLAIN, 40));
                pulsante.setFocusPainted(false); // Rimuovi il bordo di focus
                pulsante.addActionListener(new GestoreClickPulsante(i, j));
                pulsanti[i][j] = pulsante;
                add(pulsante);
            }
        }
    }

    private class GestoreClickPulsante implements ActionListener {
        private final int riga;
        private final int colonna;

        public GestoreClickPulsante(int riga, int colonna) {
            this.riga = riga;
            this.colonna = colonna;
        }

        public void actionPerformed(ActionEvent e) {
            if (!gioco.isFinito() && gioco.eseguiMossa(riga, colonna, CleverXO.GIOCATORE_O)) {
                aggiornaPulsante(riga, colonna, CleverXO.GIOCATORE_O);

                if (!gioco.isFinito()) {
                    gioco.effettuaMossaAI(); // L'IA effettua la sua mossa
                    int rigaAI = gioco.getUltimaRiga();
                    int colonnaAI = gioco.getUltimaColonna();
                    aggiornaPulsante(rigaAI, colonnaAI, CleverXO.GIOCATORE_X); // Aggiorna il tabellone con la mossa dell'IA
                }

                if (gioco.isFinito()) {
                    char vincitore = gioco.getVincitore();
                    if (vincitore == CleverXO.GIOCATORE_O) {
                        JOptionPane.showMessageDialog(null, "Complimenti! Hai vinto!");
                    } else if (vincitore == CleverXO.GIOCATORE_X) {
                        JOptionPane.showMessageDialog(null, "Hai perso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Pareggio!");
                    }
                }
            }
        }
    }

    private void aggiornaPulsante(int riga, int colonna, char giocatore) {
        JButton pulsante = pulsanti[riga][colonna];
        pulsante.setEnabled(false); // Disabilita il pulsante dopo il clic
        pulsante.setText(String.valueOf(giocatore)); // Imposta il testo del pulsante con il giocatore
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CleverXOgui gui = new CleverXOgui();
            gui.setVisible(true);
        });
    }
}
