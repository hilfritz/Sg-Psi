package com.hilfritz.mvp.ui.psi;

import com.hilfritz.mvp.api.psi.pojo.Item;
import com.hilfritz.mvp.api.psi.pojo.PsiPojo;

import rx.Observable;
import rx.Scheduler;

/**
 * @author Hilfritz Camallere on {$Date}
 */
public interface PsiMapContract {

    public interface Presenter{
        void init(View view, Model model, Scheduler bgThread, Scheduler mainThread);
        void populate();
        void destroy();
        String getReadingsPerRegionMetaData(PsiPojo psiPojo, String regionName);
        String getReadingsByRegionName(String regionName, Item item);
    }

    public interface Model{
        Observable<PsiPojo> getAllPsi();
    }

    public interface View{
        void showLoading();
        void hideLoading();
        void showDialogWithMessage(String str);
        void showMapWithData(PsiPojo psiPojo);
        String getStringFromStringResId(int stringResID);
        void showTitle(String str);
        void showTimings(String date, String lastUpdate);
    }
}
