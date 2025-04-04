package org.ks.photoeditor.usecase;

import io.reactivex.rxjava3.disposables.Disposable;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

@Singleton
public class SetTrimOnImageUseCase {
    private final PhotoSourceRepository photoSourceRepository;
    private Rectangle cropRect;

    @Inject
    public SetTrimOnImageUseCase(PhotoSourceRepository photoSourceRepository) {
        this.photoSourceRepository = photoSourceRepository;
    }

    public void run() {
        Disposable photoSubscription = photoSourceRepository.getCurrentPhoto().firstElement()
                .subscribe(
                        photo -> {
                            if (photo != null && cropRect != null) {
                                BufferedImage trimmedImage = trimImage(photo, cropRect);
                                photoSourceRepository.updatePhoto(trimmedImage);
                            } else {
                                System.out.println("Brak zdjęcia lub prostokąta przycinania.");
                            }
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("Strumień zakończony.");
                        }
                );
    }

    public void setCropRect(Rectangle cropRect) {
        this.cropRect = cropRect;
    }

    private BufferedImage trimImage(BufferedImage originalImage, Rectangle cropRect) {
        if (originalImage == null || cropRect == null) {
            return null;
        }

        int width = cropRect.width;
        int height = cropRect.height;

        if (width <= 0 || height <= 0) {
            return null;
        }

        BufferedImage trimmedImage = new BufferedImage(width, height, originalImage.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int originalX = cropRect.x + x;
                int originalY = cropRect.y + y;

                if (originalX >= 0 && originalX < originalImage.getWidth() &&
                        originalY >= 0 && originalY < originalImage.getHeight()) {
                    trimmedImage.setRGB(x, y, originalImage.getRGB(originalX, originalY));
                }
            }
        }

        return trimmedImage;
    }
}