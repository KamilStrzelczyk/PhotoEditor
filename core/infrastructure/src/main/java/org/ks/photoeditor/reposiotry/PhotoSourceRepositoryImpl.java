package org.ks.photoeditor.reposiotry;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import org.ks.photoeditor.model.FrameInfo;
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
    private final BehaviorSubject<FrameInfo> frameInfoSubject = BehaviorSubject.createDefault(new FrameInfo(0, 0)); // Domyślne wartości

    @Override
    public BehaviorSubject<BufferedImage> getCurrentPhoto() {
        return photoSubject;
    }

    @Override
    public BehaviorSubject<FrameInfo> getFrameInfo() {
        return frameInfoSubject;
    }

    @Override
    public boolean loadedNewPhoto(File photo) {
        try {
            BufferedImage image = ImageIO.read(photo);
            if (image != null) {
                basePhoto = image;
                photoSubject.onNext(image);
                updateFrameInfo(image);
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
        // Implementacja, jeśli potrzebna
    }

    @Override
    public void saveEditedPhoto() {
        // Implementacja, jeśli potrzebna
    }

    @Override
    public void updatePhoto(BufferedImage editedImage) {
        photoSubject.onNext(editedImage);
        updateFrameInfo(editedImage);
    }

    @Override
    public void revertPhoto() {
        if (basePhoto != null) {
            photoSubject.onNext(basePhoto);
            updateFrameInfo(basePhoto);
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