package org.ks.photoeditor.presentation.imagecropper;

import static org.ks.photoeditor.presentation.imagecropper.ImageCropperViewModel.FRAME_INFO;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.inject.Inject;
import javax.swing.*;
import org.ks.photoeditor.model.FrameInfo;

public class ImageCropper extends JPanel {
  FrameInfo frameInfo;

  private Rectangle cropRect;
  private Point startPoint;
  private boolean dragging;
  private int dragHandle = -1;

  private static final int HANDLE_SIZE = 8;
  private int originalWidth;
  private int originalHeight;
  private ImageCropperViewModel viewModel;

  @Inject
  public ImageCropper(ImageCropperViewModel viewModel) {
    this.viewModel = viewModel;
    frameInfo = new FrameInfo(0, 0);

    viewModel.addPropertyChangeListener(
        new PropertyChangeListener() {
          @Override
          public void propertyChange(PropertyChangeEvent evt) {
            if (FRAME_INFO.equals(evt.getPropertyName())) {
              frameInfo = (FrameInfo) evt.getNewValue();
              updateCropRect();
            }
          }
        });

    cropRect = new Rectangle(50, 50, 100, 100);
    startPoint = new Point();
    dragging = false;
    setOpaque(false);
    setPreferredSize(new Dimension(500, 500));
    setVisible(true);

    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
            dragHandle = getHandle(e.getPoint());
            if (dragHandle != -1) {
              startPoint = e.getPoint();
              dragging = true;
            } else if (cropRect.contains(e.getPoint())) {
              startPoint = e.getPoint();
              dragging = true;
              dragHandle = 8;
            }
          }

