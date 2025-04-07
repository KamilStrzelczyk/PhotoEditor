package org.ks.photoeditor.repository;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.ks.photoeditor.model.FrameInfo;

import java.awt.image.BufferedImage;
import java.io.File;

public interface PhotoSourceRepository {

    BehaviorSubject<BufferedImage> getCurrentPhoto();
    BehaviorSubject<Boolean> canUndo();
    BehaviorSubject<FrameInfo> getFrameInfo();
    BehaviorSubject<Boolean> saveFileIsEnable();

    boolean loadedNewPhoto(File photo);
    void undo();
    void revertPhoto();
    void updatePhoto(BufferedImage editedImage);
    void clear();

}
