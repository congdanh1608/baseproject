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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.FragmentUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.danhtran.baseproject.BuildConfig;
import vn.danhtran.baseproject.R;
import vn.danhtran.baseproject.activity.main.MainActivity;
import vn.danhtran.baseproject.enums.RequestCode;
import vn.danhtran.baseproject.fragment.login.LoginFragment;
import vn.danhtran.baseproject.fragment.recyclerviewtest.RecyclerFragment;
import vn.danhtran.baseproject.receiver.ErrorReceiver;
import vn.danhtran.baseproject.receiver.LocationReceiver;

/**
 * Created by CongDanh on 07/06/2016.
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    protected ViewDataBinding binding;
    private View progressBar;
    protected static boolean isAlive = false;
    protected HashMap<String, String> hashTitles;
    protected FragmentManager mFragmentManager;
    private String curFragment = "";
    protected TextView tvTitle;

    public abstract int setLayout();

    public abstract void initUI();          //set handler + excute

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
        initFragmentManager();
        createHashMapTitle();
        initUI();
        initListener();
        initData();
    }

    private void initFragmentManager() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);
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
        registerReceiver();
    }

    private void registerReceiver() {
        ErrorReceiver.instance().registerErrorReceiver(new ErrorReceiver.ErrorReceiverListener() {
            @Override
            public void onFailure(Object error) {
                //Log
            }
        });
//        ErrorReceiver.instance().registerErrorReceiver(error -> {
//            //Log
//        });
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
        boolean isPass = !tag.equals(curFragment);
        //except fragment movie + tvshow -> b/c i can open that fragment in similar.
        if (curFragment != null && isExceptFragment(tag)) {
            fragMain = null;
            isPass = true;
        }
        if (isPass) {
            //check in backstack -> get fragment from backstack
            if (fragMain != null) {
                mFragmentManager.popBackStack(tag, 0);
                return;
            }
            //add new fragment
            if (tag.equals(LoginFragment.class.getName())) {
                fragMain = LoginFragment.instance();
            } else if (tag.equals(RecyclerFragment.class.getName())) {
                fragMain = RecyclerFragment.instance();
            }


            if (fragMain != null) {
                changeTitle(tag, title);
                FragmentUtils.replaceFragment(mFragmentManager, fragMain, R.id.content_fragment, true);
            }
        }
    }

    private boolean isExceptFragment(String tag) {
        List<String> listExcept = new ArrayList<>();
        return listExcept.contains(tag);
    }

    public void changeTitle(String tag, String title) {
        if (TextUtils.isEmpty(title)) {
            title = hashTitles.get(tag);
        }
        if (tvTitle != null)
            tvTitle.setText(title);
        else if (title != null)
            setTitle(title);
        else changeTitleBackToMain(tag);
    }

    private void changeTitleBackToMain(String tag) {
        /*if (MainFragment.class.getName().equals(tag)) {
            String nameCurrentChildFragment = getCurrentNameChildFragment();
            if (!TextUtils.isEmpty(nameCurrentChildFragment))
                changeTitle(nameCurrentChildFragment, "");
        }*/
    }

    private void createHashMapTitle() {
        hashTitles = new HashMap<>();
        hashTitles.put(LoginFragment.class.getName(), "");
    }

    public void startMyActivity(String tag, Bundle bundle) {
        Intent intent = null;
        if (!TextUtils.isEmpty(tag)) {
            if (tag.equals(MainActivity.class.getName())) {
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

    private void continueCurrentAction() {
        String current = getCurrentNameFragment();
        if (BuildConfig.DEBUG) Logger.d("Continue " + current);
        if (mFragmentManager != null && current != null) {
            Fragment fragment = mFragmentManager.findFragmentByTag(current);
            if (fragment != null) {

            }
        }
    }

    public String getPreviousNameFragment() {
        int count = mFragmentManager.getBackStackEntryCount();
        if (count > 1)
            return mFragmentManager.getBackStackEntryAt(count - 2).getName();
        return null;
    }

    public String getCurrentNameFragment() {
        int count = mFragmentManager.getBackStackEntryCount();
        if (count > 0)
            return mFragmentManager.getBackStackEntryAt(count - 1).getName();
        return null;
    }

    public Fragment getCurrentFragment() {
        if (mFragmentManager != null) {
            String currentNameFragment = getCurrentNameFragment();
            if (!TextUtils.isEmpty(currentNameFragment))
                return mFragmentManager.findFragmentByTag(currentNameFragment);
        }
        return null;
    }

    public FragmentManager getChildManager() {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment != null) {
            return currentFragment.getChildFragmentManager();
        }
        return null;
    }

    public String getCurrentNameChildFragment() {
        FragmentManager childManager = getChildManager();
        if (childManager != null) {
            int count = childManager.getBackStackEntryCount();
            if (count > 0) {
                return childManager.getBackStackEntryAt(count - 1).getName();
            }
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RequestCode code = RequestCode.fromValue(requestCode);
        if (code != null)
            switch (code) {
                case MY_PERMISSIONS_REQUEST_LOCATION:
//                    LocationReceiver.instance().requestLocation(this, success -> {
//                        continueCurrentAction();
//                    });
                    LocationReceiver.instance().requestLocation(this, new LocationReceiver.LocationReceiverListener() {
                        @Override
                        public void locationChanged(boolean success) {
                            continueCurrentAction();
                        }
                    });
                    break;
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
