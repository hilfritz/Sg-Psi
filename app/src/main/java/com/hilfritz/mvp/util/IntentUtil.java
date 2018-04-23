package com.hilfritz.mvp.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Hilfritz Camallere on 19/7/16.
 */
public class IntentUtil {
    private static final String TAG = "IntentUtil";
    public static void sendEmailToTechSupport(String receiver, String subject, String body, Context context){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + receiver));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        //emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text
        context.startActivity(Intent.createChooser(emailIntent, "Choose Email app"));
    }

    public static void sendEmailToTechSupport(String subject, String body, Context context){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "help.android@herdhr.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        //emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text
        context.startActivity(Intent.createChooser(emailIntent, "Choose Email app"));
    }

    public static void addSmsIntentNoPicker(Context context, String mobile){
        Uri sms_uri = Uri.parse("smsto:"+mobile);
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        context.startActivity(sms_intent);
    }

    public final static void openInExternalWebBrowser(String url, Context context){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
    /**
     *
     * @param activity Activity
     * @param intentTag String tag
     * @return String returns the intent extra designated by the <b>intentTag</b> otherwise null
     */
    public static String getStringIntentExtra(Activity activity, String intentTag){
        String retVal = null;
        if (activity.getIntent()!=null){
            if (activity.getIntent().getStringExtra(intentTag)!=null){
                retVal = activity.getIntent().getStringExtra(intentTag);
            }
        }
        return retVal;
    }


    public static int getIntIntentExtra(Activity activity, String key, int defaultValue){
        int retVal = defaultValue;
        if (activity.getIntent()!=null){
            retVal = activity.getIntent().getIntExtra(key, defaultValue);
        }
        return retVal;
    }
    public static long getLongIntentExtra(Activity activity, String key, int defaultValue){
        long retVal = defaultValue;
        if (activity.getIntent()!=null){
            retVal = activity.getIntent().getLongExtra(key, defaultValue);
        }
        return retVal;
    }

    public static void openPDFFiile(Context context,File file){

        final Uri uri = Uri.fromFile(file);

        /*
        //REGISTER PDF APP READERS
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");

        //intent.setDataAndType(uri, "application/pdf");
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            Log.d(TAG, "openPDFFiile: "+packageName);
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        */

        //OPEN THE PDF
        Intent intent2 = new Intent(Intent.ACTION_VIEW);
        intent2.setDataAndType(uri, "application/pdf");
        intent2.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent1 = Intent.createChooser(intent2, "Open With");
        try {
            context.startActivity(intent1);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Please install pdf viewer app.", Toast.LENGTH_SHORT).show();
            // Instruct the user to install a PDF reader here, or something
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void openPDFFiile(Context context,String absoluteFilePath){
        File file = new File(absoluteFilePath);
        IntentUtil.openPDFFiile(context, file);
    }
}
