package org.ks.photoeditor.di;

import dagger.Component;
import javax.inject.Singleton;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetGrayscaleEffectUseCase;
import org.ks.photoeditor.usecase.SetImageBlurUseCase;

@Singleton
@Component(modules = {InfrastructureModule.class, DomainModule.class})
public interface AppComponent {
  PhotoSourceRepository getPhotoSourceRepository();

  SetImageBlurUseCase getSetImageBlurUseCase();

  SetGrayscaleEffectUseCase getSetGrayscaleEffectUseCase();
}
