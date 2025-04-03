package org.ks.photoeditor.repository;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

public interface PhotoSourceRepository {

    BufferedImage getCurrentPhoto();

    boolean loadedNewPhoto(File photo);

    void loadedEditedPhoto(UUID id);

    void saveEditedPhoto();
}
