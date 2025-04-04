package org.ks.photoeditor.presentation.editorscreen;

import org.ks.photoeditor.presentation.dashboard.DashboardScreen;
import org.ks.photoeditor.presentation.editorscreen.component.BottomBar;
import org.ks.photoeditor.presentation.editorscreen.component.ImageDisplay;
import org.ks.photoeditor.presentation.editorscreen.component.TopBar;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static org.ks.photoeditor.presentation.editorscreen.EditorScreenViewModel.STATE;

public class EditorScreen extends JPanel {
    Boolean isTrimButtonVisible = false;
    EditorScreenViewModel viewModel;
    PhotoSourceRepository userRepository;
    ImageDisplay imageDisplay;
    BottomBar bottomBar = new BottomBar();

    @Inject
    public EditorScreen(
            EditorScreenViewModel viewModel,
            ImageDisplay imageDisplay
    ) {
        this.viewModel = viewModel;
        this.imageDisplay = imageDisplay;
        this.userRepository = viewModel.photoSourceRepository;

        viewModel.addPropertyChangeListener(
                new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if (STATE.equals(evt.getPropertyName())) {
                            imageDisplay.runCropper(viewModel.state.isImageCropperVisible());
                            bottomBar.setTrimButtonVisible(viewModel.state.isImageCropperVisible());
                        }
                    }
                });
        initView();
    }

    void initView() {
        setLayout(new BorderLayout());
        SwingUtilities.invokeLater(() -> {
            TopBar topBar = new TopBar(viewModel::onTopBarActionClicked);
            bottomBar.run(
                    onRotateClickedLeft -> viewModel.onRotateClickedLeft(),
                    onRotateClickedRight -> viewModel.onRotateClickedRight(),
                    isTrimButtonClicked -> viewModel.onTrimButtonClicked()
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
