package org.ks.photoeditor.di;

import dagger.Module;
import dagger.Provides;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.*;

import javax.inject.Singleton;

@Module
public class DomainModule {

    @Provides
    SetImageBlurUseCase provideSetImageBlurUseCase(PhotoSourceRepository photoSourceRepository) {
        return new SetImageBlurUseCase(photoSourceRepository);
    }

    @Provides
    SetGrayscaleEffectUseCase provideSetGrayscaleEffectUseCase(PhotoSourceRepository photoSourceRepository) {
        return new SetGrayscaleEffectUseCase(photoSourceRepository);
    }

    @Provides
    SetImagePositionUseCase provideSetImagePositionUseCase(PhotoSourceRepository photoSourceRepository) {
        return new SetImagePositionUseCase(photoSourceRepository);
    }

    @Provides
    SetContrastEffectUseCase provideSetContrastEffectUseCase(PhotoSourceRepository photoSourceRepository) {
        return new SetContrastEffectUseCase(photoSourceRepository);
    }

    @Singleton
    @Provides
    SetTrimOnImageUseCase provideSetTrimOnImageUseCase(PhotoSourceRepository photoSourceRepository) {
        return new SetTrimOnImageUseCase(photoSourceRepository);
    }
}
