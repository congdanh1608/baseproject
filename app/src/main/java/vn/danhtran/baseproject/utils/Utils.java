package vn.danhtran.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.blankj.utilcode.util.AppUtils;

import vn.danhtran.baseproject.BuildConfig;
import vn.danhtran.baseproject.MyApplication;
import vn.danhtran.baseproject.R;

/**
 * Created by SilverWolf on 17/06/2017.
 */

public class Utils {
    private static int DEVICE_WIDTH = 0;
    private static int DEVICE_HEIGHT = 0;

    public static void getScreenSize() {
        if (DEVICE_WIDTH != 0 && DEVICE_HEIGHT != 0) {
            return;
        }
        if (BuildConfig.DEBUG) Log.d("Util", "getScreenSize");
        Display display = ((WindowManager) MyApplication.Instance().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        if (MyApplication.Instance().getVersionOS() >= Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                MyApplication.Instance().getVersionOS() < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                widthPixels = (Integer) Display.class.getMethod("getWidth")
                        .invoke(display);
                heightPixels = (Integer) Display.class.getMethod("getHeight")
                        .invoke(display);

            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        // includes window decorations (statusbar bar/menu bar)
        else if (MyApplication.Instance().getVersionOS() >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getSize", Point.class).invoke(display,
                        realSize);
                widthPixels = realSize.x;
                heightPixels = realSize.y;
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        } else {
            widthPixels = display.getWidth();
            heightPixels = display.getHeight();
        }
        DEVICE_WIDTH = widthPixels;
        DEVICE_HEIGHT = heightPixels;

    }

    public static int getWidthScreenInPX() {
        getScreenSize();
        return DEVICE_WIDTH;
    }

    public static int getHeightScreenInPX() {
        getScreenSize();
        return DEVICE_HEIGHT;
    }

    public static int getWidthScreenInDP() {
        getScreenSize();
        return DimensionUtils.pxToDp(DEVICE_WIDTH);
    }

    public static int getHeightScreenInDP() {
        getScreenSize();
        return DimensionUtils.pxToDp(DEVICE_WIDTH);
    }

    //Open url
    public static void openUrl(Activity activity, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
    }

    public static void exitApp(Activity activity) {
        MyApplication.Instance().setLocation(null);       //clear location
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static void goToPlayStore(Activity activity) {
        final String appPackageName = AppUtils.getAppPackageName(MyApplication.Instance().getApplicationContext()); // getPackageName() from Context or Activity object
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getUrlApp())));
        }
    }

    private static String getUrlApp() {
        final String appPackageName = AppUtils.getAppPackageName(MyApplication.Instance().getApplicationContext()); // getPackageName() from Context or Activity object
        return "https://play.google.com/store/apps/details?id=" + appPackageName;
    }

    /*public static void shareAppByMsg(Activity activity) {
        String message = getUrlApp();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        activity.startActivity(Intent.createChooser(share, activity.getString(R.string.share_app)));
    }

    public static void sendEmailTo(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{activity.getString(R.string.email_developer)});
        intent.putExtra(Intent.EXTRA_SUBJECT, "[" + activity.getString(R.string.app_name) + "] "
                + activity.getString(R.string.feedback));
        intent.putExtra(Intent.EXTRA_TEXT, "");

        activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.send_email)));
    }*/
}
