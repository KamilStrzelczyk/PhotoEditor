package org.ks.photoeditor.usecase;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.ks.photoeditor.repository.PhotoSourceRepository;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;

public class SetImageBlurUseCase {
    BlurAlgorithm algorithm = new BlurAlgorithm();
    PhotoSourceRepository userRepository;
    BufferedImage image;

    @Inject
    public SetImageBlurUseCase(PhotoSourceRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void applyBlur(int blurRadius) {
        System.out.println("Blurred image 1");
        Disposable photoSubscription = userRepository.getCurrentPhoto().observeOn(Schedulers.computation()).firstElement()
                .subscribe(
                        photo -> {
                            if (photo != null) {
                                image = photo;
                                userRepository.updatePhoto(algorithm.run(blurRadius, photo));

                            }
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("Strumień zakończony.");
                        }
                );
    }
}

class BlurAlgorithm {

    BufferedImage run(int blurRadius, BufferedImage image) {
        System.out.println("Blurred image 2");

        Kernel kernel = createBlurKernel(blurRadius);
        BufferedImage extendedImage = extendImageWithBorders(image, blurRadius);
        BufferedImage blurredExtendedImage = applyConvolution(extendedImage, kernel);
        BufferedImage blurredImage = cropBlurredImage(blurredExtendedImage, blurRadius, image.getWidth(), image.getHeight());

        System.out.println("Blurred image created.");
        return (blurredImage);
    }

    private Kernel createBlurKernel(int blurRadius) {
        int size = blurRadius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];
        Arrays.fill(data, weight);
        return new Kernel(size, size, data);
    }

    private BufferedImage extendImageWithBorders(BufferedImage image, int blurRadius) {
        BufferedImage extendedImage = new BufferedImage(image.getWidth() + 2 * blurRadius, image.getHeight() + 2 * blurRadius, image.getType());
        Graphics2D g2dExtended = extendedImage.createGraphics();
        g2dExtended.drawImage(image, blurRadius, blurRadius, null);
        g2dExtended.dispose();

        copyImageBorders(image, extendedImage, blurRadius);
        return extendedImage;
    }

    private void copyImageBorders(BufferedImage originalImage, BufferedImage extendedImage, int blurRadius) {
        for (int i = 0; i < blurRadius; i++) {
            for (int j = 0; j < extendedImage.getWidth(); j++) {
                int originalX = j - blurRadius;
                int originalYTop = 0;
                int originalYBottom = originalImage.getHeight() - 1;

                if (originalX >= 0 && originalX < originalImage.getWidth()) {
                    extendedImage.setRGB(j, i, originalImage.getRGB(originalX, originalYTop));
                    extendedImage.setRGB(j, extendedImage.getHeight() - 1 - i, originalImage.getRGB(originalX, originalYBottom));
                }
            }
            for (int j = 0; j < extendedImage.getHeight(); j++) {
                int originalXLeft = 0;
                int originalXRight = originalImage.getWidth() - 1;
                int originalY = j - blurRadius;

                if (originalY >= 0 && originalY < originalImage.getHeight()) {
                    extendedImage.setRGB(i, j, originalImage.getRGB(originalXLeft, originalY));
                    extendedImage.setRGB(extendedImage.getWidth() - 1 - i, j, originalImage.getRGB(originalXRight, originalY));
                }
            }
        }
    }

    private BufferedImage applyConvolution(BufferedImage extendedImage, Kernel kernel) {
        ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return convolve.filter(extendedImage, null);
    }

    private BufferedImage cropBlurredImage(BufferedImage blurredExtendedImage, int blurRadius, int originalWidth, int originalHeight) {
        return blurredExtendedImage.getSubimage(blurRadius, blurRadius, originalWidth, originalHeight);
    }
}