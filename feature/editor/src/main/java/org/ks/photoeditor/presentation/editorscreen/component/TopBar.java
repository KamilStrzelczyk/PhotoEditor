package org.ks.photoeditor.presentation.editorscreen.component;

import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.*;
import org.ks.photoeditor.utils.Res;

public class TopBar extends JPanel {
  private static final String BLUR_IC = "blur_ic.png";
  private static final String CORRECTION_IC = "correction_ic.png";
  private static final String FILTERS_IC = "filters_ic.png";
  private static final String TRIM_IC = "trim_ic.png";

  private static final int ICON_WIDTH = 32;
  private static final int ICON_HEIGHT = 32;

  public TopBar(Consumer<TopBarAction> actionConsumer) {
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    addToStart(gbc, actionConsumer);
    addToCenter(gbc, actionConsumer);
    addToEnd(gbc, actionConsumer);
  }

  private void addToStart(GridBagConstraints gbc, Consumer<TopBarAction> actionConsumer) {
    JButton resetButton =
        createButton("Resetuj", () -> actionConsumer.accept(TopBarAction.RESET_CLICKED));

    gbc.gridx = 0;
    gbc.weightx = 0;
    add(resetButton, gbc);
    gbc.gridx = 1;
    gbc.weightx = 1;
    add(Box.createHorizontalGlue(), gbc);
  }

  private void addToCenter(GridBagConstraints gbc, Consumer<TopBarAction> actionConsumer) {
    JButton blurButton =
        createIconButton(BLUR_IC, () -> actionConsumer.accept(TopBarAction.BLUR_CLICKED));
    JButton correctionButton =
        createIconButton(
            CORRECTION_IC, () -> actionConsumer.accept(TopBarAction.CORRECTION_CLICKED));
    JButton filtersButton =
        createIconButton(FILTERS_IC, () -> actionConsumer.accept(TopBarAction.EFFECTS_CLICKED));
    JButton trimButton =
        createIconButton(TRIM_IC, () -> actionConsumer.accept(TopBarAction.TRIM_CLICKED));

    gbc.gridx = 2;
    gbc.weightx = 0;
    add(blurButton, gbc);
    gbc.gridx = 3;
    add(correctionButton, gbc);
    gbc.gridx = 4;
    add(filtersButton, gbc);
    gbc.gridx = 5;
    add(trimButton, gbc);
    gbc.gridx = 6;
    gbc.weightx = 1;
    add(Box.createHorizontalGlue(), gbc);
  }

  private void addToEnd(GridBagConstraints gbc, Consumer<TopBarAction> actionConsumer) {
    JButton saveButton =
        createButton("Zapisz", () -> actionConsumer.accept(TopBarAction.SAVE_CLICKED));
    saveButton.setBackground(new Color(158, 209, 96));
    saveButton.setForeground(Color.BLACK);
    saveButton.setOpaque(true);
    JButton cancelButton =
        createButton("Anuluj", () -> actionConsumer.accept(TopBarAction.CANCEL_CLICKED));

    gbc.gridx = 7;
    gbc.weightx = 0;
    add(saveButton, gbc);
    gbc.gridx = 8;
    add(cancelButton, gbc);
  }

  private JButton createIconButton(String iconPath, Runnable action) {
    ImageIcon icon = new ImageIcon(Objects.requireNonNull(Res.getIcResourcePath(iconPath)));
    Image scaledImage =
        icon.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledImage);
    JButton button = new JButton(scaledIcon);
    button.addActionListener(e -> action.run());
    return button;
  }

  private JButton createButton(String text, Runnable action) {
    JButton button = new JButton(text);
    button.addActionListener(e -> action.run());
    return button;
  }
}
