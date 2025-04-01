package org.ks.photoeditor;

import com.formdev.flatlaf.FlatLightLaf;
import org.ks.photoeditor.component.PEFrame;
import org.ks.photoeditor.presentation.SplashScreen;
import org.ks.photoeditor.presentation.dashboard.DashboardScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> new SplashScreen(onSplashFinished -> new PEFrame(new DashboardScreen())));
    }
}
