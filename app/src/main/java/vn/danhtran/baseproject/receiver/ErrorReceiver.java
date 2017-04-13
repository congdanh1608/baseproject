package vn.danhtran.baseproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.io.Serializable;

import vn.danhtran.baseproject.MyApplication;
import vn.danhtran.baseproject.activity.BaseAppCompatActivity;

/**
 * Created by danhtran on 10/11/2016.
 */
public class ErrorReceiver extends BroadcastReceiver {
    private static final String KEY_ERROR = "KEY_ERROR";
    public static final String ACTION_POST_FEED_RECEIVER = "vn.asquare.soev.ERROR_RECEIVER";
    private static ErrorReceiver errorReceiver;
    private BaseAppCompatActivity activity;
    private Object error;

    public static ErrorReceiver instance() {
        synchronized (ErrorReceiver.class) {
            if (errorReceiver == null)
                errorReceiver = new ErrorReceiver();
            return errorReceiver;
        }
    }

    public void registerPostFeed(BaseAppCompatActivity activity) {
        this.activity = activity;
        MyApplication.Instance().getApplicationContext().registerReceiver(this, new IntentFilter(ACTION_POST_FEED_RECEIVER));      //must register in this to maintain activity + context
    }

    public void broadcastIntent(Object error) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ERROR, (Serializable) error);
        intent.putExtras(bundle);
        intent.setAction(ACTION_POST_FEED_RECEIVER);
        activity.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        error = intent.getSerializableExtra(KEY_ERROR);
        if (activity != null) {
//            Util.showMessageErrorAPI(error, activity, activity.rootview);
        }
    }
}
