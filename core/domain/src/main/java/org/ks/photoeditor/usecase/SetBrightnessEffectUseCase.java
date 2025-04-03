package org.ks.photoeditor.usecase;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SetBrightnessEffectUseCase {

    public static BufferedImage applyBrightness(BufferedImage image, int brightness) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage brightenedImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                Color color = new Color(rgb);
                int red = Math.min(255, Math.max(0, color.getRed() + brightness));
                int green = Math.min(255, Math.max(0, color.getGreen() + brightness));
                int blue = Math.min(255, Math.max(0, color.getBlue() + brightness));
                int brightenedRgb = new Color(red, green, blue).getRGB();
                brightenedImage.setRGB(x, y, brightenedRgb);
            }
        }

        return brightenedImage;
    }
}
