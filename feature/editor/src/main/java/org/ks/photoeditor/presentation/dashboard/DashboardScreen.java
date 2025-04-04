package org.ks.photoeditor.presentation.dashboard;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.ks.photoeditor.PEImage;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.utils.Res;

public class DashboardScreen extends JDialog {

  private static final String PLUS_IC = "plus_ic.png";
  private static final String EXAMPLE = "example_1.jpg";
  private static final String EXAMPLE_2 = "example_2.png";
  private final PhotoSourceRepository userRepository;
  private Consumer<Void> onImageSelected;

  public DashboardScreen(PhotoSourceRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void run(Consumer<Void> onImageSelected) {
    this.onImageSelected = onImageSelected;
    setLayout(new BorderLayout());
    JScrollPane scrollPane = createImageGridScrollPane(this::handleButtonClick);
    add(scrollPane, BorderLayout.NORTH);
    setTitle("PhotoEditor");
    setSize(600, 400);
    setLocationRelativeTo(null);
    setModal(true);
    setVisible(true);
  }

  public void setIsVisible(Boolean isVisible) {
    setVisible(isVisible);
  }

  private JScrollPane createImageGridScrollPane(Consumer<PEImage> onButtonClicked) {
    return new JScrollPane(
        createButtonGrid(onButtonClicked),
        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
  }

  private JPanel createButtonGrid(Consumer<PEImage> onButtonClicked) {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(0, 3, 2, 2));
    panel.setBackground(null);
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));

    List<PEImage> icons = loadIcons();
    List<JButton> buttons = createJButtons(icons, onButtonClicked);

    for (JButton button : buttons) {
      panel.add(button);
    }

    panel.setPreferredSize(new Dimension(100, 100));
    panel.setMaximumSize(new Dimension(100, 100));
    panel.setMinimumSize(new Dimension(100, 100));
    return panel;
  }

  private List<PEImage> loadIcons() {
    ImageIcon icon = new ImageIcon(Objects.requireNonNull(Res.getIcResourcePath(PLUS_IC)));
    ImageIcon image_1 = new ImageIcon(Objects.requireNonNull(Res.getImageResourcePath(EXAMPLE)));
    ImageIcon image_2 = new ImageIcon(Objects.requireNonNull(Res.getImageResourcePath(EXAMPLE_2)));
    return List.of(
        new PEImage(UUID.randomUUID(), icon, icon),
        new PEImage(UUID.randomUUID(), null, image_1),
        new PEImage(UUID.randomUUID(), null, image_2));
  }

  private List<JButton> createJButtons(List<PEImage> icons, Consumer<PEImage> onButtonClicked) {
    return icons.stream().map(icon -> createButton(icon, onButtonClicked)).toList();
  }

  private JButton createButton(PEImage icon, Consumer<PEImage> onButtonClicked) {
    Image imageToScale;
    if (icon.image() != null) {
      imageToScale = icon.image().getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    } else {
      imageToScale = icon.thumbnail().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    }

    JButton button = new JButton(new ImageIcon(imageToScale));

    button.addActionListener(
        e -> {
          System.out.println(icon.id());
          onButtonClicked.accept(icon);
        });
    button.setPreferredSize(new Dimension(100, 100));
    button.setMaximumSize(new Dimension(100, 100));
    button.setMinimumSize(new Dimension(100, 100));
    return button;
  }

  private void handleButtonClick(PEImage peImage) {
    if (peImage.thumbnail().getImage()
        == new ImageIcon(Objects.requireNonNull(Res.getIcResourcePath(PLUS_IC))).getImage()) {
      uploadImage(onImageSelected);
    } else {
      try {
        URL imageUrl = Res.getImageResourcePath(EXAMPLE);
        BufferedImage image = ImageIO.read(imageUrl);

        File tempFile = File.createTempFile("tempImage", ".png");
        ImageIO.write(image, "png", tempFile);
        if (userRepository.loadedNewPhoto(tempFile)) {
          onImageSelected.accept(null);
          setVisible(false);
        }
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Błąd podczas ładowania obrazu: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  private void uploadImage(Consumer<Void> onImageSelected) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(
        new javax.swing.filechooser.FileNameExtensionFilter("Obrazy (JPG, PNG)", "jpg", "png"));

    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      if (userRepository.loadedNewPhoto(file)) {
        onImageSelected.accept(null);
        setVisible(false);
      }
    }
  }
}
