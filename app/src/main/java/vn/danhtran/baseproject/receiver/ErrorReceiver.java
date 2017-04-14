package vn.danhtran.baseproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.io.Serializable;

import vn.danhtran.baseproject.MyApplication;

/**
 * Created by danhtran on 10/11/2016.
 */
public class ErrorReceiver extends BroadcastReceiver {
    private static final String KEY_ERROR = "KEY_ERROR";
    public static final String ACTION_POST_FEED_RECEIVER = "vn.asquare.soev.ERROR_RECEIVER";
    private static ErrorReceiver errorReceiver;
    private ErrorReceiverListener errorReceiverListener;

    public static ErrorReceiver instance() {
        synchronized (ErrorReceiver.class) {
            if (errorReceiver == null)
                errorReceiver = new ErrorReceiver();
            return errorReceiver;
        }
    }

    public void registerErrorReceiver(ErrorReceiverListener errorReceiverListener) {
        this.errorReceiverListener = errorReceiverListener;
        MyApplication.Instance().getApplicationContext().registerReceiver(this, new IntentFilter(ACTION_POST_FEED_RECEIVER));      //must register in this to maintain activity + context
    }

    public void broadcastIntent(Object error) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_ERROR, (Serializable) error);
        intent.putExtras(bundle);
        intent.setAction(ACTION_POST_FEED_RECEIVER);
        MyApplication.Instance().getApplicationContext().sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Object error = intent.getSerializableExtra(KEY_ERROR);
        if (errorReceiverListener!=null)
            errorReceiverListener.onFailure(error);
    }

    public interface ErrorReceiverListener {
        void onFailure(Object error);
    }
}
