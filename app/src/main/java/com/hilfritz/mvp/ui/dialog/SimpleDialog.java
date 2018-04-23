package com.hilfritz.mvp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hilfritz.mvp.R;
import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.util.FrescoUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hilfritz Camallere on 11/11/16.
 */

public class SimpleDialog extends DialogFragment {

    public static final String TAG = "CustomDialog";
    private static final String ARG_MESSAGE = "param1";
    private static final String ARG_ICONID = "param2";
    private static final String ARG_CANCELLABLE = "param3";
    private static final String ARG_DIALOG_TYPE = "param4";
    private static final String ARG_JSONOBJECT = "param5";
    private static final String ARG_FINISH_ON_DISMISS = "param6";
    private static final String ARG_PARAM7 = "param7";

    public Context c;
    public Dialog d;


    @BindView(R.id.message)
    public TextView message;


    @BindView(R.id.rounded_img)
    SimpleDraweeView icon;


    int dialogType = TYPE_DIALOG_NORMAL; //NORMAL DIALOG WITHOUT BUTTON, WITH 1 TEXTVIEW
    String jsonObjectExtra = "";

    public static final int TYPE_DIALOG_NORMAL = 0;

    public static final int TYPE_DIALOG_LEAVE_REQUEST_PENDING_CANCEL = 1;

    @Inject
    Gson gson;
    //public GPSTracker gpsTracker;
    boolean finishOnDismiss = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication)getActivity().getApplication()).getAppComponent().inject(this);
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

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate( R.layout.dialog_simple, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            String message = getArguments().getString(ARG_MESSAGE);
            setMessage(message);
            int iconId = getArguments().getInt(ARG_ICONID);
            setDrawableId(iconId);
            setCancelable(getArguments().getBoolean(ARG_CANCELLABLE, true));
            setDialogType(getArguments().getInt(ARG_DIALOG_TYPE, TYPE_DIALOG_NORMAL));
            setJsonObjectExtra(getArguments().getString(ARG_JSONOBJECT, ""));
            setFinishOnDismiss(getArguments().getBoolean(ARG_FINISH_ON_DISMISS, finishOnDismiss));




        }

        return view;
    }

    public void setMessage(String message){
        Log.d(TAG, "setMessage: ");
        this.message.setText(message);
    }

    public void setDrawableId(int drawableId){
        Log.d(TAG, "setDrawableId: ");
        if (drawableId==0){
            icon.setVisibility(View.GONE);
        }else{
            icon.setImageURI(FrescoUtil.getUriFromDrawableId(drawableId));
        }

    }

    /**
     * Simple cancellable/non-cancellable dialogs
     * @param message
     * @param iconId
     * @param cancellable
     * @return
     */
    public static SimpleDialog newInstance(String message, int iconId, boolean cancellable) {
        SimpleDialog fragment = new SimpleDialog();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        args.putInt(ARG_ICONID, iconId);
        args.putBoolean(ARG_CANCELLABLE, cancellable);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *
     * @param message
     * @param iconId
     * @param cancellable
     * @param finishOnDismiss
     * @return
     */
    public static SimpleDialog newInstance(String message, int iconId, boolean cancellable, boolean finishOnDismiss) {
        SimpleDialog fragment = new SimpleDialog();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        args.putInt(ARG_ICONID, iconId);
        args.putBoolean(ARG_CANCELLABLE, cancellable);
        args.putBoolean(ARG_FINISH_ON_DISMISS, finishOnDismiss);
        fragment.setArguments(args);
        return fragment;
    }
    /**
     *
     * @param message
     * @param iconId
     * @param cancellable
     * @param dialogType int
     *                   <ul>
     *                   <li>{@link SimpleDialog#TYPE_DIALOG_LEAVE_REQUEST_PENDING_CANCEL}</li>
     *                   <li></li>
     *                   <li></li>
     *                   </ul>
     * @return
     */
    public static SimpleDialog newInstance(String message, int iconId, boolean cancellable, int dialogType, String jsonObject) {
        SimpleDialog fragment = new SimpleDialog();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        args.putInt(ARG_ICONID, iconId);
        args.putBoolean(ARG_CANCELLABLE, cancellable);
        args.putInt(ARG_DIALOG_TYPE, dialogType);
        args.putString(ARG_JSONOBJECT, jsonObject);
        fragment.setArguments(args);
        return fragment;
    }

    public String getJsonObjectExtra() {
        return jsonObjectExtra;
    }

    public void setJsonObjectExtra(String jsonObjectExtra) {
        this.jsonObjectExtra = jsonObjectExtra;
    }

    public int getDialogType() {
        return dialogType;
    }

    public void setDialogType(int dialogType) {
        this.dialogType = dialogType;
    }

    public boolean isFinishOnDismiss() {
        return finishOnDismiss;
    }

    public void setFinishOnDismiss(boolean finishOnDismiss) {
        this.finishOnDismiss = finishOnDismiss;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (isFinishOnDismiss()){
            getActivity().finish();
        }
    }
}
