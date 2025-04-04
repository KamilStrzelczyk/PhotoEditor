package org.ks.photoeditor.presentation.editorscreen;

import org.ks.photoeditor.presentation.editorscreen.component.TopBarAction;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetGrayscaleEffectUseCase;
import org.ks.photoeditor.usecase.SetImageBlurUseCase;
import org.ks.photoeditor.usecase.SetImagePositionUseCase;

public class EditorScreenViewModel {
    public PhotoSourceRepository photoSourceRepository;
    SetImageBlurUseCase setImageBlur;
    SetGrayscaleEffectUseCase setGrayscaleEffect;
    SetImagePositionUseCase setImagePosition;

    public EditorScreenViewModel(
            PhotoSourceRepository photoSourceRepository,
            SetImageBlurUseCase setImageBlur,
            SetGrayscaleEffectUseCase setGrayscaleEffect,
            SetImagePositionUseCase setImagePosition
    ) {
        this.photoSourceRepository = photoSourceRepository;
        this.setImageBlur = setImageBlur;
        this.setGrayscaleEffect = setGrayscaleEffect;
        this.setImagePosition = setImagePosition;
    }

    public void onTopBarActionClicked(TopBarAction action) {
        switch (action) {
            case BLUR_CLICKED:
                setImageBlur.applyBlur(5);
                break;
            case CORRECTION_CLICKED:
                setGrayscaleEffect.run();
                break;
            case FILTERS_CLICKED:
                // Handle filters action
                break;
            case TRIM_CLICKED:
                // Handle trim action
                break;
            case RESET_CLICKED:
                photoSourceRepository.revertPhoto();
                break;
            case SAVE_CLICKED:
                // Handle save action
                break;
            case CANCEL_CLICKED:
                // Handle cancel action
                break;
        }
    }

    public void onRotateClickedLeft() {
        setImagePosition.rotateLeft();
    }

    public void onRotateClickedRight() {
        setImagePosition.rotateRight();
    }
}
