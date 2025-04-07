package org.ks.photoeditor.presentation.editorscreen.component;

import static org.ks.photoeditor.presentation.editorscreen.component.TopBarAction.*;

import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.*;
import org.ks.photoeditor.utils.Res;

public class TopBar extends JPanel {
  private static final String EMPTY_STRING = "";
  private static final String RESET_BUTTON_TEXT = "Resetuj";
  private static final String UNDO_BUTTON_TEXT = "Cofnij";
  private static final String RESET_TOOLTIP = "Kliknij, aby zresetować";
  private static final String UNDO_TOOLTIP = "Kliknij, aby cofnąć ostatnią operację";
  private static final String SAVE_BUTTON_TEXT = "Zapisz";
  private static final String CANCEL_BUTTON_TEXT = "Anuluj";
  private static final String BLUR_BUTTON_TEXT = "Blur";
  private static final String CORRECTION_BUTTON_TEXT = "Korekta";
  private static final String FILTERS_BUTTON_TEXT = "Filtry";
  private static final String TRIM_BUTTON_TEXT = "Przycinanie";

  private static final String BLUR_IC = "blur_ic.png";
  private static final String CORRECTION_IC = "correction_ic.png";
  private static final String FILTERS_IC = "filters_ic.png";
  private static final String TRIM_IC = "trim_ic.png";

  private static final int ICON_WIDTH = 32;
  private static final int ICON_HEIGHT = 32;

  private JButton undoButton;
  private JButton saveButton;

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
        createButton(
            RESET_BUTTON_TEXT, RESET_TOOLTIP, () -> actionConsumer.accept(RESET_CLICKED), true);
    undoButton =
        createButton(
            UNDO_BUTTON_TEXT, UNDO_TOOLTIP, () -> actionConsumer.accept(UNDO_CLICKED), false);

    gbc.gridx = 0;
    gbc.weightx = 0;
    add(resetButton, gbc);
    gbc.gridx = 1;
    add(undoButton, gbc);
    gbc.gridx = 2;
    gbc.weightx = 1;
    add(Box.createHorizontalGlue(), gbc);
  }

  private void addToCenter(GridBagConstraints gbc, Consumer<TopBarAction> actionConsumer) {
    JButton blurButton =
        createIconButton(BLUR_IC, BLUR_BUTTON_TEXT, () -> actionConsumer.accept(BLUR_CLICKED));
    JButton correctionButton =
        createIconButton(
            CORRECTION_IC, CORRECTION_BUTTON_TEXT, () -> actionConsumer.accept(CORRECTION_CLICKED));
    JButton filtersButton =
        createIconButton(
            FILTERS_IC, FILTERS_BUTTON_TEXT, () -> actionConsumer.accept(EFFECTS_CLICKED));
    JButton trimButton =
        createIconButton(TRIM_IC, TRIM_BUTTON_TEXT, () -> actionConsumer.accept(TRIM_CLICKED));

    gbc.gridx = 3;
    gbc.weightx = 0;
    add(blurButton, gbc);
    gbc.gridx = 4;
    add(correctionButton, gbc);
    gbc.gridx = 5;
    add(filtersButton, gbc);
    gbc.gridx = 6;
    add(trimButton, gbc);
    gbc.gridx = 7;
    gbc.weightx = 1;
    add(Box.createHorizontalGlue(), gbc);
  }

  private void addToEnd(GridBagConstraints gbc, Consumer<TopBarAction> actionConsumer) {
    saveButton =
        createButton(
            SAVE_BUTTON_TEXT,
            EMPTY_STRING,
            () -> actionConsumer.accept(TopBarAction.SAVE_CLICKED),
            false);
    saveButton.setBackground(new Color(158, 209, 96));
    saveButton.setForeground(Color.BLACK);
    saveButton.setOpaque(true);
    JButton cancelButton =
        createButton(
            CANCEL_BUTTON_TEXT,
            EMPTY_STRING,
            () -> actionConsumer.accept(TopBarAction.CANCEL_CLICKED),
            true);

    gbc.gridx = 8;
    gbc.weightx = 0;
    add(saveButton, gbc);
    gbc.gridx = 9;
    add(cancelButton, gbc);
  }

  private JButton createIconButton(String iconPath, String toolTipText, Runnable action) {
    ImageIcon icon = new ImageIcon(Objects.requireNonNull(Res.getIcResourcePath(iconPath)));
    Image scaledImage =
        icon.getImage().getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledImage);
    JButton button = new JButton(scaledIcon);
    button.setToolTipText(toolTipText);
    button.addActionListener(e -> action.run());
    return button;
  }

  private JButton createButton(
      String text, String toolTipText, Runnable action, Boolean isEnabled) {
    JButton button = new JButton(text);
    button.setEnabled(isEnabled);
    button.setToolTipText(toolTipText);
    button.addActionListener(e -> action.run());
    return button;
  }

  public void setUndoButtonActive(boolean isActive) {
    undoButton.setEnabled(isActive);
  }

  public void setSaveButtonActive(boolean isActive) {
    saveButton.setEnabled(isActive);
  }
}
