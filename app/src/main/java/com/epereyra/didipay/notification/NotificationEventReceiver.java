package com.epereyra.didipay.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.epereyra.didipay.util.Util;

public class NotificationEventReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Util.scheduleJob(context);
    }
}
