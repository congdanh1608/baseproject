package vn.danhtran.baseproject;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.os.Build;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import vn.danhtran.baseproject.appmodel.Profile;
import vn.danhtran.baseproject.appmodel.Session;
import vn.danhtran.baseproject.enums.SharePref;
import vn.danhtran.baseproject.sharepreferences.SharePreferences;
import vn.danhtran.customuniversalimageloader.FactoryImageLoader;

/**
 * Created by danhtran on 1/29/17.
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication myApplication;
    private Session session;
    private Location location;
    private String lang;
    private int versionOS;
    private boolean isUseLocation = true;
    private boolean isLauchApp = true;

    public MyApplication() {
        super();
        myApplication = this;
    }

    public static MyApplication Instance() {
        return myApplication;
    }

    public boolean isLauchApp() {
        return isLauchApp;
    }

    public void setLauchApp(boolean lauchApp) {
        isLauchApp = lauchApp;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getVersionOS() {
        return versionOS;
    }

    public void setVersionOS(int versionOS) {
        this.versionOS = versionOS;
    }

    public boolean isUseLocation() {
        return isUseLocation;
    }

    public void setUseLocation(boolean useLocation) {
        isUseLocation = useLocation;
    }

    public Session getSession() {
        return session;
    }

    public Profile getMyProfile() {
        if (session != null) return session.getProfile();
        return null;
    }

    public String getToken() {
        if (session != null) return session.getToken();
        return null;
    }

    public int getTypeLogin() {
        if (session != null) return session.getTypeLogin();
        return -1;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void updateSession() {
        if (session != null)
            SharePreferences.getInstance().writeObject(SharePref.SESSION_LOGIN.toString(), session);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize SDK
        initSDK();
        initData();
    }

    private void initSDK() {
        initFacebook();
        initFactoryImage();
//        initFont();
//        initFabric();
        initLogger();
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    private void initFactoryImage() {
        FactoryImageLoader.getInstance().initImageLoaderNoBackgroundUniversal(this);
    }

    //init fonts for app
    /*private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(FontUtil.ROBOTO_REGULAR)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }*/

    /*private void initFabric() {
        Fabric.with(this, new Crashlytics());
    }*/

    private void initLogger() {
        if ((0 != (getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE)))
            Logger.init(); // for debug, print all log
        else
            Logger.init().logLevel(LogLevel.NONE); // for release, remove all log
    }

    private void initData() {
        //version OS
        versionOS = Build.VERSION.SDK_INT;

        //language
        String s = SharePreferences.getInstance().readString(SharePref.LANGUAGE.toString());
        if (s != null) lang = s;
        else lang = Locale.getDefault().getLanguage();

        //load token
        session = SharePreferences.getInstance().readObject(Session.class, SharePref.SESSION_LOGIN.toString());

        //secret key
        if (BuildConfig.DEBUG)
            generalSecretKey();
    }

    //for facebook
    private void generalSecretKey() {
        MessageDigest md = null;
        try {
            PackageInfo info = getApplicationContext().getPackageManager().getPackageInfo(
                    getApplicationContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Logger.d(Base64.encodeToString(md.digest(), Base64.DEFAULT));
    }
}
