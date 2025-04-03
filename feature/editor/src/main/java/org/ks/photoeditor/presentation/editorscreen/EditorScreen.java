package org.ks.photoeditor.presentation.editorscreen;

import org.ks.photoeditor.presentation.dashboard.DashboardScreen;
import org.ks.photoeditor.presentation.dashboard.component.ImageDisplay;
import org.ks.photoeditor.presentation.dashboard.component.TopBar;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;

public class EditorScreen extends JPanel {
    PhotoSourceRepository userRepository;
    ImageDisplay imageDisplay = new ImageDisplay();

    @Inject
    public EditorScreen(PhotoSourceRepository userRepository) {
        this.userRepository = userRepository;
        setLayout(new BorderLayout());
        SwingUtilities.invokeLater(() -> {
            TopBar topBar = new TopBar();
            add(topBar, BorderLayout.NORTH);
            add(imageDisplay, BorderLayout.CENTER);
            new DashboardScreen(userRepository, e -> {
                imageDisplay.loadPhoto(userRepository);
            });
        });
    }
}