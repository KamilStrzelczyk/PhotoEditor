package org.ks.photoeditor;

import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import javax.swing.*;
import java.io.File;

public class EditorScreen {
    PhotoSourceRepository userRepository;

    public EditorScreen(PhotoSourceRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("test");
        new ImageDisplay(userRepository.getCurrentPhoto());
    }
}

class ImageDisplay {
    ImageDisplay(File file) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Wyświetlanie Obrazka");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);

            ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());

            JLabel imageLabel = new JLabel(imageIcon);
            frame.add(imageLabel);

            frame.setVisible(true);
        });
    }
}

