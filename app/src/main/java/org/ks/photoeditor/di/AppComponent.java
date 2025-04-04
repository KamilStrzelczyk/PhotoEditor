package org.ks.photoeditor.di;

import dagger.Component;
import javax.inject.Singleton;
import org.ks.photoeditor.presentation.editorscreen.EditorScreen;
import org.ks.photoeditor.presentation.editorscreen.EditorScreenViewModel;
import org.ks.photoeditor.repository.PhotoSourceRepository;
import org.ks.photoeditor.usecase.SetGrayscaleEffectUseCase;
import org.ks.photoeditor.usecase.SetImageBlurUseCase;
import org.ks.photoeditor.usecase.SetImagePositionUseCase;
import org.ks.photoeditor.usecase.SetTrimOnImageUseCase;

@Singleton
@Component(modules = {InfrastructureModule.class, DomainModule.class, EditorModule.class})
public interface AppComponent {
  PhotoSourceRepository getPhotoSourceRepository();

  EditorScreenViewModel getEditorScreenViewModel();

  SetImageBlurUseCase getSetImageBlurUseCase();

  SetGrayscaleEffectUseCase getSetGrayscaleEffectUseCase();

  SetImagePositionUseCase getSetImagePositionUseCase();

  SetTrimOnImageUseCase getSetTrimOnImageUseCase();

  EditorScreen getEditorScreen();
}
