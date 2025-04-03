package org.ks.photoeditor.component;

import org.ks.photoeditor.utils.Res;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PEFrame extends JFrame {
    private static final String PHOTO_EDITOR_IC = "photo_editor_ic.png";

    public PEFrame(JPanel screen) {
        Image icon = new ImageIcon((Objects.requireNonNull(Res.getIcResourcePath(PHOTO_EDITOR_IC)))).getImage();
        setIconImage(icon);
        setTitle("PhotoEditor");
        setSize(600, 400);
        this.add(screen, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}
