package org.ks.photoeditor.presentation.editorscreen.component;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class BottomBar extends JPanel {

    public BottomBar(Consumer<Void> onRotate, Consumer<Void> onScale) {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton rotateButton = new JButton("Obróć");
        JButton scaleButton = new JButton("Skaluj");

        rotateButton.addActionListener(e -> onRotate.accept(null));
        scaleButton.addActionListener(e -> onScale.accept(null));

        add(rotateButton);
        add(scaleButton);
    }
}