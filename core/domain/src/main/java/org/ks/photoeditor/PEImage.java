package org.ks.photoeditor;

import javax.swing.*;
import java.util.UUID;

public record PEImage(
        UUID id,
        ImageIcon image,
        ImageIcon thumbnail
) {
}