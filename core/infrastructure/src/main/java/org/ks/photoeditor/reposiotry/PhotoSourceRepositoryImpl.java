package org.ks.photoeditor.reposiotry;

import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.imageio.ImageIO;
import javax.inject.Singleton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Singleton
public class PhotoSourceRepositoryImpl implements PhotoSourceRepository {

    File basePhoto;

    @Override
    public BufferedImage getCurrentPhoto() {
        if (basePhoto == null) {
            return null;
        }

        try {
            return ImageIO.read(basePhoto);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
