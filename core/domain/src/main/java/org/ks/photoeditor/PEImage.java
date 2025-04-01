package org.ks.photoeditor;

import javax.swing.*;

public record PEImage(
        int id,
        ImageIcon image,
        ImageIcon thumbnail
) {
}