package org.ks.photoeditor.component;

import javax.swing.*;
import java.awt.*;

public class PEFrame extends JFrame {

    public PEFrame(JPanel screen) {
        setTitle("PhotoEditor");
        setSize(600, 400);
        this.add(screen, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
