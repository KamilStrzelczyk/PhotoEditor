package org.ks.photoeditor.presentation.editorscreen;

import org.ks.photoeditor.presentation.editorscreen.component.TopBarAction;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetGrayscaleEffectUseCase;
import org.ks.photoeditor.usecase.SetImageBlurUseCase;
import org.ks.photoeditor.usecase.SetImagePositionUseCase;
import org.ks.photoeditor.usecase.SetTrimOnImageUseCase;

import javax.inject.Inject;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EditorScreenViewModel {
    public static String STATE = "state";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public EditorViewModelState state = new EditorViewModelState();

    public PhotoSourceRepository photoSourceRepository;
    SetImageBlurUseCase setImageBlur;
    SetGrayscaleEffectUseCase setGrayscaleEffect;
    SetImagePositionUseCase setImagePosition;
    SetTrimOnImageUseCase setTrimOnImage;

    @Inject
    public EditorScreenViewModel(
            PhotoSourceRepository photoSourceRepository,
            SetImageBlurUseCase setImageBlur,
            SetGrayscaleEffectUseCase setGrayscaleEffect,
            SetImagePositionUseCase setImagePosition,
            SetTrimOnImageUseCase setTrimOnImage
    ) {
        this.photoSourceRepository = photoSourceRepository;
        this.setImageBlur = setImageBlur;
        this.setGrayscaleEffect = setGrayscaleEffect;
        this.setImagePosition = setImagePosition;
        this.setTrimOnImage = setTrimOnImage;
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
                state.setImageCropperVisible();
                break;
            case RESET_CLICKED:
                photoSourceRepository.revertPhoto();
                break;
            case SAVE_CLICKED:
                // Handle save action
                break;
            case CANCEL_CLICKED:
                photoSourceRepository.clear();
                state.setCancelled();
                break;
        }
        state.keepTrimVisible(action);
        support.firePropertyChange(STATE, null, state);
    }

    public void onRotateClickedLeft() {
        System.out.println("onRotateClickedLeft");
        setImagePosition.rotateLeft();
    }

    public void onTrimButtonClicked() {
        System.out.println("onTrimButtonClicked");
        setTrimOnImage.run();
    }

    public void onRotateClickedRight() {
        System.out.println("onRotateClickedRight");
        setImagePosition.rotateRight();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
