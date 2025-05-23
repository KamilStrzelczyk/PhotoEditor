package org.ks.photoeditor.usecase;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SetNegativeEffectUseCase {
    PhotoSourceRepository userRepository;
    BufferedImage image;

    @Inject
    public SetNegativeEffectUseCase(PhotoSourceRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run() {
        System.out.println("Negative image 1");
        Disposable photoSubscription = userRepository
                .getCurrentPhoto()
                .observeOn(Schedulers.computation())
                .firstElement()
                .subscribe(
                        photo -> {
                            if (photo != null) {
                                image = photo;
                                userRepository.updatePhoto(applyNegative(photo));

                            }
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("Strumień zakończony.");
                        }
                );
    }


    public static BufferedImage applyNegative(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage negativeImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int red = 255 - color.getRed();
                int green = 255 - color.getGreen();
                int blue = 255 - color.getBlue();
                int negativeRgb = new Color(red, green, blue).getRGB();
                negativeImage.setRGB(x, y, negativeRgb);
            }
        }

        return negativeImage;
    }
}
