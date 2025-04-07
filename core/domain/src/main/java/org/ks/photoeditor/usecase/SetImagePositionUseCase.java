package org.ks.photoeditor.usecase;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class SetImagePositionUseCase {

    private final PhotoSourceRepository userRepository;
    private BufferedImage image;

    @Inject
    public SetImagePositionUseCase(PhotoSourceRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void rotateLeft() {
        rotateImage(90);
    }

    public void rotateRight() {
        rotateImage(-90);
    }

    private void rotateImage(double angle) {
        System.out.println("Rotating image " + (angle > 0 ? "right" : "left"));
        Disposable photoSubscription = userRepository
                .getCurrentPhoto()
                .observeOn(Schedulers.computation())
                .firstElement()
                .subscribe(
                        photo -> {
                            if (photo != null) {
                                image = photo;
                                userRepository.updatePhoto(rotate(image, angle));
                            }
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("Strumień zakończony.");
                        }
                );
    }

    private BufferedImage rotate(BufferedImage image, double angle) {
        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads));
        double cos = Math.abs(Math.cos(rads));
        int newWidth = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - image.getWidth()) / 2.0, (newHeight - image.getHeight()) / 2.0);

        int x = image.getWidth() / 2;
        int y = image.getHeight() / 2;

        at.rotate(rads, x, y);
        g2d.transform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
}