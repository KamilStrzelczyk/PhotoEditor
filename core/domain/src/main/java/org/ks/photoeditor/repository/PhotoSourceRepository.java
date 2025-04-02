package org.ks.photoeditor.repository;

import java.io.File;
import java.util.UUID;

public interface PhotoSourceRepository {

    File getCurrentPhoto();

    boolean loadedNewPhoto(File photo);

    void loadedEditedPhoto(UUID id);

    void saveEditedPhoto();
}
