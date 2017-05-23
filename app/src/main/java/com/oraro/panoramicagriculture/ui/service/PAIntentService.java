package com.oraro.panoramicagriculture.ui.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.oraro.panoramicagriculture.ui.activity.MessageActivity;
import com.oraro.panoramicagriculture.ui.activity.MessageListActivity;

/**
 * Created by Administrator on 2017/5/5.
 */
public class PAIntentService extends GTIntentService {
    public PAIntentService() {

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        super.onHandleIntent(intent);
        Bundle var2 = intent.getExtras();
        if (var2 != null)
            Log.i(TAG,"action="+var2.getInt("action")+",bundle="+var2);
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        Log.e(TAG, "onReceiveMessageData -> " + "GTTransmitMessage = " + new String(msg.getPayload()));
        Intent intent = new Intent(this.getApplicationContext(), MessageListActivity.class);
        intent.putExtra("gson", new String(msg.getPayload()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        Log.e(TAG, "onReceiveCommandResult -> " + "GTCmdMessage = " + cmdMessage);
    }

}
