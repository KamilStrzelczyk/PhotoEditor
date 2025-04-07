package org.ks.photoeditor.di;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.ks.photoeditor.reposiotry.PhotoSourceRepositoryImpl;
import org.ks.photoeditor.repository.PhotoSourceRepository;

@Module
public class InfrastructureModule {

  @Singleton
  @Provides
  PhotoSourceRepository provideUserRepository() {
    return new PhotoSourceRepositoryImpl();
  }
}
