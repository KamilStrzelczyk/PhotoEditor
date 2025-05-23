package org.ks.photoeditor;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import org.ks.photoeditor.component.PEFrame;
import org.ks.photoeditor.di.AppComponent;
import org.ks.photoeditor.di.DaggerAppComponent;
import org.ks.photoeditor.di.DomainModule;
import org.ks.photoeditor.di.InfrastructureModule;

public class Main {
  public static void main(String[] args) {
    AppComponent appComponent =
        DaggerAppComponent.builder()
            .infrastructureModule(new InfrastructureModule())
            .domainModule(new DomainModule())
            .build();

    FlatDarkLaf.setup();

    SwingUtilities.invokeLater(
        () -> new SplashScreen(onSplashFinished -> new PEFrame(appComponent.getEditorScreen())));
  }
}
