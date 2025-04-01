package org.ks.photoeditor.presentation;

import org.ks.photoeditor.utils.Res;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SplashScreen {
    private static final String SPLASH_IMAGE = "photo_editor_splash.png";

    public SplashScreen() {
        ImageIcon icon = new ImageIcon((Objects.requireNonNull(Res.getImageResourcePath(SPLASH_IMAGE))));
        JWindow splash = new JWindow();
        JLabel label = new JLabel(icon);

        splash.setLayout(new BorderLayout());
        splash.add(label, BorderLayout.CENTER);
        splash.setSize(400, 400);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);
    }
}
