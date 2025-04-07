package org.ks.photoeditor.usecase;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SetGrayscaleEffectUseCase {
    PhotoSourceRepository userRepository;
    BufferedImage image;

    @Inject
    public SetGrayscaleEffectUseCase(PhotoSourceRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run() {
        System.out.println("GrayscaleEffec image 1");
        Disposable photoSubscription = userRepository
                .getCurrentPhoto()
                .observeOn(Schedulers.computation())
                .firstElement()
                .subscribe(
                        photo -> {
                            if (photo != null) {
                                image = photo;
                                userRepository.updatePhoto(algorithm(photo));

                            }
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("Strumień zakończony.");
                        }
                );
    }


    public BufferedImage algorithm(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int gray = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
                int grayRgb = new Color(gray, gray, gray).getRGB();
                grayscaleImage.setRGB(x, y, grayRgb);
            }
        }

        return grayscaleImage;
    }
}