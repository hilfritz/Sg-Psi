package com.hilfritz.mvp.util;

import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by hilfritz on 15/12/16.
 */

public class RxUtil {
    private static final String TAG = "RxUtil";

    /**
     *
     * @param subscription
     * @return boolean TRUE if subscrption is still on going, (like network request is still on going)
     */
    public static final boolean isSubscribed(Subscription subscription){
        Log.d(TAG, "isSubscribed: ");
        if (subscription!=null && subscription.isUnsubscribed()==false){
            Log.d(TAG, "isSubscribed: true");
            return true;
        }
        return false;
    }

    /**
     * Used to unsubscribe a subscription from RX
     * @param subscription
     */
    public static final void unsubscribe(Subscription subscription){
        if (RxUtil.isSubscribed(subscription)){
            Log.d(TAG, "unsubscribe: ");
            subscription.unsubscribe();
        }
    }

    /**
     * Creates an observable object from a view that triggers when the view is clicked
     * @param view
     * @return
     */
    public static final Observable<View> getViewClickObservable(final View view){
        Observable<View> clickEventObservable = Observable.create(new Observable.OnSubscribe<View>() {
            @Override
            public void call(final Subscriber<? super View> subscriber) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (subscriber.isUnsubscribed()) return;
                        subscriber.onNext(v);
                    }
                });
            }
        });
        return clickEventObservable;
    }

    public static final Observable<Integer> secureThreadInBg(){
        return Observable.just(sampleCalculation());
    }

    public static final Integer sampleCalculation(){
        int x = 0;
        int y = 1;
        return x+y;
    }

}
