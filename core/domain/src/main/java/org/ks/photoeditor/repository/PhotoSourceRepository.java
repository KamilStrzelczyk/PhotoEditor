package org.ks.photoeditor.repository;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.ks.photoeditor.model.FrameInfo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

public interface PhotoSourceRepository {

    BehaviorSubject<BufferedImage> getCurrentPhoto();

    boolean loadedNewPhoto(File photo);

    void loadedEditedPhoto(UUID id);

    void saveEditedPhoto();

    void revertPhoto();

    void updatePhoto(BufferedImage editedImage);

    void clear();

    BehaviorSubject<FrameInfo> getFrameInfo();
}
