package org.ks.photoeditor.presentation.editorscreen.lightsidepanel;

import javax.inject.Inject;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetBrightnessEffectUseCase;
import org.ks.photoeditor.usecase.SetContrastEffectUseCase;

public class LightSidePanelViewModel {
  PhotoSourceRepository photoSourceRepository;
  SetBrightnessEffectUseCase setBrightnessEffect;
  SetContrastEffectUseCase setContrastEffect;

  @Inject
  public LightSidePanelViewModel(
      PhotoSourceRepository photoSourceRepository,
      SetBrightnessEffectUseCase setBrightnessEffect,
      SetContrastEffectUseCase setContrastEffect) {
    this.photoSourceRepository = photoSourceRepository;
    this.setBrightnessEffect = setBrightnessEffect;
    this.setContrastEffect = setContrastEffect;
  }

  void onBrightnessChanged(Integer brightness) {
    setBrightnessEffect.run(brightness);
  }

  void onContrastChanged(Integer contrast) {
    setContrastEffect.run(contrast);
  }
}
