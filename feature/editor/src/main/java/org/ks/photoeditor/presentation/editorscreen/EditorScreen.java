package org.ks.photoeditor.presentation.editorscreen;

import org.ks.photoeditor.presentation.dashboard.DashboardScreen;
import org.ks.photoeditor.presentation.editorscreen.component.ImageDisplay;
import org.ks.photoeditor.presentation.editorscreen.component.TopBar;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetImageBlurUseCase;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;

public class EditorScreen extends JPanel {
    PhotoSourceRepository userRepository;
    SetImageBlurUseCase setImageBlurUseCase;

    ImageDisplay imageDisplay = new ImageDisplay();

    @Inject
    public EditorScreen(PhotoSourceRepository userRepository, SetImageBlurUseCase setImageBlurUseCase) {
        this.userRepository = userRepository;
        this.setImageBlurUseCase = setImageBlurUseCase;
        setLayout(new BorderLayout());
        SwingUtilities.invokeLater(() -> {
            TopBar topBar = new TopBar(
                    onSave -> setImageBlurUseCase.applyBlur(5),
                    onCancel -> {
                    }
            );
            add(topBar, BorderLayout.NORTH);
            add(imageDisplay, BorderLayout.CENTER);
            new DashboardScreen(userRepository, onImageSelected -> {
                imageDisplay.loadPhoto(userRepository);
            });
        });
    }
}
