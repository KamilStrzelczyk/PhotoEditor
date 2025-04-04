package org.ks.photoeditor.di;

import dagger.Module;
import dagger.Provides;
import org.ks.photoeditor.presentation.editorscreen.EditorScreen;
import org.ks.photoeditor.presentation.editorscreen.EditorScreenViewModel;
import org.ks.photoeditor.presentation.editorscreen.component.ImageDisplay;
import org.ks.photoeditor.presentation.imagecropper.ImageCropper;
import org.ks.photoeditor.presentation.imagecropper.ImageCropperViewModel;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetGrayscaleEffectUseCase;
import org.ks.photoeditor.usecase.SetImageBlurUseCase;
import org.ks.photoeditor.usecase.SetImagePositionUseCase;
import org.ks.photoeditor.usecase.SetTrimOnImageUseCase;

@Module
public class EditorModule {

    @Provides
    EditorScreenViewModel provideEditorScreenViewModel(
            PhotoSourceRepository photoSourceRepository,
            SetImageBlurUseCase setImageBlurUseCase,
            SetGrayscaleEffectUseCase setGrayscaleEffectUseCase,
            SetImagePositionUseCase setImagePositionUseCase,
            SetTrimOnImageUseCase setTrimOnImageUseCase) {
        return new EditorScreenViewModel(
                photoSourceRepository,
                setImageBlurUseCase,
                setGrayscaleEffectUseCase,
                setImagePositionUseCase,
                setTrimOnImageUseCase
        );
    }

    @Provides
    ImageCropperViewModel provideImageCropperViewModel(PhotoSourceRepository photoSourceRepository, SetTrimOnImageUseCase setTrimOnImageUseCase) {
        return new ImageCropperViewModel(photoSourceRepository,setTrimOnImageUseCase);
    }

    @Provides
    EditorScreen provideEditorScreen(
            EditorScreenViewModel editorScreenViewModel,
            ImageDisplay imageDisplay) {
        return new EditorScreen(editorScreenViewModel, imageDisplay);
    }

    @Provides
    ImageCropper provideImageCropper(
            ImageCropperViewModel imageCropperViewModel) {
        return new ImageCropper(imageCropperViewModel);
    }
}
