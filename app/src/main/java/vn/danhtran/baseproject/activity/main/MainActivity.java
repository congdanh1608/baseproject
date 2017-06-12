package vn.danhtran.baseproject.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import vn.danhtran.baseproject.R;
import vn.danhtran.baseproject.activity.BaseAppCompatActivity;
import vn.danhtran.baseproject.databinding.ActivityMainBinding;
import vn.danhtran.baseproject.databinding.NavHeaderMainBinding;
import vn.danhtran.baseproject.fragment.login.LoginFragment;
import vn.danhtran.baseproject.fragment.login.authentication.MyAuthenticate;
import vn.danhtran.baseproject.fragment.recyclerviewtest.RecyclerFragment;

public class MainActivity extends BaseAppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding activityMainBinding;
    private NavHeaderMainBinding navHeaderMainBinding;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

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
//        addMyFragment(LoginFragment.class.getSimpleName(), null, null);
        addMyFragment(RecyclerFragment.class.getSimpleName(), null, null);

       /* if (MyApplication.Instance().getTypeLogin() != MyAuthenticate.TYPE_LOGIN_ANONYMOUS &&
                TextUtils.isEmpty(MyApplication.Instance().getMyProfile().getPhone())) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(EditProfileActivity.KEY_ALLOW_UPDATE, true);
            bundle.putBoolean(EditProfileActivity.KEY_REQUIRE, true);
            startMyActivity(EditProfileActivity.class.getSimpleName(), bundle);
        }*/
    }

    @Override
    public FragmentManager initFragmentManager() {
        return getSupportFragmentManager();
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
        toggle.syncState();

        activityMainBinding.navView.setNavigationItemSelectedListener(this);

        addDefaultFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            addMyFragment(LoginFragment.class.getSimpleName(), null, null);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

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
