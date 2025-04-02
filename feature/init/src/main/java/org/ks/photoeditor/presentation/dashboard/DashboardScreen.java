package org.ks.photoeditor.presentation.dashboard;

import org.ks.photoeditor.EditorScreen;
import org.ks.photoeditor.PEImage;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.utils.Res;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class DashboardScreen extends JPanel {

    private static final String PLUS_IC = "plus_ic.png";
    private final PhotoSourceRepository userRepository;

    @Inject
    public DashboardScreen(PhotoSourceRepository userRepository) {
        this.userRepository = userRepository;
        setLayout(new BorderLayout());
        JScrollPane scrollPane = createImageGridScrollPane(e -> uploadImage());
        add(scrollPane, BorderLayout.NORTH);
    }


    private JScrollPane createImageGridScrollPane(Consumer<PEImage> onButtonClicked) {
        return new JScrollPane(createButtonGrid(onButtonClicked), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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
        return List.of(new PEImage(UUID.randomUUID(), icon, icon), new PEImage(UUID.randomUUID(), icon, icon), new PEImage(UUID.randomUUID(), icon, icon));
    }

    private List<JButton> createJButtons(List<PEImage> icons, Consumer<PEImage> onButtonClicked) {
        return icons.stream().map(icon -> createButtonForIcon(icon, onButtonClicked)).toList();
    }

    private JButton createButtonForIcon(PEImage icon, Consumer<PEImage> onButtonClicked) {
        JButton button = new JButton(new ImageIcon(icon.thumbnail().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));

        button.addActionListener(e -> {
            System.out.println(icon.id());
            onButtonClicked.accept(icon);
        });

        button.setPreferredSize(new Dimension(100, 100));
        button.setMaximumSize(new Dimension(100, 100));
        button.setMinimumSize(new Dimension(100, 100));
        return button;
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Obrazy (JPG, PNG)", "jpg", "png"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (userRepository.loadedNewPhoto(file)) {
                new EditorScreen(userRepository);
            }
        }
    }
}
