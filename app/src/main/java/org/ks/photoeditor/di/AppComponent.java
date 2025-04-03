package org.ks.photoeditor.di;

import dagger.Component;
import javax.inject.Singleton;
import org.ks.photoeditor.repository.PhotoSourceRepository;

@Singleton
@Component(modules = {InfrastructureModule.class})
public interface AppComponent {
  PhotoSourceRepository getPhotoSourceRepository();
}
