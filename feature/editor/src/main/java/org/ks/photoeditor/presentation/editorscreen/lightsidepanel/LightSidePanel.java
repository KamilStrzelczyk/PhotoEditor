package org.ks.photoeditor.presentation.editorscreen.lightsidepanel;

import java.awt.*;
import javax.inject.Inject;
import javax.swing.*;

public class LightSidePanel extends JPanel {
  LightSidePanelViewModel viewModel;
  private final JSlider brightnessSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
  private final JSlider contrastSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
  private final JButton resetButton = new JButton("Reset");

  @Inject
  public LightSidePanel(LightSidePanelViewModel lightSidePanelViewModel) {
    this.viewModel = lightSidePanelViewModel;
  }

  public void run() {
    setLayout(new GridBagLayout());

    JPanel sliderPanel = new JPanel();
    sliderPanel.setLayout(new GridLayout(3, 2, 10, 10));

    JLabel brightnessLabel = new JLabel("Jasność:");
    JLabel contrastLabel = new JLabel("Kontrast:");

    sliderPanel.add(brightnessLabel);
    sliderPanel.add(brightnessSlider);
    sliderPanel.add(contrastLabel);
    sliderPanel.add(contrastSlider);
    sliderPanel.add(new JLabel(""));
    sliderPanel.add(resetButton);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.insets = new Insets(20, 20, 20, 20);
    gbc.anchor = GridBagConstraints.CENTER;
    add(sliderPanel, gbc);

    brightnessSlider.addChangeListener(
        e -> viewModel.onBrightnessChanged(brightnessSlider.getValue()));
    contrastSlider.addChangeListener(e -> viewModel.onContrastChanged(contrastSlider.getValue()));

    resetButton.addActionListener(
        e -> {
          brightnessSlider.setValue(0);
          contrastSlider.setValue(0);
        });
    resetButton.setBackground(new Color(158, 209, 96));
    resetButton.setForeground(Color.BLACK);

    setVisible(false);
  }

  public void setPanelVisible(Boolean isVisible) {
    setVisible(isVisible);
    repaint();
  }
}
