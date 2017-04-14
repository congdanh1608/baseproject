package vn.danhtran.baseproject.fragment.login;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import java.util.List;

import vn.danhtran.baseproject.SingleResultListener;
import vn.danhtran.baseproject.fragment.BaseFragment;
import vn.danhtran.baseproject.fragment.login.authentication.MyAuthenticate;

/**
 * Created by danhtran on 13/02/2017.
 */
public class LoginHandler {
    private BaseFragment fragment;
    private Context context;
    private String email = null, password;
    private int typeLogin;

    public LoginHandler(BaseFragment fragment) {
        this.fragment = fragment;

        initData();
    }

    private void initData() {
        context = fragment.getContext();
    }

    public TextWatcher getEmailTextWatcher() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                email = s.toString();
            }
        };
    }

    public TextWatcher getPassTextWatcher() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
            }
        };
    }

    public View.OnClickListener onClickBtnDismiss() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.onBackPressed();
            }
        };
    }

    public View.OnClickListener onClickLoginFacebook() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.showProgressbar();
                typeLogin = MyAuthenticate.TYPE_LOGIN_FACEBOOK;
                MyAuthenticate.getInstance().loginWith(MyAuthenticate.TYPE_LOGIN_FACEBOOK, fragment.getActivity(), new SingleResultListener<String>() {
                    @Override
                    public void onSuccess(List<String> data) {
                        fragment.hideProgressbar();
                    }

                    @Override
                    public void onFailure(Object error) {
                        fragment.hideProgressbar();
                    }
                });
            }
        };
    }

    public View.OnClickListener onClickLoginGoogle() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fragment.showProgressbar();
                typeLogin = MyAuthenticate.TYPE_LOGIN_GOOGLE;
                MyAuthenticate.getInstance().loginWith(MyAuthenticate.TYPE_LOGIN_GOOGLE, fragment.getActivity(), new SingleResultListener<String>() {
                    @Override
                    public void onSuccess(List<String> data) {
                        fragment.hideProgressbar();
                    }

                    @Override
                    public void onFailure(Object error) {
                        fragment.hideProgressbar();
                    }
                });
            }
        };
    }

    public View.OnClickListener onClickLogout() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.showProgressbar();
                typeLogin = MyAuthenticate.TYPE_LOGIN_GOOGLE;
                MyAuthenticate.getInstance().logout( new SingleResultListener<String>() {
                    @Override
                    public void onSuccess(List<String> data) {
                        fragment.hideProgressbar();
                    }

                    @Override
                    public void onFailure(Object error) {
                        fragment.hideProgressbar();
                    }
                });
            }
        };
    }
}
