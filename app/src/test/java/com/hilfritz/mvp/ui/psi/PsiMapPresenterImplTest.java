package com.hilfritz.mvp.ui.psi;

import android.util.Log;

import com.google.gson.Gson;
import com.hilfritz.mvp.api.psi.pojo.PsiPojo;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Hilfritz Camallere on 21/4/18
 * common error: https://github.com/robolectric/robolectric/issues/2949
 * http://www.vogella.com/tutorials/Mockito/article.html
 */
public class PsiMapPresenterImplTest {

    private static final String TAG = "PsiMapPresenterImplTest";
    PsiMapPresenterImpl presenter;
    PsiMapContract.View view;
    PsiMapContract.Model model;
    Gson gson;
    Scheduler bgThread, mainThread ;

    @Before
    public void before() {
        gson = new Gson();
        //MOCKS THE subscribeOn(Schedulers.io()) to use the same thread the test is being run on
        //Schedulers.trampoline() runs the test in the same thread used by the test
        RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.trampoline();
            }
        });

        //MOCKS
        view = mock (PsiMapContract.View.class);
        model = mock(PsiMapContract.Model.class);
        bgThread = Schedulers.io();
        mainThread = bgThread;

    }

    @After
    public void after() {
        RxJavaHooks.reset();
    }
    @Test
    public void sampleTest(){
        assertEquals(1,1);
    }

    @Test
    public void loadPsiDataSuccess(){

        //>>>> CONDITIONS
        presenter = new PsiMapPresenterImpl();
        when(view.getStringFromStringResId(anyInt())).thenReturn("");
        final PsiPojo sampleJsonPojoTemp = getSampleJsonPojo();
        final Observable<PsiPojo> sampleJsonPojo= Observable.just(sampleJsonPojoTemp);

        //alternative 1
        when (model.getAllPsi()).thenReturn(sampleJsonPojo);
        //alternative 2

//        model = new PsiMapContract.Model() {
//            @Override
//            public Observable<PsiPojo> getAllPsi() {
//                return sampleJsonPojo;
//            }
//        };


        //>>>> ACTION
        presenter.init(view,model,bgThread,mainThread);
        presenter.populate();

        //>>>>> VERIFY
        verify(view,times(1)).showLoading();    //LOADING WAS ONLY SHOWN 1
        verify(view,times(1)).hideLoading();    //HIDE LOADING WAS ONLY SHOWN 1
        verify(view, never()).showDialogWithMessage(anyString());   //MAKE SURE ERROR DIALOG WAS NOT CALLED
        verify(view, times(1)).showMapWithData(sampleJsonPojoTemp);

    }

    @Test
    public void loadPsiDataFail(){
        //>>>> CONDITIONS
        presenter = new PsiMapPresenterImpl();
        when(view.getStringFromStringResId(anyInt())).thenReturn("errorMessage");
        Observable<PsiPojo> empty = Observable.<PsiPojo>empty();
        //alternative 1
        final Exception exception = new Exception("somewthing wrong");
        final Throwable throwable = new Throwable(exception);
        final Observable<PsiPojo> error = Observable.<PsiPojo>error(throwable);

        when(model.getAllPsi()).thenReturn(error);
        //when (model.getAllPsi()).thenReturn(empty);
        //alternative 2
        /*
        model = new PsiMapContract.Model() {
            @Override
            public PsiPojo getAllPsi() {
                return null;
            }
        };
        */

        //>>>> ACTION
        presenter.init(view,model,bgThread,mainThread);
        presenter.populate();

        //>>>>> VERIFY
        verify(view,times(1)).showLoading();    //LOADING WAS ONLY SHOWN 1
        verify(view,times(1)).hideLoading();    //HIDE LOADING WAS ONLY SHOWN 1
        verify(view, times(1)).showDialogWithMessage("errorMessage");   //MAKE SURE ERROR DIALOG WAS CALLED
        verify(view, never()).showMapWithData((PsiPojo) any()); //MADE SURE SHOW MAP DATA WAS NOT CALLED

    }

    public PsiPojo getSampleJsonPojo(){
        return gson.fromJson(getSampleJsonObject().toString(), PsiPojo.class);
    }

    public JSONObject getSampleJsonObject(){
        JSONObject retVal = null;
        StringBuilder text = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("raw/sample.json"));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            retVal = new JSONObject(text.toString());
        } catch (Exception e) {
            e.printStackTrace();
            // do exception handling
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retVal;
    }

    public void log(String str){
        Log.d(TAG, str);
        //System.out.println(str);
    }

}