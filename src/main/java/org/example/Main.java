package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CleverXOgui gui = new CleverXOgui();
            gui.setTitle("CleverXO");
            gui.setVisible(true);
        });
    }
}
