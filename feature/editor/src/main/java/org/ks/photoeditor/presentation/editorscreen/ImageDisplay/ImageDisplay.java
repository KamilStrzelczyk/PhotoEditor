package org.ks.photoeditor.presentation.editorscreen.ImageDisplay;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.inject.Inject;
import javax.swing.*;

import org.ks.photoeditor.domain.model.ImagePosition;
import org.ks.photoeditor.presentation.imagecropper.ImageCropper;

public class ImageDisplay extends JPanel implements PropertyChangeListener {
    private final ImageCropper cropper;
    private final JLabel imageLabel = new JLabel("Brak obrazu do wyświetlenia.");
    private final JScrollPane scrollPane = new JScrollPane(imageLabel);
    private final ImageDisplayViewModel viewModel;

    @Inject
    public ImageDisplay(ImageCropper cropper, ImageDisplayViewModel viewModel) {
        this.cropper = cropper;
        this.viewModel = viewModel;

        setLayout(new OverlayLayout(this));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        add(cropper);
        add(scrollPane);
        cropper.setVisible(false);

        viewModel.addPropertyChangeListener(this);

        viewModel.getPhoto().subscribe(this::updateImageDisplay);

        scrollPane.getHorizontalScrollBar().addAdjustmentListener(
                e -> updateImagePositionInViewModel());
        scrollPane.getVerticalScrollBar().addAdjustmentListener(
                e -> updateImagePositionInViewModel());
    }

    public void runCropper(Boolean isVisible) {
        cropper.setVisible(isVisible);
        if (isVisible) {
            updateImagePositionInViewModel();
        }
        this.repaint();
    }

    private void updateImagePositionInViewModel() {
        viewModel.calculateAndUpdateImagePosition(
                imageLabel.getWidth(),
                imageLabel.getHeight(),
                scrollPane.getHorizontalScrollBar().getValue(),
                scrollPane.getVerticalScrollBar().getValue(),
                imageLabel.getLocation()
        );
    }

    private void updateImageDisplay(BufferedImage photo) {
        if (photo != null) {
            ImageIcon imageIcon = new ImageIcon(photo);
            imageLabel.setIcon(imageIcon);
            imageLabel.setText(null);
            updateImagePositionInViewModel();
        } else {
            imageLabel.setIcon(null);
            imageLabel.setText("Brak obrazu do wyświetlenia.");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (ImageDisplayViewModel.IMAGE_POSITION.equals(evt.getPropertyName())) {
            ImagePosition position = (ImagePosition) evt.getNewValue();
            if (cropper.isVisible()) {
                cropper.updateImageInfo(position);
            }
        }
    }
}