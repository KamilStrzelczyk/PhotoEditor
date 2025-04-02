package org.ks.photoeditor.di;

import dagger.Component;
import javax.inject.Singleton;
import org.ks.photoeditor.infrastructure.di.InitModule;
import org.ks.photoeditor.presentation.dashboard.DashboardScreen;
import org.ks.photoeditor.repository.PhotoSourceRepository;

@Singleton
@Component(modules = {InfrastructureModule.class, InitModule.class})
public interface AppComponent {
  PhotoSourceRepository getPhotoSourceRepository();

  DashboardScreen getDashboardScreen();
}
