package com.hilfritz.mvp.ui.loading;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.hilfritz.mvp.R;
import com.hilfritz.mvp.eventbus.event.DialogEvent;
import com.hilfritz.mvp.util.RxUtil;
import com.hilfritz.mvp.util.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Hilfritz Camallere on 11/11/16.
 */

public class FullscreenLoadingDialog extends DialogFragment {

    public static final String TAG = "LoadingDialog";
    private static final String ARG_MESSAGE = "param1";
    private static final String ARG_ICONID = "param2";
    private static final String ARG_CANCELLABLE = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";

    public Context c;
    public Dialog d;

    @BindView(R.id.message)
    public TextView message;


    final int DISPLAY_TIMEOUT = 40;
    int displayedSecondsCounter = 0;


    @BindView(R.id.rounded_img)
    SimpleDraweeView icon;



    //@BindView(R.id.rounded_img)
    //ImageView icon;


    int dialogType = 0;
    private Subscription displayTimerTimeoutSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        try {
            EventBus.getDefault().register(this);
        }catch (Exception e){
            Log.d(TAG, "onCreate() eventbus registration error: "+e.getLocalizedMessage());
        }
    }

    /**
     * TO REMOVE THE TITLE SPACE
     * see http://stackoverflow.com/questions/15277460/how-to-create-a-dialogfragment-without-title
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Log.d(TAG, "onCreateDialog: ");

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE); //REMOVES THE TITLE OF THE DIALOG
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);  //REMOVES THE DEFAULT DIALOG BACKGROUND
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate( R.layout.dialog_loading_fullscreen, container, false);
        ButterKnife.bind(this, view);

        loadDefaultLoadingGif();
        //TURN ON THE IMAGE RENDERING FOR THE SVG ANIMATION
        //icon.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        if (getArguments() != null) {
            String message = getArguments().getString(ARG_MESSAGE);
            setMessage(message);
            int iconId = getArguments().getInt(ARG_ICONID);
            setDrawableId(iconId);
            setCancelable(getArguments().getBoolean(ARG_CANCELLABLE, true));
        }

        displayedSecondsCounter = 0;
        startDisplayTimeout();

        return view;
    }

    private void startDisplayTimeout() {
        Log.d(TAG, "startDisplayTimeout: ");
        displayTimerTimeoutSubscription =
                Observable
                        .interval(1000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {
                                Log.d(TAG, "onCompleted: emergency dismmiss");
                                dismissAllowingStateLoss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "onError: emergency dismmiss");
                                e.printStackTrace();
                                //Toast.makeText(getActivity(), "Please refresh or try again.", Toast.LENGTH_SHORT).show();
                                dismissAllowingStateLoss();
                            }

                            @Override
                            public void onNext(Long aLong) {
                                Log.d(TAG, "onNext: loading displaying for "+aLong.intValue()+" seconds");
                                if (aLong.intValue() >= DISPLAY_TIMEOUT){
                                    Log.d(TAG, "onNext: emergency dismmiss");
                                    dismissAllowingStateLoss();
                                }
                                displayedSecondsCounter = aLong.intValue();
                            }
                        });

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(TAG, "onDismiss: ");

    }


    private void loadDefaultLoadingGif() {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithResourceId(R.raw.loading_gif).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageRequest.getSourceUri())
                .setAutoPlayAnimations(true)

        .build();
        icon.setController(controller);
    }


    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN)
    public void onDialogEvent(DialogEvent e) {
        Log.d(TAG, "onDialogEvent: ");
        if (e.getEventType()==DialogEvent.CLOSE){
            Log.d(TAG, "onDialogEvent: closing dialog");
            dismissAllowingStateLoss();
        }
    }
    public void setMessage(String message){
        Log.d(TAG, "setMessage: ");
        if (!StringUtil.isStringEmpty(message)) {
            this.message.setText(message);
        }
    }

    public void setDrawableId(int drawableId){
        Log.d(TAG, "setDrawableId: ");
        //icon.setImageURI(FrescoUtil.getUriFromDrawableId(drawableId));
    }

    /**
     * Simple cancellable/non-cancellable dialogs
     * @return
     */
    public static FullscreenLoadingDialog newInstance() {
        FullscreenLoadingDialog fragment = new FullscreenLoadingDialog();
        Bundle args = new Bundle();
        args.putBoolean(ARG_CANCELLABLE, false);
        fragment.setArguments(args);
        return fragment;
    }
    public static FullscreenLoadingDialog newInstance(String message, int iconDrawableId, boolean isCancellable) {
        FullscreenLoadingDialog fragment = new FullscreenLoadingDialog();
        Bundle args = new Bundle();
        args.putBoolean(ARG_CANCELLABLE, isCancellable);
        args.putInt(ARG_ICONID, iconDrawableId);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }
    public static FullscreenLoadingDialog newInstance(String message, boolean isCancellable) {
        FullscreenLoadingDialog fragment = new FullscreenLoadingDialog();
        Bundle args = new Bundle();
        args.putBoolean(ARG_CANCELLABLE, isCancellable);
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    public static void showLoading(FragmentManager fragmentManager){
        FullscreenLoadingDialog cd = FullscreenLoadingDialog.newInstance();
        cd.show(fragmentManager, FullscreenLoadingDialog.TAG);
    }

    public static void showLoading(FragmentManager fragmentManager, String message, int iconDrawableRes, boolean isCancellable){
        FullscreenLoadingDialog cd = FullscreenLoadingDialog.newInstance(message, iconDrawableRes, isCancellable);
        cd.show(fragmentManager, FullscreenLoadingDialog.TAG);
    }
    public static void showLoading(FragmentManager fragmentManager, String message, boolean isCancellable){
        FullscreenLoadingDialog cd = FullscreenLoadingDialog.newInstance(message, isCancellable);
        cd.show(fragmentManager, FullscreenLoadingDialog.TAG);
    }


    @Override
    public void onResume() {
        Log.d(TAG,"onResume: ");
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    public static final void hideLoading(){
        DialogEvent.fireEvent(DialogEvent.CLOSE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        try {
            EventBus.getDefault().unregister(this);
        }catch (Exception e){
            Log.d(TAG, "onDestroy() busprovider registration error: "+e.getLocalizedMessage());
        }
        RxUtil.unsubscribe(displayTimerTimeoutSubscription);
    }

}
