package org.ks.photoeditor.repository;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

public interface PhotoSourceRepository {

    BehaviorSubject<BufferedImage> getCurrentPhoto();

    boolean loadedNewPhoto(File photo);

    void loadedEditedPhoto(UUID id);

    void saveEditedPhoto();

    void updatePhoto(BufferedImage editedImage);
}
