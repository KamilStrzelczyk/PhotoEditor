package org.ks.photoeditor.presentation.editorscreen;

import static org.ks.photoeditor.presentation.editorscreen.EditorScreenViewModel.STATE;

import java.awt.*;
import javax.inject.Inject;
import javax.swing.*;
import org.ks.photoeditor.presentation.dashboard.DashboardScreen;
import org.ks.photoeditor.presentation.editorscreen.ImageDisplay.ImageDisplay;
import org.ks.photoeditor.presentation.editorscreen.component.BottomBar;
import org.ks.photoeditor.presentation.editorscreen.component.TopBar;
import org.ks.photoeditor.presentation.editorscreen.effectsidepanel.EffectSidePanel;
import org.ks.photoeditor.presentation.editorscreen.lightsidepanel.LightSidePanel;
import org.ks.photoeditor.repository.PhotoSourceRepository;

public class EditorScreen extends JPanel {
  EffectSidePanel effectSidePanel;
  LightSidePanel lightSidePanel;
  EditorScreenViewModel viewModel;
  PhotoSourceRepository userRepository;
  ImageDisplay imageDisplay;
  TopBar topBar;
  DashboardScreen dashboardScreen;
  BottomBar bottomBar = new BottomBar();

  @Inject
  public EditorScreen(
      EditorScreenViewModel viewModel,
      EffectSidePanel effectSidePanel,
      LightSidePanel lightSidePanel,
      ImageDisplay imageDisplay) {
    this.viewModel = viewModel;
    this.imageDisplay = imageDisplay;
    this.userRepository = viewModel.photoSourceRepository;
    this.dashboardScreen = new DashboardScreen(userRepository);
    this.effectSidePanel = effectSidePanel;
    this.lightSidePanel = lightSidePanel;
    viewModel.addPropertyChangeListener(
        evt -> {
          if (STATE.equals(evt.getPropertyName())) {
            updateUIState(viewModel.state);
          }
        });
    initView();
  }

  void initView() {
    setLayout(new BorderLayout());
    SwingUtilities.invokeLater(
        () -> {
          topBar = new TopBar(viewModel::onTopBarActionClicked);
          bottomBar.run(
              onRotateClickedLeft -> viewModel.onRotateClickedLeft(),
              onRotateClickedRight -> viewModel.onRotateClickedRight(),
              isTrimButtonClicked -> viewModel.onTrimButtonClicked());
          add(topBar, BorderLayout.NORTH);
          add(imageDisplay, BorderLayout.CENTER);
          effectSidePanel.run();
          lightSidePanel.run();

          JPanel sidePanelsContainer = new JPanel();
          sidePanelsContainer.setLayout(new BoxLayout(sidePanelsContainer, BoxLayout.Y_AXIS));

          sidePanelsContainer.add(effectSidePanel);
          sidePanelsContainer.add(lightSidePanel);

          add(sidePanelsContainer, BorderLayout.EAST);

          add(bottomBar, BorderLayout.SOUTH);
          dashboardScreen.run();
        });
  }

  private void updateUIState(EditorViewModelState state) {
    imageDisplay.runCropper(state.isImageCropperVisible());
    bottomBar.setTrimButtonVisible(state.isImageCropperVisible());
    dashboardScreen.setIsVisible(state.isCancelled());
    lightSidePanel.setPanelVisible(state.isLightSelected());
    effectSidePanel.setPanelVisible(state.isEffectsSelected());
    topBar.setUndoButtonActive(state.isUndoButtonActive());
    topBar.setSaveButtonActive(state.isSaveButtonActive());
  }
}
