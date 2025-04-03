package org.ks.photoeditor.presentation.editorscreen.component;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.function.Consumer;

public class TopBar extends JPanel {

    public TopBar(Consumer<Void> onSave, Consumer<Void> onCancel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        ImageIcon icon1 = new ImageIcon("icon1.png");
        ImageIcon icon2 = new ImageIcon("icon2.png");
        ImageIcon icon3 = new ImageIcon("icon3.png");

        JButton button1 = new JButton(icon1);
        JButton button2 = new JButton(icon2);
        JButton button3 = new JButton(icon3);

        gbc.gridx = 0;
        gbc.weightx = 0;
        add(button1, gbc);
        gbc.gridx = 1;
        add(button2, gbc);
        gbc.gridx = 2;
        add(button3, gbc);

        gbc.gridx = 3;
        gbc.weightx = 1;
        add(Box.createHorizontalGlue(), gbc);

        JLabel textLabel = new JLabel("31%");
        ImageIcon icon4 = new ImageIcon("icon4.png");
        JButton button4 = new JButton(icon4);

        gbc.gridx = 4;
        gbc.weightx = 0;
        add(textLabel, gbc);
        gbc.gridx = 5;
        add(button4, gbc);

        gbc.gridx = 6;
        gbc.weightx = 1;
        add(Box.createHorizontalGlue(), gbc);


        JButton button5 = createButton("Opcje zapisywania", onSave);
        JButton button6 = createButton("Anuluj", onCancel);

        gbc.gridx = 7;
        gbc.weightx = 0;
        add(button5, gbc);
        gbc.gridx = 8;
        add(button6, gbc);


    }
    private JButton createButton(String text, Consumer<Void> action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.accept(null));
        return button;
    }
}