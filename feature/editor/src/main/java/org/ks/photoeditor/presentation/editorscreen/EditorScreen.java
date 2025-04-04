package org.ks.photoeditor.presentation.editorscreen;

import org.ks.photoeditor.presentation.dashboard.DashboardScreen;
import org.ks.photoeditor.presentation.editorscreen.component.BottomBar;
import org.ks.photoeditor.presentation.editorscreen.component.ImageDisplay;
import org.ks.photoeditor.presentation.editorscreen.component.TopBar;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;

public class EditorScreen extends JPanel {
    PhotoSourceRepository userRepository;
    ImageDisplay imageDisplay = new ImageDisplay();

    @Inject
    public EditorScreen(EditorScreenViewModel viewModel) {
        this.userRepository = viewModel.photoSourceRepository;
        setLayout(new BorderLayout());
        SwingUtilities.invokeLater(() -> {
            TopBar topBar = new TopBar(viewModel::onTopBarActionClicked);
            BottomBar bottomBar = new BottomBar(
                    onRotateClickedLeft -> viewModel.onRotateClickedLeft(),
                    onRotateClickedRight -> viewModel.onRotateClickedRight()
            );
            add(topBar, BorderLayout.NORTH);
            add(imageDisplay, BorderLayout.CENTER);
            add(bottomBar, BorderLayout.SOUTH);
            new DashboardScreen(userRepository, onImageSelected -> {
                imageDisplay.loadPhoto(userRepository);
            });
        });
    }
}
