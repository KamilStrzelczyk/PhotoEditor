package org.ks.photoeditor.presentation.editorscreen.component;

import io.reactivex.rxjava3.disposables.Disposable;
import org.ks.photoeditor.presentation.imagecropper.ImageCropper;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImageDisplay extends JPanel {
    private ImageCropper cropper;
    private BufferedImage photo = null;
    private JLabel imageLabel = new JLabel("Brak obrazu do wyświetlenia.");
    private JScrollPane scrollPane = new JScrollPane(imageLabel);

    @Inject
    public ImageDisplay(ImageCropper cropper) {
        this.cropper = cropper;
        setLayout(new OverlayLayout(this));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        add(cropper);
        add(scrollPane);
        cropper.setVisible(false);
    }

    public void runCropper(Boolean isVisible) {
        cropper.setVisible(isVisible);
        System.out.println("RUN!!! " + isVisible);
        this.repaint();
    }

    public void loadPhoto(PhotoSourceRepository userRepository) {
        Disposable photoSubscription = userRepository.getCurrentPhoto().subscribe(image ->
                {
                    photo = image;
                    updateImageDisplay();
                }
        );
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