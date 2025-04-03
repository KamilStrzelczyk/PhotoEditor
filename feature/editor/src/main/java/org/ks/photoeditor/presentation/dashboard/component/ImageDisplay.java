package org.ks.photoeditor.presentation.dashboard.component;

import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageDisplay extends JPanel {
    private BufferedImage photo = null;
    private JLabel imageLabel = new JLabel("Brak obrazu do wyświetlenia.");
    private JScrollPane scrollPane = new JScrollPane(imageLabel);

    public ImageDisplay() {
        setLayout(new BorderLayout());
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void loadPhoto(PhotoSourceRepository userRepository) {
        photo = userRepository.getCurrentPhoto();
        updateImageDisplay();
    }

    private void updateImageDisplay() {
        if (photo != null) {
            ImageIcon imageIcon = new ImageIcon(photo);
            imageLabel.setIcon(imageIcon);
            imageLabel.setText(null);
        } else {
            imageLabel.setIcon(null);
            imageLabel.setText("Brak obrazu do wyświetlenia.");
        }
    }
}
