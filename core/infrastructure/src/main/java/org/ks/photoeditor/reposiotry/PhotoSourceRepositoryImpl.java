package org.ks.photoeditor.reposiotry;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.inject.Singleton;
import org.ks.photoeditor.model.FrameInfo;
import org.ks.photoeditor.repository.PhotoSourceRepository;

@Singleton
public class PhotoSourceRepositoryImpl implements PhotoSourceRepository {

  private BufferedImage basePhoto;
  private final BehaviorSubject<Boolean> saveFileIsEnable = BehaviorSubject.createDefault(false);
  private final BehaviorSubject<BufferedImage> photoSubject = BehaviorSubject.create();
  private final BehaviorSubject<FrameInfo> frameInfoSubject =
      BehaviorSubject.createDefault(new FrameInfo(0, 0));

  private final List<BufferedImage> photoHistory = new ArrayList<>();
  private final BehaviorSubject<Boolean> canUndoSubject = BehaviorSubject.createDefault(false);
  private static final int MAX_HISTORY_SIZE = 5;

  @Override
  public BehaviorSubject<BufferedImage> getCurrentPhoto() {
    return photoSubject;
  }

  @Override
  public BehaviorSubject<FrameInfo> getFrameInfo() {
    return frameInfoSubject;
  }

  @Override
  public BehaviorSubject<Boolean> canUndo() {
    return canUndoSubject;
  }

  @Override
  public boolean loadedNewPhoto(File photo) {
    try {
      BufferedImage image = ImageIO.read(photo);
      if (image != null) {
        photoHistory.clear();

        basePhoto = image;
        saveFileIsEnable.onNext(true);
        photoSubject.onNext(image);
        updateFrameInfo(image);
        canUndoSubject.onNext(false);
        return true;
      } else {
        return false;
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public BehaviorSubject<Boolean> saveFileIsEnable() {
    return saveFileIsEnable;
  }

  @Override
  public void updatePhoto(BufferedImage editedImage) {
    BufferedImage currentImage = photoSubject.getValue();
    if (currentImage != null) {
      addToHistory(currentImage);
    }

    photoSubject.onNext(editedImage);
    updateFrameInfo(editedImage);
  }

  private void addToHistory(BufferedImage image) {
    if (photoHistory.size() >= MAX_HISTORY_SIZE) {
      photoHistory.removeFirst();
    }

    photoHistory.add(image);
    canUndoSubject.onNext(true);
  }

  @Override
  public void undo() {
    if (!photoHistory.isEmpty()) {
      BufferedImage previousImage = photoHistory.removeLast();

      photoSubject.onNext(previousImage);
      updateFrameInfo(previousImage);

      canUndoSubject.onNext(!photoHistory.isEmpty());
    }
  }

  @Override
  public void clear() {
    basePhoto = null;
    saveFileIsEnable.onNext(false);
    photoHistory.clear();
    photoSubject.onNext(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
    frameInfoSubject.onNext(new FrameInfo(0, 0));
    canUndoSubject.onNext(false);
  }

  @Override
  public void revertPhoto() {
    if (basePhoto != null) {
      photoHistory.clear();

      photoSubject.onNext(basePhoto);
      updateFrameInfo(basePhoto);
      canUndoSubject.onNext(false);
    }
  }

  private void updateFrameInfo(BufferedImage image) {
    if (image != null) {
      frameInfoSubject.onNext(new FrameInfo(image.getWidth(), image.getHeight()));
    } else {
      frameInfoSubject.onNext(new FrameInfo(0, 0));
    }
  }
}
