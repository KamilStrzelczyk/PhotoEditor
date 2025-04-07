package org.ks.photoeditor.presentation.editorscreen.effectsidepanel;

import java.awt.*;
import javax.swing.*;

public class EffectSidePanel extends JPanel {
  SidePanelViewModel viewModel;
  private final JButton sepiaButton = new JButton("Sepia");
  private final JButton negativeButton = new JButton("Negative");
  private final JButton grayscaleButton = new JButton("Grayscale");

  public EffectSidePanel(SidePanelViewModel sidePanelViewModel) {
    this.viewModel = sidePanelViewModel;
  }

  public void run() {
    setLayout(new GridBagLayout());

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
    buttonPanel.add(sepiaButton);
    buttonPanel.add(negativeButton);
    buttonPanel.add(grayscaleButton);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.insets = new Insets(20, 20, 20, 20);
    gbc.anchor = GridBagConstraints.CENTER;
    add(buttonPanel, gbc);

    sepiaButton.addActionListener(
        e -> viewModel.onSidePanelEffectClicked(EffectSidePanelAction.SEPIA));
    negativeButton.addActionListener(
        e -> viewModel.onSidePanelEffectClicked(EffectSidePanelAction.NEGATIVE));
    grayscaleButton.addActionListener(
        e -> viewModel.onSidePanelEffectClicked(EffectSidePanelAction.GRAYSCALE));

    setVisible(false);
  }

  public void setPanelVisible(Boolean isVisible) {
    setVisible(isVisible);
    repaint();
  }
}
