package vn.danhtran.baseproject.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import vn.danhtran.baseproject.BuildConfig;
import vn.danhtran.baseproject.MyApplication;
import vn.danhtran.baseproject.R;
import vn.danhtran.baseproject.activity.BaseAppCompatActivity;
import vn.danhtran.baseproject.enums.RequestCode;
import vn.danhtran.baseproject.utils.DeprecatedUtil;

/**
 * Created by danh.tran on 04/08/16.
 */

public class LocationReceiver extends BroadcastReceiver implements OnLocationUpdatedListener {
    private LocationReceiverListener listener;
    private static LocationReceiver locationReceiver;

    public static LocationReceiver instance() {
        synchronized (LocationReceiver.class) {
            if (locationReceiver == null)
                locationReceiver = new LocationReceiver();
            return locationReceiver;
        }
    }

    public void requestLocation(BaseAppCompatActivity activity, LocationReceiverListener listener) {
        this.listener = listener;

        if (MyApplication.Instance().getLocation() != null) {
            listener.locationChanged(true);
            return;
        }

        Context context = MyApplication.Instance().getApplicationContext();
        context.registerReceiver(this, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)); //register in this to maintain activity + context

        if (checkPermissionLocation()) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            // Getting GPS status
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // Getting network status
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                showSettingsAlert();
            } else {
                SmartLocation.with(context).location().start(this);
            }
        } else {
            if (activity != null)
                requestPermissionLocation(activity);
        }
    }

    public void stopUpdateLocation() {
        SmartLocation.with(MyApplication.Instance().getApplicationContext()).location().stop();
        listener = null;
    }

    @Override
    public void onLocationUpdated(Location location) {
        MyApplication.Instance().setLocation(location);
        MyApplication.Instance().setUseLocation(true);
        listener.locationChanged(true);
        if (BuildConfig.DEBUG) Log.d("LocationReceiver", "Got your location");

        //stop
        stopUpdateLocation();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            requestLocation(null, listener);
            MyApplication.Instance().setUseLocation(true);
        }
    }

    public void showSettingsAlert() {
        MyApplication.Instance().setUseLocation(false);
        Context context = MyApplication.Instance().getApplicationContext();

        new MaterialDialog.Builder(context)
                .title("title")
                .content("Content")
                .positiveText("Agree")
                .icon(DeprecatedUtil.getDrawableVector(R.drawable.ic_alert, R.mipmap.ic_alert))
                .onNegative((dialog, which) -> {
                    dialog.cancel();
                    listener.locationChanged(false);
                })
                .onPositive((dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                    listener.locationChanged(false);
                })
                .show();
    }

    private boolean checkPermissionLocation() {
        Context context = MyApplication.Instance().getApplicationContext();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static void requestPermissionLocation(BaseAppCompatActivity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RequestCode.MY_PERMISSIONS_REQUEST_LOCATION.getValue());
    }

    public interface LocationReceiverListener {
        void locationChanged(boolean success);
    }
}
