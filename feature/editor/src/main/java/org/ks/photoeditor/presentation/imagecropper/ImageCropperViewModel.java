package org.ks.photoeditor.presentation.imagecropper;

import io.reactivex.rxjava3.disposables.Disposable;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.inject.Inject;
import org.ks.photoeditor.model.FrameInfo;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetTrimOnImageUseCase;

public class ImageCropperViewModel {
  public static String FRAME_INFO = "frameInfo";
  public static FrameInfo frameInfo;
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);
  public PhotoSourceRepository photoSourceRepository;
  public SetTrimOnImageUseCase setTrimOnImage;

  @Inject
  public ImageCropperViewModel(
      PhotoSourceRepository photoSourceRepository, SetTrimOnImageUseCase setTrimOnImage) {
    this.setTrimOnImage = setTrimOnImage;
    this.photoSourceRepository = photoSourceRepository;
    Disposable frameInfoSubscription =
        photoSourceRepository
            .getFrameInfo()
            .subscribe(
                newFrameInfo -> {
                  FrameInfo oldFrameInfo = frameInfo;
                  frameInfo = newFrameInfo;
                  support.firePropertyChange(FRAME_INFO, oldFrameInfo, frameInfo);
                });
  }

  public void fetchCropRect(Rectangle cropRect) {
    setTrimOnImage.setCropRect(cropRect);
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }
}