          @Override
          public void mouseReleased(MouseEvent e) {
            dragging = false;
            dragHandle = -1;
            sendCropRect();
          }
        });

    addMouseMotionListener(
        new MouseMotionAdapter() {
          @Override
          public void mouseDragged(MouseEvent e) {
            if (dragging) {
              if (dragHandle == 8) {
                moveCropRect(e.getPoint());
              } else {
                resizeCropRect(e.getPoint());
              }
              startPoint = e.getPoint();
              repaint();
              sendCropRect();
            }
          }
        });
  }

  private void updateCropRect() {
    if (frameInfo != null && frameInfo.width() > 0 && frameInfo.height() > 0) {
      int width = frameInfo.width();
      int height = frameInfo.height();

      originalWidth = getWidth();
      originalHeight = getHeight();

      int cropX = (originalWidth - width) / 2;
      int cropY = (originalHeight - height) / 2;

      cropRect.setBounds(cropX, cropY, width, height);
      repaint();
      sendCropRect();
    }
  }

  private int getHandle(Point p) {
    if (new Rectangle(cropRect.x, cropRect.y, HANDLE_SIZE, HANDLE_SIZE).contains(p))
      return 0; // Lewy górny róg
    if (new Rectangle(
            cropRect.x + cropRect.width - HANDLE_SIZE, cropRect.y, HANDLE_SIZE, HANDLE_SIZE)
        .contains(p)) return 1; // Prawy górny róg
    if (new Rectangle(
            cropRect.x + cropRect.width - HANDLE_SIZE,
            cropRect.y + cropRect.height - HANDLE_SIZE,
            HANDLE_SIZE,
            HANDLE_SIZE)
        .contains(p)) return 2; // Prawy dolny róg
    if (new Rectangle(
            cropRect.x, cropRect.y + cropRect.height - HANDLE_SIZE, HANDLE_SIZE, HANDLE_SIZE)
        .contains(p)) return 3; // Lewy dolny róg
    if (new Rectangle(
            cropRect.x + cropRect.width / 2 - HANDLE_SIZE / 2, cropRect.y, HANDLE_SIZE, HANDLE_SIZE)
        .contains(p)) return 4; // Górna krawędź
    if (new Rectangle(
            cropRect.x,
            cropRect.y + cropRect.height / 2 - HANDLE_SIZE / 2,
            HANDLE_SIZE,
            HANDLE_SIZE)
        .contains(p)) return 5; // Lewa krawędź
    if (new Rectangle(
            cropRect.x + cropRect.width - HANDLE_SIZE,
            cropRect.y + cropRect.height / 2 - HANDLE_SIZE / 2,
            HANDLE_SIZE,
            HANDLE_SIZE)
        .contains(p)) return 6; // Prawa krawędź
    if (new Rectangle(
            cropRect.x + cropRect.width / 2 - HANDLE_SIZE / 2,
            cropRect.y + cropRect.height - HANDLE_SIZE,
            HANDLE_SIZE,
            HANDLE_SIZE)
        .contains(p)) return 7; // Dolna krawędź
    return -1;
  }

  private void resizeCropRect(Point p) {
    int dx = p.x - startPoint.x;
    int dy = p.y - startPoint.y;

    int newX = cropRect.x;
    int newY = cropRect.y;
    int newWidth = cropRect.width;
    int newHeight = cropRect.height;

    switch (dragHandle) {
      case 0: // Lewy górny róg
        newX += dx;
        newY += dy;
        newWidth -= dx;
        newHeight -= dy;
        break;
      case 1: // Prawy górny róg
        newY += dy;
        newWidth += dx;
        newHeight -= dy;
        break;
      case 2: // Prawy dolny róg
        newWidth += dx;
        newHeight += dy;
        break;
      case 3: // Lewy dolny róg
        newX += dx;
        newWidth -= dx;
        newHeight += dy;
        break;
      case 4: // Górna krawędź
        newY += dy;
        newHeight -= dy;
        break;
      case 5: // Lewa krawędź
        newX += dx;
        newWidth -= dx;
        break;
      case 6: // Prawa krawędź
        newWidth += dx;
        break;
      case 7: // Dolna krawędź
        newHeight += dy;
        break;
    }

    // Ograniczenie ramki do obszaru panelu
    if (newX < 0) {
      newWidth -= Math.abs(newX);
      newX = 0;
    }
    if (newY < 0) {
      newHeight -= Math.abs(newY);
      newY = 0;
    }
    if (newX + newWidth > originalWidth) {
      newWidth = originalWidth - newX;
    }
    if (newY + newHeight > originalHeight) {
      newHeight = originalHeight - newY;
    }

    // Dodatkowe ograniczenie rozmiaru
    newWidth = Math.max(HANDLE_SIZE * 2, newWidth);
    newHeight = Math.max(HANDLE_SIZE * 2, newHeight);

    cropRect.x = newX;
    cropRect.y = newY;
    cropRect.width = newWidth;
    cropRect.height = newHeight;
  }

  private void moveCropRect(Point p) {
    int dx = p.x - startPoint.x;
    int dy = p.y - startPoint.y;

    cropRect.x += dx;
    cropRect.y += dy;

    // Ograniczenie ramki do obszaru panelu
    cropRect.x = Math.max(0, Math.min(cropRect.x, originalWidth - cropRect.width));
    cropRect.y = Math.max(0, Math.min(cropRect.y, originalHeight - cropRect.height));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.RED);
    g.drawRect(cropRect.x, cropRect.y, cropRect.width, cropRect.height);

    // Rysowanie uchwytów
    g.setColor(Color.BLUE);
    g.fillRect(cropRect.x, cropRect.y, HANDLE_SIZE, HANDLE_SIZE); // Lewy górny róg
    g.fillRect(
        cropRect.x + cropRect.width - HANDLE_SIZE,
        cropRect.y,
        HANDLE_SIZE,
        HANDLE_SIZE); // Prawy górny róg
    g.fillRect(
        cropRect.x + cropRect.width - HANDLE_SIZE,
        cropRect.y + cropRect.height - HANDLE_SIZE,
        HANDLE_SIZE,
        HANDLE_SIZE); // Prawy dolny róg
    g.fillRect(
        cropRect.x,
        cropRect.y + cropRect.height - HANDLE_SIZE,
        HANDLE_SIZE,
        HANDLE_SIZE); // Lewy dolny róg
    g.fillRect(
        cropRect.x + cropRect.width / 2 - HANDLE_SIZE / 2,
        cropRect.y,
        HANDLE_SIZE,
        HANDLE_SIZE); // Górna krawędź
    g.fillRect(
        cropRect.x,
        cropRect.y + cropRect.height / 2 - HANDLE_SIZE / 2,
        HANDLE_SIZE,
        HANDLE_SIZE); // Lewa krawędź
    g.fillRect(
        cropRect.x + cropRect.width - HANDLE_SIZE,
        cropRect.y + cropRect.height / 2 - HANDLE_SIZE / 2,
        HANDLE_SIZE,
        HANDLE_SIZE); // Prawa krawędź
    g.fillRect(
        cropRect.x + cropRect.width / 2 - HANDLE_SIZE / 2,
        cropRect.y + cropRect.height - HANDLE_SIZE,
        HANDLE_SIZE,
        HANDLE_SIZE); // Dolna krawędź
  }

  private void sendCropRect() {
    if (viewModel != null) {
      viewModel.fetchCropRect(cropRect);
    }
  }
}
