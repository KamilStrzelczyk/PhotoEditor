package org.ks.photoeditor.di;

import dagger.Module;
import dagger.Provides;
import org.ks.photoeditor.presentation.editorscreen.EditorScreenViewModel;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetGrayscaleEffectUseCase;
import org.ks.photoeditor.usecase.SetImageBlurUseCase;
import org.ks.photoeditor.usecase.SetImagePositionUseCase;

@Module
public class EditorModule {

    @Provides
    EditorScreenViewModel provideEditorScreenViewModel(
            PhotoSourceRepository photoSourceRepository,
            SetImageBlurUseCase setImageBlurUseCase,
            SetGrayscaleEffectUseCase setGrayscaleEffectUseCase,
            SetImagePositionUseCase setImagePositionUseCase) {
        return new EditorScreenViewModel(
                photoSourceRepository,
                setImageBlurUseCase,
                setGrayscaleEffectUseCase,
                setImagePositionUseCase
        );
    }
}
