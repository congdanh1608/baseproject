package vn.danhtran.baseproject.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import vn.danhtran.baseproject.activity.BaseAppCompatActivity;

/**
 * Created by DanhTran on 2/12/17.
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;
    protected FrameLayout frameLayout;
    protected boolean isAlive = false;
    protected ViewDataBinding binding;

    private View progressBar;

    public abstract int setLayout();

    public abstract void initUI();              //set handler + excute

    public abstract void initData();

    public abstract void initListener();

    public abstract void onConfigurationChanged();

    public View getRootView() {
        return rootView;
    }

    public BaseAppCompatActivity getBaseActivity() {
        if (getActivity() instanceof BaseAppCompatActivity)
            return (BaseAppCompatActivity) getActivity();
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    protected void setProgressBar(View view) {
        this.progressBar = view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        frameLayout = new FrameLayout(getActivity());
        int xml = setLayout();
        if (xml != 0 && binding == null) {
            binding = DataBindingUtil.inflate(inflater, xml, container, false);
            rootView = binding.getRoot();
        }
        setHasOptionsMenu(true);
        initUI();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        initData();
    }


    @Override
    public void onStart() {
        super.onStart();
        isAlive = true;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        isAlive = false;
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChanged();
    }

    public void showProgressbar() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressbar() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }

    public void delayOnBackPressed(int timeDelay) {
        if (timeDelay < 0) timeDelay = 0;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().onBackPressed();
            }
        }, timeDelay);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //hide all item menu
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}