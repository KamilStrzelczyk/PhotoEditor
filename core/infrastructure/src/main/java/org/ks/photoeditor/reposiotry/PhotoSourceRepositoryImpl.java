package org.ks.photoeditor.reposiotry;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.imageio.ImageIO;
import javax.inject.Singleton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Singleton
public class PhotoSourceRepositoryImpl implements PhotoSourceRepository {

    private BufferedImage basePhoto;
    private final BehaviorSubject<BufferedImage> photoSubject = BehaviorSubject.create();

    @Override
    public BehaviorSubject<BufferedImage> getCurrentPhoto() {
        return photoSubject;
    }

    @Override
    public boolean loadedNewPhoto(File photo) {
        try {
            BufferedImage image = ImageIO.read(photo);
            if (image != null) {
                basePhoto = image;
                photoSubject.onNext(image);
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
    public void loadedEditedPhoto(UUID id) {

    }

    @Override
    public void saveEditedPhoto() {

    }

    @Override
    public void updatePhoto(BufferedImage editedImage) {
        photoSubject.onNext(editedImage);
    }
}