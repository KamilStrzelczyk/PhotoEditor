package org.ks.photoeditor.usecase;

import io.reactivex.rxjava3.disposables.Disposable;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SetBrightnessEffectUseCase {
    PhotoSourceRepository userRepository;
    BufferedImage image;

    @Inject
    public SetBrightnessEffectUseCase(PhotoSourceRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(int brightness) {
        Disposable photoSubscription = userRepository.getCurrentPhoto().firstElement()
                .subscribe(
                        photo -> {
                            if (photo != null) {
                                image = photo;
                                if (brightness == 0) {
                                    userRepository.revertPhoto();
                                } else {
                                    userRepository.updatePhoto(applyBrightness(photo, brightness));
                                }
                            }
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("Strumień zakończony.");
                        }
                );
    }

    public static BufferedImage applyBrightness(BufferedImage image, int brightness) {
        System.out.println("Brightness image 2" + brightness);
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage brightenedImage = new BufferedImage(width, height, image.getType());

        double scaleFactor = 1.0 + brightness / 100.0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);

                // Apply brightness more gently using interpolation
                int red = (int) (color.getRed() + (255 - color.getRed()) * (scaleFactor - 1.0) * 0.5);
                int green = (int) (color.getGreen() + (255 - color.getGreen()) * (scaleFactor - 1.0) * 0.5);
                int blue = (int) (color.getBlue() + (255 - color.getBlue()) * (scaleFactor - 1.0) * 0.5);

                // Ensure values stay within 0-255
                red = Math.min(255, Math.max(0, red));
                green = Math.min(255, Math.max(0, green));
                blue = Math.min(255, Math.max(0, blue));

                int brightenedRgb = new Color(red, green, blue).getRGB();
                brightenedImage.setRGB(x, y, brightenedRgb);
            }
        }

        return brightenedImage;
    }
}