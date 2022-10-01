package com.molitics.molitician;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(MolticsApplication app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(MolticsApplication application);

        AppComponent build();
    }
}
