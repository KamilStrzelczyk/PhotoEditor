package org.ks.photoeditor.usecase;

import io.reactivex.rxjava3.disposables.Disposable;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SetSepiaEffectUseCase {
    PhotoSourceRepository userRepository;
    BufferedImage image;

    @Inject
    public SetSepiaEffectUseCase(PhotoSourceRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void run() {
        System.out.println("Sepia image 1");
        Disposable photoSubscription = userRepository.getCurrentPhoto().firstElement()
                .subscribe(
                        photo -> {
                            if (photo != null) {
                                System.out.println("Otrzymano zdjęcie.");
                                image = photo;
                                userRepository.updatePhoto(applySepia(photo));

                            } else {
                                System.out.println("Otrzymano null.");
                            }
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("Strumień zakończony.");
                        }
                );
    }

    public static BufferedImage applySepia(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage sepiaImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                int sepiaRed = (int) Math.min(255, 0.393 * red + 0.769 * green + 0.189 * blue);
                int sepiaGreen = (int) Math.min(255, 0.349 * red + 0.686 * green + 0.168 * blue);
                int sepiaBlue = (int) Math.min(255, 0.272 * red + 0.534 * green + 0.131 * blue);

                int sepiaRgb = new Color(sepiaRed, sepiaGreen, sepiaBlue).getRGB();
                sepiaImage.setRGB(x, y, sepiaRgb);
            }
        }

        return sepiaImage;
    }
}