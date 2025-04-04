package org.ks.photoeditor.presentation.editorscreen;

import org.ks.photoeditor.presentation.editorscreen.component.TopBarAction;

public class EditorViewModelState {
    private boolean isImageCropperVisible = false;
    private boolean isBlurApplied = false;
    private boolean isGrayscaleApplied = false;
    private boolean isFiltersApplied = false;
    private boolean isPhotoReset = false;
    private boolean isPhotoSaved = false;
    private boolean isCancelled = false;

    private void resetStates() {
        this.isImageCropperVisible = false;
        this.isBlurApplied = false;
        this.isGrayscaleApplied = false;
        this.isFiltersApplied = false;
        this.isPhotoReset = false;
        this.isPhotoSaved = false;
        this.isCancelled = false;
    }

    public void setImageCropperVisible() {
        resetStates();
        this.isImageCropperVisible = true;
    }

    public void keepTrimVisible(TopBarAction action) {
        if (action != TopBarAction.TRIM_CLICKED) {
            resetStates();
        }
    }
    public boolean isImageCropperVisible() {
        return isImageCropperVisible;
    }
}
