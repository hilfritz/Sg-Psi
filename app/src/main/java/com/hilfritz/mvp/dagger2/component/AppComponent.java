package com.hilfritz.mvp.dagger2.component;

import com.hilfritz.mvp.dagger2.module.CacheModule;
import com.hilfritz.mvp.dagger2.module.PresenterModule;
import com.hilfritz.mvp.dagger2.module.RestApiModule;
import com.hilfritz.mvp.dagger2.module.SessionModule;
import com.hilfritz.mvp.dagger2.module.UtilityModule;
import com.hilfritz.mvp.framework.BaseActivity;
import com.hilfritz.mvp.ui.dialog.SimpleDialog;
import com.hilfritz.mvp.ui.psi.PsiMapActivityFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Hilfritz P. Camallere on 6/4/2016.
 */
@Singleton
@Component(modules = {
        RestApiModule.class,
        PresenterModule.class,
        UtilityModule.class,
        SessionModule.class,
        CacheModule.class
})
public interface AppComponent {
    void inject(BaseActivity baseActivity);
    void inject(SimpleDialog simpleDialog);
    void inject(PsiMapActivityFragment simpleDialog);

    //void inject(ImageCache imageCache);
    //void inject(LogFileManager logFileManager);

}
