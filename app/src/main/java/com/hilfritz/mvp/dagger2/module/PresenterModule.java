package com.hilfritz.mvp.dagger2.module;

import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.ui.psi.PsiMapContract;
import com.hilfritz.mvp.ui.psi.PsiMapPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hilfritz P. Camallere on 6/28/2016.
 */
@Module
public class PresenterModule {


    private final MyApplication myApplication;

    public PresenterModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    //ACTIVITY HERE
    @Provides
    @Singleton
    PsiMapContract.Presenter providePsiMapPresenter(){
        return new PsiMapPresenterImpl();
    }
}
