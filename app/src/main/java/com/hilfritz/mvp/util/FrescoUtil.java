package com.hilfritz.mvp.util;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.DrawableRes;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Hilfritz Camallere on 14/12/16.
 */

public class FrescoUtil {
    public static final String TAG = "FrescoUtil";
    /**
     * see http://stackoverflow.com/questions/30887615/loading-drawable-image-resource-in-frescos-simpledraweeview
     * @param drawableId
     * @return
     */
    public static final Uri getUriFromDrawableId(@DrawableRes int drawableId){
        return new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
                .path(String.valueOf(drawableId))
                .build();
    }

    /*
    public static final void loadHeaderImage(SimpleDraweeView image, Context context, int width, int height){
        Log.d(TAG, "loadHeaderImage: ");
        ResizeOptions resizeOptions = new ResizeOptions(width, height);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(FrescoUtil.getUriFromDrawableId(R.drawable.login_image_top))
                .setResizeOptions(resizeOptions)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(image.getController())
                .setControllerListener(new ControllerListener<ImageInfo>() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {
                        Log.d(TAG, "onSubmit: ");
                    }

                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        Log.d(TAG, "onFinalImageSet: ");
                    }

                    @Override
                    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                        Log.d(TAG, "onIntermediateImageSet: ");
                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {
                        Log.d(TAG, "onIntermediateImageFailed: ");
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        Log.d(TAG, "onFailure: ");
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onRelease(String id) {
                        Log.d(TAG, "onRelease: ");
                    }
                })
                .build();
        image.setController(controller);
    }
    */

    public static final void loadImage(Uri uri, SimpleDraweeView image, Context context, int width, int height){
        Log.d(TAG, "loadImage: ");
        ResizeOptions resizeOptions = new ResizeOptions(width, height);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(resizeOptions)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(image.getController())
                .setControllerListener(new ControllerListener<ImageInfo>() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {
                        Log.d(TAG, "onSubmit: ");
                    }

                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        Log.d(TAG, "onFinalImageSet: ");
                    }

                    @Override
                    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                        Log.d(TAG, "onIntermediateImageSet: ");
                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {
                        Log.d(TAG, "onIntermediateImageFailed: ");
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        Log.d(TAG, "onFailure: ");
                        throwable.printStackTrace();
                    }

                    @Override
                    public void onRelease(String id) {
                        Log.d(TAG, "onRelease: ");
                    }
                })
                .build();
        image.setController(controller);
    }

    public static final void loadColorDrawable(SimpleDraweeView image, int color){
        Log.d(TAG, "loadImage: ");
        //ResizeOptions resizeOptions = new ResizeOptions(width, height);
        DraweeController controller = Fresco.newDraweeControllerBuilder()

                .setOldController(image.getController())

                .setControllerListener(new ControllerListener<ImageInfo>() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {
                        Log.d(TAG, "onSubmit: ");
                    }

                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        Log.d(TAG, "onFinalImageSet: ");
                    }

                    @Override
                    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                        Log.d(TAG, "onIntermediateImageSet: ");
                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {
                        Log.d(TAG, "onIntermediateImageFailed: ");
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        Log.d(TAG, "onFailure: ");
                    }

                    @Override
                    public void onRelease(String id) {
                        Log.d(TAG, "onRelease: ");
                    }
                })
                .build();
        image.setController(controller);
    }

    public static final void loadSimpleDraweeViewImage(SimpleDraweeView image, Uri uri){
        Log.d(TAG, "loadHeaderImage: ");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setOldController(image.getController())
                .setControllerListener(new ControllerListener<ImageInfo>() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {
                        Log.d(TAG, "onSubmit: ");
                    }

                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        Log.d(TAG, "onFinalImageSet: ");
                    }

                    @Override
                    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                        Log.d(TAG, "onIntermediateImageSet: ");
                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {
                        Log.d(TAG, "onIntermediateImageFailed: ");
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        Log.d(TAG, "onFailure: ");
                    }

                    @Override
                    public void onRelease(String id) {
                        Log.d(TAG, "onRelease: ");
                    }
                })
                .build();
        image.setController(controller);

    }
}
