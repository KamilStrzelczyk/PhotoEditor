package org.ks.photoeditor;

import org.ks.photoeditor.presentation.SplashScreen;

import static javax.swing.SwingUtilities.invokeLater;

public class Main {
  public static void main(String[] args) {
    invokeLater(SplashScreen::new);
  }
}
