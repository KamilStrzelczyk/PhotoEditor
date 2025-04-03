package org.ks.photoeditor.di;

import dagger.Module;
import dagger.Provides;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetImageBlurUseCase;

@Module
public class DomainModule {

    @Provides
    SetImageBlurUseCase provideSetImageBlurUseCase(PhotoSourceRepository photoSourceRepository) {
        return new SetImageBlurUseCase(photoSourceRepository);
    }
}
