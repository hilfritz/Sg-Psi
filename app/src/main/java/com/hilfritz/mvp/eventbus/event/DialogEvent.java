package com.hilfritz.mvp.eventbus.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hilfritz on 2/12/17.
 */

public class DialogEvent {
    int eventType = -100;
    public static final int CLOSE = 1;

    public DialogEvent(int eventType) {
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }

    public static void fireEvent(int clickEventType){
        EventBus.getDefault().post(
                new DialogEvent(
                        clickEventType)
        );
    }
}
