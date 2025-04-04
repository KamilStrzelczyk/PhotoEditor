package org.ks.photoeditor.presentation.editorscreen;

import static org.ks.photoeditor.presentation.editorscreen.EditorScreenViewModel.STATE;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.inject.Inject;
import javax.swing.*;
import org.ks.photoeditor.presentation.dashboard.DashboardScreen;
import org.ks.photoeditor.presentation.editorscreen.component.BottomBar;
import org.ks.photoeditor.presentation.editorscreen.component.ImageDisplay;
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
  BottomBar bottomBar = new BottomBar();
  DashboardScreen dashboardScreen;

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
        new PropertyChangeListener() {
          @Override
          public void propertyChange(PropertyChangeEvent evt) {
            if (STATE.equals(evt.getPropertyName())) {
              imageDisplay.runCropper(viewModel.state.isImageCropperVisible());
              bottomBar.setTrimButtonVisible(viewModel.state.isImageCropperVisible());
              dashboardScreen.setIsVisible(viewModel.state.isCancelled());
              lightSidePanel.setPanelVisible(viewModel.state.isLightSelected());
              effectSidePanel.setPanelVisible(viewModel.state.isEffectsSelected());
            }
          }
        });
    initView();
  }

  void initView() {
    setLayout(new BorderLayout());
    SwingUtilities.invokeLater(
        () -> {
          TopBar topBar = new TopBar(viewModel::onTopBarActionClicked);
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
          dashboardScreen.run(
              onImageSelected -> {
                imageDisplay.loadPhoto(userRepository);
              });
        });
  }
}
