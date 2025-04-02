package org.ks.photoeditor;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import org.ks.photoeditor.component.PEFrame;
import org.ks.photoeditor.di.AppComponent;
import org.ks.photoeditor.di.DaggerAppComponent;
import org.ks.photoeditor.di.InfrastructureModule;
import org.ks.photoeditor.infrastructure.di.InitModule;
import org.ks.photoeditor.presentation.SplashScreen;

public class Main {
  public static void main(String[] args) {
    AppComponent appComponent =
        DaggerAppComponent.builder()
            .infrastructureModule(new InfrastructureModule())
            .initModule(new InitModule())
            .build();
    FlatLightLaf.setup();
    SwingUtilities.invokeLater(
        () -> new SplashScreen(onSplashFinished -> new PEFrame(appComponent.getDashboardScreen())));
  }
}
