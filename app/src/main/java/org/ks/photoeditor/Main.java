package org.ks.photoeditor;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import org.ks.photoeditor.component.PEFrame;
import org.ks.photoeditor.di.AppComponent;
import org.ks.photoeditor.di.DaggerAppComponent;
import org.ks.photoeditor.di.InfrastructureModule;
import org.ks.photoeditor.presentation.editorscreen.EditorScreen;

public class Main {
  public static void main(String[] args) {
    AppComponent appComponent =
        DaggerAppComponent.builder().infrastructureModule(new InfrastructureModule()).build();
    FlatDarkLaf.setup();
    SwingUtilities.invokeLater(
        () ->
            new SplashScreen(
                onSplashFinished -> {
                  new PEFrame(
                      new EditorScreen(
                          appComponent.getPhotoSourceRepository(),
                          appComponent.getSetImageBlurUseCase(),
                          appComponent.getSetGrayscaleEffectUseCase()));
                }));
  }
}
