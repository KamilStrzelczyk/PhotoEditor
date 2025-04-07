package org.ks.photoeditor.presentation.dashboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import org.ks.photoeditor.PEImage;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.utils.Res;

public class DashboardScreen extends JDialog {

  private static final String PLUS_IC = "plus_ic.png";
  private static final String EXAMPLE = "example_1.jpg";
  private static final String EXAMPLE_2 = "example_2.png";
  private final PhotoSourceRepository userRepository;

  public DashboardScreen(PhotoSourceRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void run() {
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

    panel.setPreferredSize(new Dimension(300, 300));
    return panel;
  }

  private List<PEImage> loadIcons() {
    ImageIcon plusIcon = new ImageIcon(Objects.requireNonNull(Res.getIcResourcePath(PLUS_IC)));

    URL urlExample1 = Res.getImageResourcePath(EXAMPLE);
    URL urlExample2 = Res.getImageResourcePath(EXAMPLE_2);
    ImageIcon imageExample1 = new ImageIcon(urlExample1);
    ImageIcon imageExample2 = new ImageIcon(urlExample2);

    return List.of(
            new PEImage(UUID.randomUUID(), plusIcon, null),
            new PEImage(UUID.randomUUID(), imageExample1, urlExample1),
            new PEImage(UUID.randomUUID(), imageExample2, urlExample2)
    );
  }

  private List<JButton> createJButtons(List<PEImage> icons, Consumer<PEImage> onButtonClicked) {
    return icons.stream().map(icon -> createButton(icon, onButtonClicked)).toList();
  }

  private JButton createButton(PEImage peImage, Consumer<PEImage> onButtonClicked) {
    Image imageToScale = peImage.image().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    JButton button = new JButton(new ImageIcon(imageToScale));
    button.addActionListener(e -> {
      System.out.println(peImage.id());
      onButtonClicked.accept(peImage);
    });
    button.setPreferredSize(new Dimension(100, 100));
    return button;
  }

  private void handleButtonClick(PEImage peImage) {
    if (peImage.url() == null) {
      uploadImage();
    } else {
      try {
        BufferedImage image = ImageIO.read(peImage.url());
        File tempFile = File.createTempFile("tempImage", ".png");
        ImageIO.write(image, "png", tempFile);
        if (userRepository.loadedNewPhoto(tempFile)) {
          setVisible(false);
        }
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Błąd podczas ładowania obrazu: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  private void uploadImage() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Obrazy (JPG, PNG)", "jpg", "png"));
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      if (userRepository.loadedNewPhoto(file)) {
        setVisible(false);
      }
    }
  }
}
