package org.ks.photoeditor.utils;


import java.net.URL;

public class Res {
    public static URL getImageResourcePath(String resourceName) {
        final URL resource = Res.class.getResource("/images/" + resourceName);
        if (resource == null) {
            System.err.println("Błąd: Nie znaleziono pliku " + resourceName);
            return null;
        }
        return resource;
    }
}