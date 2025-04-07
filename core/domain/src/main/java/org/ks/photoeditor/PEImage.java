package org.ks.photoeditor;

import javax.swing.*;
import java.net.URL;
import java.util.UUID;

public record PEImage(
        UUID id,
        ImageIcon image,
        URL url
) {
}
