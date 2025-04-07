package org.ks.photoeditor.utils;

import java.net.URL;

public class Res {
  private static URL getResourcePath(String folder, String resourceName) {
    final URL resource = Res.class.getResource("/" + folder + "/" + resourceName);
    if (resource == null) {
      System.err.println("Błąd: Nie znaleziono pliku " + resourceName + " w katalogu " + folder);
      return null;
    }
    return resource;
  }

  public static URL getImageResourcePath(String resourceName) {
    return getResourcePath("images", resourceName);
  }

  public static URL getIcResourcePath(String resourceName) {
    return getResourcePath("ic", resourceName);
  }
}
