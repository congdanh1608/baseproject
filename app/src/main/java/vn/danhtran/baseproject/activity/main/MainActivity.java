package vn.danhtran.baseproject.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import vn.danhtran.baseproject.R;
import vn.danhtran.baseproject.activity.BaseAppCompatActivity;
import vn.danhtran.baseproject.databinding.ActivityMainBinding;
import vn.danhtran.baseproject.databinding.NavHeaderMainBinding;
import vn.danhtran.baseproject.fragment.login.LoginFragment;
import vn.danhtran.baseproject.fragment.login.authentication.MyAuthenticate;
import vn.danhtran.baseproject.fragment.recyclerviewtest.RecyclerFragment;
import vn.danhtran.baseproject.utils.Utils;

public class MainActivity extends BaseAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding activityMainBinding;
    private NavHeaderMainBinding navHeaderMainBinding;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private int backButtonCount = 0;


    public static Intent instance(Activity activity) {
        return new Intent(activity, MainActivity.class);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initUI() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        activityMainBinding = (ActivityMainBinding) binding;
        navHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.nav_header_main, activityMainBinding.navView, false);
        activityMainBinding.navView.addHeaderView(navHeaderMainBinding.getRoot());
    }

    private void addDefaultFragment() {          //MapFragment
    /*    String postId = null;
        if (!TextUtils.isEmpty(deepLink)) {
            TextUtil.HashTag hashTag = TextUtil.getHashTag(deepLink);
            if (hashTag != null)
                postId = hashTag.getValue();
        }*/
//        addMyFragment(LoginFragment.class.getName(), null, null);
        addMyFragment(RecyclerFragment.class.getName(), null, null);

       /* if (MyApplication.Instance().getTypeLogin() != MyAuthenticate.TYPE_LOGIN_ANONYMOUS &&
                TextUtils.isEmpty(MyApplication.Instance().getMyProfile().getPhone())) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(EditProfileActivity.KEY_ALLOW_UPDATE, true);
            bundle.putBoolean(EditProfileActivity.KEY_REQUIRE, true);
            startMyActivity(EditProfileActivity.class.getName(), bundle);
        }*/
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = activityMainBinding.appBarMain.toolbar;
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, activityMainBinding.drawerLayout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        activityMainBinding.drawerLayout.addDrawerListener(toggle);
        //set event click for toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowBackButton()) {
                    onBackPressed();
                } else if (activityMainBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    activityMainBinding.drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        toggle.syncState();

        activityMainBinding.navView.setNavigationItemSelectedListener(this);

        addDefaultFragment();
    }

    @Override
    public void onBackStackChanged() {
        super.onBackStackChanged();
        showBackArrow();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int backStackEntryCount = mFragmentManager.getBackStackEntryCount();
            if (backStackEntryCount <= 1) {        //exit app
                exitApp();
            } else if (isDrawerLeftFragment(getPreviousNameFragment())) {   //previous nerver null -> b/c count > 1
//                addMyFragment(MainFragment.class.getName(), null, null);    //go back main fragment when back in menu drawer
            } else
                super.onBackPressed();
        }
    }

    private boolean isDrawerLeftFragment(String tag) {
        List<String> listExcept = new ArrayList<>();

        return listExcept.contains(tag);
    }

    private boolean isShowBackButton() {
//        String currentFrag = getCurrentNameFragment();
//        if (isDrawerLeftFragment(currentFrag) || MainFragment.class.getName().equals(currentFrag))
//            return false;
        return true;
    }

    private void showBackArrow() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            if (isShowBackButton()) {
                actionBar.setDisplayHomeAsUpEnabled(false);             //set back button if it is not...
                toggle.setDrawerIndicatorEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                //set home button if it is drawer left fragment/main fragment
                toggle.setDrawerIndicatorEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
            }
            toggle.syncState();
        }
    }

    private void showLogoHomeFragment() {
      /*  if (HomeFragment.class.getName().equals(getCurrentNameFragment())) {
            actionBar.setBackgroundDrawable(DeprecatedUtil.getResourceDrawable(R.mipmap.bacground_actionbar));

            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(getLayoutInflater().inflate(R.layout.logo_title_actionbar, null),
                    new ActionBar.LayoutParams(
                            ActionBar.LayoutParams.WRAP_CONTENT,
                            ActionBar.LayoutParams.MATCH_PARENT,
                            Gravity.CENTER
                    )
            );
        }*/
    }

    private void exitApp() {
        //check exit app
        if (backButtonCount >= 1) {
            Utils.exitApp(this);
        } else {
            ToastUtils.showShortToastSafe("Exit app?");
            backButtonCount++;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backButtonCount = 0;
                }
            }, 3 * 1000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String fragment = null, activity = null;
                Bundle bundle = new Bundle();
                switch (id) {
                    case R.id.nav_camera:
                        addMyFragment(LoginFragment.class.getName(), null, null);
                        break;
                    case R.id.nav_gallery:
                        break;
                }
                if (!TextUtils.isEmpty(fragment)) {
                    addMyFragment(fragment, null, null);
                } else if (!TextUtils.isEmpty(activity)) {
//                    startMyActivity(FullActivity.class.getName(), bundle);
                }
            }
        }, 200);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyAuthenticate.getInstance().onActivityResult(requestCode, resultCode, data);
    }
}
