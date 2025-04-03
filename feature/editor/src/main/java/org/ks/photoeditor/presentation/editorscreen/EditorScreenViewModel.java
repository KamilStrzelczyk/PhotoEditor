package org.ks.photoeditor.presentation.editorscreen;

import org.ks.photoeditor.presentation.editorscreen.component.TopBarAction;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetGrayscaleEffectUseCase;
import org.ks.photoeditor.usecase.SetImageBlurUseCase;

public class EditorScreenViewModel {
    public PhotoSourceRepository userRepository;
    SetImageBlurUseCase setImageBlur;
    SetGrayscaleEffectUseCase setGrayscaleEffect;

    public EditorScreenViewModel(PhotoSourceRepository userRepository, SetImageBlurUseCase setImageBlur, SetGrayscaleEffectUseCase setGrayscaleEffect) {
        this.userRepository = userRepository;
        this.setImageBlur = setImageBlur;
        this.setGrayscaleEffect = setGrayscaleEffect;
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
                // Handle reset action
                break;
            case SAVE_CLICKED:
                // Handle save action
                break;
            case CANCEL_CLICKED:
                // Handle cancel action
                break;
        }
    }
}
