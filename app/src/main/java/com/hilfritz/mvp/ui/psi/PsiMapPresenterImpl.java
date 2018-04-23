package com.hilfritz.mvp.ui.psi;

import com.google.gson.Gson;
import com.hilfritz.mvp.R;
import com.hilfritz.mvp.api.psi.pojo.Item;
import com.hilfritz.mvp.api.psi.pojo.PsiPojo;
import com.hilfritz.mvp.api.psi.pojo.RegionMetadatum;
import com.hilfritz.mvp.util.RxUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author Hilfritz Camallere on 21/4/18
 */
public class PsiMapPresenterImpl implements PsiMapContract.Presenter {
    PsiMapContract.View view;
    PsiMapContract.Model model;
    Scheduler bgThread, mainThread;
    Subscription getAllPsiSubscription;
    Gson gson = new Gson();

    @Override
    public void init(PsiMapContract.View view, PsiMapContract.Model model, Scheduler bgThread, Scheduler mainThread) {
        this.view = view;
        this.model = model;
        this.bgThread = bgThread;
        this.mainThread = mainThread;
    }

    @Override
    public void populate() {
        RxUtil.unsubscribe(getAllPsiSubscription);
        //1. SHOW LOADING
        view.showLoading();
        //2. CALL PSI DATA FROM API
        getAllPsiSubscription = model.getAllPsi()
                .flatMap(new Func1<PsiPojo, Observable<PsiPojo>>() {
                    @Override
                    public Observable<PsiPojo> call(PsiPojo psiPojo) {
                        //GENERATE THE SNIPPETS FOR THE MAP, ARRANGE THE DATA
                        if (psiPojo!=null && psiPojo.getRegionMetadata()!=null && psiPojo.getRegionMetadata().size()>0){
                            int size = psiPojo.getRegionMetadata().size();
                            for (int x=0; x<size;x++){
                                RegionMetadatum regionMetadatum = psiPojo.getRegionMetadata().get(x);
                                String regionName = regionMetadatum.getName();
                                String snippet = getReadingsPerRegionMetaData(psiPojo, regionName);
                                regionMetadatum.setMapSnippet(snippet);
                            }
                        }
                        return Observable.just(psiPojo);
                    }
                })
                .observeOn(mainThread)
                .subscribeOn(bgThread)
                .subscribe(new Subscriber<PsiPojo>() {
                    @Override
                    public void onCompleted() {
                        view.hideLoading();
                        RxUtil.unsubscribe(getAllPsiSubscription);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.hideLoading();
                        view.showDialogWithMessage(" Error:\n"+e.getLocalizedMessage()+" \n"+view.getStringFromStringResId(R.string.something_went_wrong));
                        e.printStackTrace();
                        RxUtil.unsubscribe(getAllPsiSubscription);
                    }

                    @Override
                    public void onNext(PsiPojo psiPojo) {
                        if (psiPojo.getRegionMetadata()==null || psiPojo.getRegionMetadata().size()==0){
                            view. showDialogWithMessage(view.getStringFromStringResId(R.string.not_available_region_data));
                            return;
                        }

                        //4. CHECK PSI DATA
                        if (psiPojo==null){
                            //4.1 CHECK PSI DATA - IF WRONG DATA, SHOW ERROR MESSAGE
                            view.showDialogWithMessage(view.getStringFromStringResId(R.string.something_went_wrong));
                            return;
                        }

                        view.showMapWithData(psiPojo);
                    }
                });
    }

    public String getReadingsPerRegionMetaData(PsiPojo psiPojo, String regionName){
        String retVal = "";
        List<Item> items = psiPojo.getItems();
        int itemSize = 0;
        if (items!=null && items.size()>0){
            itemSize = items.size();
            for (int y = 0; y < itemSize; y++){
                Item item = items.get(y);
                retVal+=getReadingsByRegionName(regionName, item);
            }
        }
        return retVal;
    }

    public String getReadingsByRegionName(String regionName, Item item){
        String retVal = "";
        String s = gson.toJson(item);
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject readingsObject = jsonObject.getJSONObject("readings");
            int readingsCount = readingsObject.names().length();
            for(int i = 0; i<readingsCount; i++){
                String key = readingsObject.names().getString(i);//THIS IS THE TOP OBJECT THAT CONTAIONS THE west:100, east:1,...

                JSONObject readingItems = readingsObject.getJSONObject(key);
                int counter = readingItems.names().length();

                for(int j = 0; j < counter; j++){

                    String variableName = readingItems.names().getString(j);
                    if (variableName.equalsIgnoreCase(regionName)){
                        String value = readingItems.get(regionName).toString();
                        retVal+="\n"+key+": "+value;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void destroy() {
        RxUtil.unsubscribe(getAllPsiSubscription);
    }
}
