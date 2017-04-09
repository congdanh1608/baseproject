package vn.danhtran.baseproject.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.blankj.utilcode.util.FragmentUtils;

import java.util.HashMap;

import vn.danhtran.baseproject.fragment.login.LoginFragment;
import vn.danhtran.baseproject.R;

/**
 * Created by CongDanh on 07/06/2016.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    protected ViewDataBinding binding;
    private View progressBar;
    protected static boolean isAlive = false;
    protected HashMap<String, String> hashTitles;
    private FragmentManager mFragmentManager;
    private String curFragment = "";

    public abstract int setLayout();

    public abstract void initUI();          //set handler + excute

    public abstract FragmentManager initFragmentManager();

    public abstract void initData();

    public abstract void initListener();

    public ViewDataBinding getBinding() {
        return binding;
    }

    public FragmentManager getmFragmentManager() {
        return mFragmentManager;
    }

    public String getCurFragment() {
        return curFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int xml = setLayout();
        if (xml != 0 && binding == null) {
            setContentView(xml);
            binding = DataBindingUtil.setContentView(this, xml);
        }

        createHashMapTitle();
        initUI();
        mFragmentManager = initFragmentManager();
        initListener();
        initData();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        try {
            super.onRestoreInstanceState(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isAlive = true;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

//attach fonts
//    protected void attachBaseContext(Context context) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
//    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void setProgressBar(View view) {
        this.progressBar = view;
    }

    @Override
    public void onBackStackChanged() {
        String temp = null;
        if (mFragmentManager.getBackStackEntryCount() >= 1) {
            temp = mFragmentManager.getBackStackEntryAt(
                    mFragmentManager.getBackStackEntryCount() - 1).getName();
            curFragment = temp;
        }

        //handle add fragment
        changeTitle(curFragment, null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    //set when change language
   /* public void setLocale(boolean recreate, String langSelected) {
        if (TextUtils.isEmpty(langSelected))
            langSelected = SharePreferences.getInstance().readString(SharePreferences.KEY_LANGUAGE);
        String langCurrent = DeprecatedUtil.getLocale(getResources()).getLanguage();

        if (langSelected != null && !langCurrent.equals(langSelected)) {
            Locale myLocale = new Locale(langSelected);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();

            Locale.setDefault(myLocale);
            DeprecatedUtil.setLocale(conf, myLocale);
            res.updateConfiguration(conf, dm);
            if (recreate) {
                restartActivity();
            }
        }
    }*/

    public void addMyFragment(String tag, Object data, String title) {
        Fragment fragMain = mFragmentManager.findFragmentByTag(tag);
        if (curFragment != null && !tag.equals(curFragment)) {
            //check in backstack -> get fragment from backstack
            if (fragMain != null) {
                mFragmentManager.popBackStack(tag, 0);
                return;
            }
            //add new fragment
            if (tag.equals(LoginFragment.class.getSimpleName())) {
                fragMain = LoginFragment.instance();
            }

            if (fragMain != null) {
                changeTitle(tag, title);
                FragmentUtils.replaceFragment(mFragmentManager, fragMain, R.id.content_fragment, true);
            }
        }
    }

    private void changeTitle(String tag, String title) {
        if (TextUtils.isEmpty(title)) {
            title = hashTitles.get(tag);
        }
        setTitle(title);
    }

    private void createHashMapTitle() {
        hashTitles = new HashMap<>();
        hashTitles.put(LoginFragment.class.getSimpleName(), "");
    }

    public void startMyActivity(String tag, Bundle bundle) {
        Intent intent = null;
        if (!TextUtils.isEmpty(tag)) {
            if (tag.equals(MainActivity.class.getSimpleName())) {
                intent = MainActivity.instance(this);
            }
        }

        if (intent != null) {
            if (bundle != null) intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void showProgressbar() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressbar() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MyConstants.MY_PERMISSIONS_REQUEST_GPS:
//                getLocation();
//                break;
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
