package com.hilfritz.mvp.ui.psi;

import android.util.Log;

import com.google.gson.Gson;
import com.hilfritz.mvp.AndroidTest;
import com.hilfritz.mvp.api.psi.pojo.PsiPojo;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
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
public class PsiMapPresenterImplTest extends AndroidTest {

    private static final String TAG = "PsiMapPresenterImplTest";
    PsiMapPresenterImpl presenter;
    PsiMapContract.View view;
    PsiMapContract.Model model;
    Gson gson = new Gson();
    Scheduler bgThread, mainThread = Schedulers.io();

    @Before
    public void before() {
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

    }

    @After
    public void after() {
        RxJavaHooks.reset();
    }

    @Test
    public void loadPsiDataSuccess(){

        //>>>> CONDITIONS
        presenter = new PsiMapPresenterImpl();
        PsiPojo sampleJsonPojoTemp = getSampleJsonPojo();
        Observable<PsiPojo> sampleJsonPojo= Observable.just(sampleJsonPojoTemp);

        //alternative 1
        when (model.getAllPsi()).thenReturn(sampleJsonPojo);
        //alternative 2
        /*
        model = new PsiMapContract.Model() {
            @Override
            public PsiPojo getAllPsi() {
                return sampleJsonPojo;
            }
        };
        */

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

        Observable<PsiPojo> empty = Observable.<PsiPojo>empty();
        //alternative 1
        when (model.getAllPsi()).thenReturn(empty);
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
        verify(view, times(1)).showDialogWithMessage(anyString());   //MAKE SURE ERROR DIALOG WAS CALLED
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


            br = new BufferedReader(new FileReader("sample.json"));
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

    public void displayAllFilesDirectories(){
        File deleteme = new File("deleteme");
        try {
            deleteme.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<File> listFiles = getListFiles(deleteme);
        for (int x = 0; x< listFiles.size(); x++){
            File file = listFiles.get(x);
            if (file.isDirectory()){
                log(">>"+file.getName());
            }else{
                log(">>=="+file.getName());
            }
        }
    }

    public void log(String str){
        Log.d(TAG, str);
        //System.out.println(str);
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                //if(file.getName().endsWith(".csv")){
                    inFiles.add(file);
                //}
            }
        }
        return inFiles;
    }

}