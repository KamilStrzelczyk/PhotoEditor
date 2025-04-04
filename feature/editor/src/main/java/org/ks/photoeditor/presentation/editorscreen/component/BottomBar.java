package org.ks.photoeditor.presentation.editorscreen.component;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class BottomBar extends JPanel {
    JButton trimButton = new JButton("Przytnij");

    public void run(
            Consumer<Void> onRotateLeft,
            Consumer<Void> onRotateRight,
            Consumer<Void> onTrimButtonClicked
    ) {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton rotateLeftButton = new JButton("Obróć w lewo");
        JButton rotateRightButton = new JButton("Obróć w prawo");

        rotateLeftButton.addActionListener(e -> onRotateLeft.accept(null));
        rotateRightButton.addActionListener(e -> onRotateRight.accept(null));
        trimButton.addActionListener(e -> onTrimButtonClicked.accept(null));

        add(rotateLeftButton);
        add(rotateRightButton);
        add(trimButton);
        trimButton.setVisible(false);
    }


    public void setTrimButtonVisible(Boolean isTrimButtonVisible) {
        trimButton.setVisible(isTrimButtonVisible);
        repaint();
    }
}