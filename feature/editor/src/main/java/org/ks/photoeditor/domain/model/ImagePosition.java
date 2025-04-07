package org.ks.photoeditor.domain.model;

public class ImagePosition {
    private final int originalWidth;
    private final int originalHeight;
    private final int displayWidth;
    private final int displayHeight;
    private final int x;
    private final int y;

    public ImagePosition(
            int originalWidth,
            int originalHeight,
            int displayWidth,
            int displayHeight,
            int x,
            int y
    ) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.x = x;
        this.y = y;
    }

    public int originalWidth() {
        return originalWidth;
    }

    public int originalHeight() {
        return originalHeight;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public double scaleX() {
        return (double) originalWidth / displayWidth;
    }

    public double scaleY() {
        return (double) originalHeight / displayHeight;
    }
}
