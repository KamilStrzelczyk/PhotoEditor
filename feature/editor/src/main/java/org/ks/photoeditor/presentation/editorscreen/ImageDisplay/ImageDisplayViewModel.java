package org.ks.photoeditor.presentation.editorscreen.ImageDisplay;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.inject.Inject;

import org.ks.photoeditor.domain.model.ImagePosition;
import org.ks.photoeditor.repository.PhotoSourceRepository;

public class ImageDisplayViewModel {
    public static final String IMAGE_POSITION = "IMAGE_POSITION";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final BehaviorSubject<BufferedImage> photoSubject = BehaviorSubject.create();
    private final PhotoSourceRepository photoRepository;
    private Disposable photoSubscription;

    @Inject
    public ImageDisplayViewModel(PhotoSourceRepository photoRepository) {
        this.photoRepository = photoRepository;
        loadPhoto();
    }

    public void loadPhoto() {
        if (photoSubscription != null && !photoSubscription.isDisposed()) {
            photoSubscription.dispose();
        }

        photoSubscription = photoRepository
                .getCurrentPhoto()
                .subscribe(
                        photoSubject::onNext,
                        error -> {});
    }

    public Observable<BufferedImage> getPhoto() {
        return photoSubject;
    }

    public void calculateAndUpdateImagePosition(
            int labelWidth, int labelHeight,
            int horizontalScrollValue, int verticalScrollValue,
            Point labelLocation) {

        BufferedImage photo = photoSubject.getValue();
        if (photo == null) return;

        int originalWidth = photo.getWidth();
        int originalHeight = photo.getHeight();

        int imageX = (labelWidth - originalWidth) / 2 + labelLocation.x - horizontalScrollValue;
        int imageY = (labelHeight - originalHeight) / 2 + labelLocation.y - verticalScrollValue;

        ImagePosition newPosition = new ImagePosition(
                originalWidth,
                originalHeight,
                originalWidth,
                originalHeight,
                imageX,
                imageY
        );

        support.firePropertyChange(IMAGE_POSITION, null, newPosition);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}