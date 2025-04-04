package org.ks.photoeditor.usecase;

import io.reactivex.rxjava3.disposables.Disposable;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SetContrastEffectUseCase {
    PhotoSourceRepository userRepository;
    BufferedImage image;

    @Inject
    public SetContrastEffectUseCase(PhotoSourceRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(int contrast) {
        System.out.println("Contrast image 1");
        Disposable photoSubscription = userRepository.getCurrentPhoto().firstElement()
                .subscribe(
                        photo -> {
                            if (photo != null) {
                                image = photo;
                                if (contrast == 0) {
                                    userRepository.revertPhoto();
                                } else {
                                    userRepository.updatePhoto(applyContrast(photo, contrast));
                                }
                            }
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("Strumień zakończony.");
                        }
                );
    }

    public static BufferedImage applyContrast(BufferedImage image, int contrast) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage contrastedImage = new BufferedImage(width, height, image.getType());

        double scaleFactor = (contrast + 100.0) / 100.0;
        double shift = 128.0 * (1.0 - scaleFactor);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);

                int red = (int) (color.getRed() * scaleFactor + shift);
                int green = (int) (color.getGreen() * scaleFactor + shift);
                int blue = (int) (color.getBlue() * scaleFactor + shift);

                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));

                int contrastedRgb = new Color(red, green, blue).getRGB();
                contrastedImage.setRGB(x, y, contrastedRgb);
            }
        }

        return contrastedImage;
    }
}