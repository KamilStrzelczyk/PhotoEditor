package org.ks.photoeditor.presentation.editorscreen;

import org.ks.photoeditor.presentation.editorscreen.component.TopBarAction;

public class EditorViewModelState {
  private boolean isImageCropperVisible = false;
  private boolean isEffectsSelected = false;
  private boolean isLightSelected = false;
  private boolean isCancelled = false;
  private boolean isUndoButtonActive = false;
  private boolean isSaveButtonActive = false;

  private void resetStates() {
    this.isImageCropperVisible = false;
    this.isEffectsSelected = false;
    this.isLightSelected = false;
    this.isCancelled = false;
  }

  public void setImageCropperVisible() {
    resetStates();
    this.isImageCropperVisible = true;
  }

  public void keepTrimVisible(TopBarAction action) {
    if (action == TopBarAction.CANCEL_CLICKED) return;
    if (action != TopBarAction.TRIM_CLICKED) {
      this.isImageCropperVisible = false;
    }
  }

  public void setCancelled() {
    resetStates();
    this.isCancelled = true;
  }

  public boolean isImageCropperVisible() {
    return isImageCropperVisible;
  }

  public boolean isCancelled() {
    return isCancelled;
  }

  public boolean isEffectsSelected() {
    return isEffectsSelected;
  }

  public boolean isLightSelected() {
    return isLightSelected;
  }

  public boolean isUndoButtonActive() {
    return isUndoButtonActive;
  }

  public void setUndoButtonActive(Boolean canUndo) {
    this.isUndoButtonActive = canUndo;
  }

  public void setFiltersSelected() {
    resetStates();
    this.isEffectsSelected = true;
  }

  public void setSaveButtonActive(Boolean isActive) {
    this.isSaveButtonActive = isActive;
  }

  public boolean isSaveButtonActive() {
    return isSaveButtonActive;
  }

  public void setLightSelected() {
    resetStates();
    this.isLightSelected = true;
  }
}
