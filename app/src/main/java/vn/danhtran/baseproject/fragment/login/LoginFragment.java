package vn.danhtran.baseproject.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.danhtran.baseproject.R;
import vn.danhtran.baseproject.databinding.LoginFragmentBinding;
import vn.danhtran.baseproject.fragment.BaseFragment;
import vn.danhtran.baseproject.fragment.login.authentication.MyAuthenticate;

/**
 * Created by SilverWolf on 06/04/2017.
 */
public class LoginFragment extends BaseFragment {
    private LoginFragmentBinding loginFragmentBinding;
    private LoginHandler loginHandler;

    public static LoginFragment instance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public int setLayout() {
        return R.layout.login_fragment;
    }

    @Override
    public void initUI() {
        loginFragmentBinding = (LoginFragmentBinding) binding;
        setProgressBar(loginFragmentBinding.progressLayout.progressBar);

        loginHandler = new LoginHandler(this);
        loginFragmentBinding.setHandler(loginHandler);
        loginFragmentBinding.executePendingBindings();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onConfigurationChanged() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyAuthenticate.getInstance().onActivityResult(requestCode, resultCode, data);
    }
}
