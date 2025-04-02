package org.ks.photoeditor.reposiotry;

import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Singleton;
import java.io.File;
import java.util.UUID;

@Singleton
public class PhotoSourceRepositoryImpl implements PhotoSourceRepository {

    File basePhoto;

    @Override
    public File getCurrentPhoto() {
        return basePhoto;
    }

    @Override
    public boolean loadedNewPhoto(File photo) {
        basePhoto = photo;
        return true;
    }

    @Override
    public void loadedEditedPhoto(UUID id) {

    }

    @Override
    public void saveEditedPhoto() {

    }
}
