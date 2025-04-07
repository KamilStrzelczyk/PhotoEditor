package org.ks.photoeditor.presentation.editorscreen;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.swing.*;
import org.ks.photoeditor.presentation.editorscreen.component.TopBarAction;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetGrayscaleEffectUseCase;
import org.ks.photoeditor.usecase.SetImageBlurUseCase;
import org.ks.photoeditor.usecase.SetImagePositionUseCase;
import org.ks.photoeditor.usecase.SetTrimOnImageUseCase;

public class EditorScreenViewModel {
  public static String STATE = "state";
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);
  public EditorViewModelState state = new EditorViewModelState();

  public PhotoSourceRepository photoSourceRepository;
  SetImageBlurUseCase setImageBlur;
  SetGrayscaleEffectUseCase setGrayscaleEffect;
  SetImagePositionUseCase setImagePosition;
  SetTrimOnImageUseCase setTrimOnImage;

  @Inject
  public EditorScreenViewModel(
      PhotoSourceRepository photoSourceRepository,
      SetImageBlurUseCase setImageBlur,
      SetGrayscaleEffectUseCase setGrayscaleEffect,
      SetImagePositionUseCase setImagePosition,
      SetTrimOnImageUseCase setTrimOnImage) {
    this.photoSourceRepository = photoSourceRepository;
    this.setImageBlur = setImageBlur;
    this.setGrayscaleEffect = setGrayscaleEffect;
    this.setImagePosition = setImagePosition;
    this.setTrimOnImage = setTrimOnImage;
  }

  public void onTopBarActionClicked(TopBarAction action) {
    switch (action) {
      case BLUR_CLICKED:
        setImageBlur.applyBlur(5);
        break;
      case CORRECTION_CLICKED:
        state.setLightSelected();
        break;
      case EFFECTS_CLICKED:
        state.setFiltersSelected();
        break;
      case TRIM_CLICKED:
        state.setImageCropperVisible();
        break;
      case RESET_CLICKED:
        photoSourceRepository.revertPhoto();
        break;
      case SAVE_CLICKED:
        new SaveImage(
            photoSourceRepository,
            onFileSaved -> {
              photoSourceRepository.clear();
              state.setCancelled();
            });
        break;
      case CANCEL_CLICKED:
        photoSourceRepository.clear();
        state.setCancelled();
        break;
    }
    state.keepTrimVisible(action);
    support.firePropertyChange(STATE, null, state);
  }

  public void onRotateClickedLeft() {
    System.out.println("onRotateClickedLeft");
    setImagePosition.rotateLeft();
  }

  public void onTrimButtonClicked() {
    System.out.println("onTrimButtonClicked");
    setTrimOnImage.run();
  }

  public void onRotateClickedRight() {
    System.out.println("onRotateClickedRight");
    setImagePosition.rotateRight();
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }
}

class SaveImage {
  JFileChooser fileChooser = new JFileChooser();
  PhotoSourceRepository photoSourceRepository;

  SaveImage(PhotoSourceRepository photoSourceRepository, Consumer<Void> onFileSaved) {
    this.photoSourceRepository = photoSourceRepository;

    JComboBox<String> formatComboBox = new JComboBox<>(new String[] {"PNG", "JPG"});
    JPanel accessoryPanel = new JPanel();
    accessoryPanel.add(new JLabel("Zapisz jako typ: "));
    accessoryPanel.add(formatComboBox);
    fileChooser.setAccessory(accessoryPanel);

    int returnValue = fileChooser.showSaveDialog(null);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String filePath = selectedFile.getAbsolutePath();
      String selectedFormat = (String) formatComboBox.getSelectedItem();

      if (!filePath.toLowerCase().endsWith(".png") && !filePath.toLowerCase().endsWith(".jpg")) {
        filePath += "." + selectedFormat.toLowerCase();
        selectedFile = new File(filePath);
      }

      BufferedImage image = photoSourceRepository.getCurrentPhoto().blockingFirst();

      if (image != null) {
        try {
          ImageIO.write(image, selectedFormat.toLowerCase(), selectedFile);
          JOptionPane.showMessageDialog(null, "Obraz zapisany pomyślnie jako " + selectedFormat);
          onFileSaved.accept(null);
        } catch (IOException e) {
          JOptionPane.showMessageDialog(null, "Błąd podczas zapisywania obrazu.");
          e.printStackTrace();
        }
      }
    }
  }
}
