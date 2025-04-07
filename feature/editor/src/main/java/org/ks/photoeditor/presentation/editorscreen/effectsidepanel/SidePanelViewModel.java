package org.ks.photoeditor.presentation.editorscreen.effectsidepanel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetGrayscaleEffectUseCase;
import org.ks.photoeditor.usecase.SetNegativeEffectUseCase;
import org.ks.photoeditor.usecase.SetSepiaEffectUseCase;

public class SidePanelViewModel {
  public PhotoSourceRepository photoSourceRepository;
  public SetSepiaEffectUseCase setSepiaEffect;
  public SetNegativeEffectUseCase setNegativeEffect;
  public SetGrayscaleEffectUseCase setGrayscaleEffect;
  public static final String IS_VISIBLE = "isVisible";
  private boolean isVisible = false;
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);

  public SidePanelViewModel(
      PhotoSourceRepository photoSourceRepository,
      SetSepiaEffectUseCase setSepiaEffect,
      SetNegativeEffectUseCase setNegativeEffect,
      SetGrayscaleEffectUseCase setGrayscaleEffect) {
    this.photoSourceRepository = photoSourceRepository;
    this.setSepiaEffect = setSepiaEffect;
    this.setNegativeEffect = setNegativeEffect;
    this.setGrayscaleEffect = setGrayscaleEffect;
  }

  public void onSidePanelEffectClicked(EffectSidePanelAction action) {
    switch (action) {
      case SEPIA:
        setSepiaEffect.run();
        break;
      case NEGATIVE:
        setNegativeEffect.run();
        break;
      case GRAYSCALE:
        setGrayscaleEffect.run();
        break;
      default:
        break;
    }
  }

  public void setVisible(boolean visible) {
    boolean oldValue = this.isVisible;
    this.isVisible = visible;
    support.firePropertyChange(IS_VISIBLE, oldValue, this.isVisible);
  }

  public boolean isVisible() {
    return isVisible;
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    support.removePropertyChangeListener(listener);
  }
}
