package ui;

import javax.swing.*;
import java.io.FileNotFoundException;
// REPRESENTS HOW IT CAN RUN ALL MY CODE

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Gui gui = new Gui();
            gui.pack();
            gui.setLocationRelativeTo(null);
            gui.setVisible(true);
        });
    }
}

