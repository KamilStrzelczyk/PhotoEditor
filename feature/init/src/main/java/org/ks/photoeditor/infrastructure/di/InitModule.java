package org.ks.photoeditor.infrastructure.di;

import dagger.Module;
import dagger.Provides;
import org.ks.photoeditor.presentation.dashboard.DashboardScreen;
import org.ks.photoeditor.repository.PhotoSourceRepository;

@Module
public class InitModule {

    @Provides
    DashboardScreen getDashboardScreen(PhotoSourceRepository repository) {
        return new DashboardScreen(repository);
    }
}
