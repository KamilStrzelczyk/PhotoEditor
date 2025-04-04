package org.ks.photoeditor.di;

import dagger.Module;
import dagger.Provides;
import org.ks.photoeditor.presentation.editorscreen.EditorScreen;
import org.ks.photoeditor.presentation.editorscreen.EditorScreenViewModel;
import org.ks.photoeditor.presentation.editorscreen.component.ImageDisplay;
import org.ks.photoeditor.presentation.editorscreen.effectsidepanel.EffectSidePanel;
import org.ks.photoeditor.presentation.editorscreen.effectsidepanel.SidePanelViewModel;
import org.ks.photoeditor.presentation.imagecropper.ImageCropper;
import org.ks.photoeditor.presentation.imagecropper.ImageCropperViewModel;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.*;

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
        return new ImageCropperViewModel(photoSourceRepository, setTrimOnImageUseCase);
    }


    @Provides
    SidePanelViewModel provideSidePanelViewModel(PhotoSourceRepository photoSourceRepository,
                                                 SetSepiaEffectUseCase setSepiaEffectUseCase,
                                                 SetNegativeEffectUseCase setNegativeEffectUseCase,
                                                 SetGrayscaleEffectUseCase setGrayscaleEffectUseCase) {
        return new SidePanelViewModel(photoSourceRepository,
                setSepiaEffectUseCase,
                setNegativeEffectUseCase,
                setGrayscaleEffectUseCase);
    }

    @Provides
    EditorScreen provideEditorScreen(
            EditorScreenViewModel editorScreenViewModel,
            EffectSidePanel sidePanel,
            ImageDisplay imageDisplay) {
        return new EditorScreen(editorScreenViewModel, sidePanel, imageDisplay);
    }

    @Provides
    ImageCropper provideImageCropper(
            ImageCropperViewModel imageCropperViewModel) {
        return new ImageCropper(imageCropperViewModel);
    }

    @Provides
    EffectSidePanel provideSidePanel(SidePanelViewModel sidePanelViewModel) {
        return new EffectSidePanel(sidePanelViewModel);
    }
}
