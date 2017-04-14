package vn.danhtran.baseproject.fragment.login.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vn.danhtran.baseproject.R;
import vn.danhtran.baseproject.SingleResultListener;
import vn.danhtran.baseproject.serverAPI.apiservice.APIManager;
import vn.danhtran.baseproject.serverAPI.models.LoginModel;
import vn.danhtran.baseproject.utils.DeprecatedUtil;
import vn.danhtran.sociallogin.MyAccessToken;
import vn.danhtran.sociallogin.SocialLogin;
import vn.danhtran.sociallogin.listener.SocialLoginListener;
import vn.danhtran.sociallogin.networks.FacebookNetwork;
import vn.danhtran.sociallogin.networks.GoogleNetwork;
import vn.danhtran.sociallogin.networks.SocialNetwork;


/**
 * Created by danhtran on 13/02/2017.
 */
public class MyAuthenticate implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public final static int TYPE_LOGIN_GOOGLE = 0;
    public final static int TYPE_LOGIN_FACEBOOK = 1;
    public final static int TYPE_LOGIN_OTHER = 3;

    private FragmentActivity activity;
    private SocialLogin socialLogin;
    private SingleResultListener<String> singleResultListener;

    private static volatile MyAuthenticate myAuthenticate;

    public static MyAuthenticate getInstance() {
        if (myAuthenticate == null) {
            synchronized (MyAuthenticate.class) {
                if (myAuthenticate == null) {
                    myAuthenticate = new MyAuthenticate();
                    SocialLogin.initialize();
                }
            }
        }

        return myAuthenticate;
    }


    private void initData(SingleResultListener<String> singleResultListener) {
        this.singleResultListener = singleResultListener;
        socialLogin = SocialLogin.getInstance();
    }

    //call to login with facebook / google
    public void loginWith(int typeLogin, FragmentActivity activity, SingleResultListener<String> singleResultListener) {
        initData(singleResultListener);
        switch (typeLogin) {
            case TYPE_LOGIN_FACEBOOK:
                loginWithFacebook(activity);
                break;
            case TYPE_LOGIN_GOOGLE:
//                loginWithGoogle(activity);

                break;
        }
    }

    private void loginWithFacebook(Activity activity) {
        List<String> fbScope = new ArrayList<>();
        fbScope.addAll(Collections.singletonList("public_profile"));
        FacebookNetwork facebookNetwork = new FacebookNetwork(activity, fbScope);
        socialLogin.addSocialNetwork(facebookNetwork);
        facebookNetwork.requestLogin(new SocialLoginListener() {
            @Override
            public void onSuccess(SocialNetwork socialNetwork) {
                MyAccessToken token = socialNetwork.geAccessToken();
                Logger.d(token.getToken());
            }

            @Override
            public void onFailure(SocialNetwork socialNetwork, Object error) {
                Logger.d(error);
            }
        });
    }

    private void loginWithGoogle(Activity activity) {
        GoogleNetwork googleNetwork = new GoogleNetwork(activity);
        socialLogin.addSocialNetwork(googleNetwork);
        googleNetwork.requestLogin(new SocialLoginListener() {
            @Override
            public void onSuccess(SocialNetwork socialNetwork) {
                MyAccessToken token = socialNetwork.geAccessToken();
                Logger.d(token.getToken());
            }

            @Override
            public void onFailure(SocialNetwork socialNetwork, Object error) {

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        socialLogin.onActivityResult(requestCode, resultCode, data);
    }

    /*public void isAuthenticated(FragmentActivity activity, SocialLoginListener<String> socialLoginListener) {
        initData(activity, socialLoginListener);
        List<String> includes = new ArrayList<>();
        includes.add(MyConstants.TYPE_INCLUDE.user.name());
        APIManager.instance().auth().isAuthenticated(includes, new SocialLoginListener<AccessTokenDataResponse>() {
            @Override
            public void onSuccess(List<AccessTokenDataResponse> data) {
                saveServerToken(null);
            }

            @Override
            public void onFailure(Object error) {
                saveServerError(error);
            }
        });
    }

    public void loginWithPhone(@NotNull String phone, @NotNull String password, FragmentActivity activity, SocialLoginListener<String> socialLoginListener) {
        initData(activity, socialLoginListener);
        List<String> includes = new ArrayList<>();
        includes.add(MyConstants.TYPE_INCLUDE.user.name());
        APIManager.instance().auth().login(phone, password, includes, new SocialLoginListener<AccessTokenDataResponse>() {
            @Override
            public void onSuccess(List<AccessTokenDataResponse> data) {
                if (data.get(0).getData() != null)
                    saveServerToken(data.get(0).getData().getAccessToken());
            }

            @Override
            public void onFailure(Object error) {
                saveServerError(error);
            }
        });
    }

    public void signUpWithPhone(@NotNull final String phone, @NotNull final String password, @NotNull String firstName, @NotNull String lastName,
                                final FragmentActivity activity, final SocialLoginListener<String> socialLoginListener) {
        initData(activity, socialLoginListener);

        APIManager.instance().auth().signUp(phone, password, firstName, lastName, new SocialLoginListener<IsSuccessModel>() {
            @Override
            public void onSuccess(List<IsSuccessModel> data) {
                loginWithPhone(phone, password, activity, socialLoginListener);
            }

            @Override
            public void onFailure(Object error) {
                saveServerError(error);
            }
        });
    }

    public void forgetPassword(@NotNull String email, FragmentActivity activity, SocialLoginListener<String> socialLoginListener) {
        initData(activity, socialLoginListener);
        APIManager.instance().auth().resetPassword(email, new SocialLoginListener<IsSuccessModel>() {
            @Override
            public void onSuccess(List<IsSuccessModel> data) {
                saveServerToken(null);
            }

            @Override
            public void onFailure(Object error) {
                saveServerError(error);
            }
        });
    }*/

    public void logout(SingleResultListener<String> singleResultListener) {
        initData(singleResultListener);
        SocialNetwork facebookNetwork = socialLogin.getSocialNetwork(SocialNetwork.Network.FACEBOOK);
        SocialNetwork googleNetwork = socialLogin.getSocialNetwork(SocialNetwork.Network.GOOGLE_PLUS);
        if (facebookNetwork != null)
            facebookNetwork.logout();
        if (googleNetwork != null)
            googleNetwork.logout();
        singleResultListener.onSuccess(null);           //temp - use it for logout from server
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void saveServerToken(String token) {
        List<String> list = new ArrayList<>();
        list.add(token);
        singleResultListener.onSuccess(list);
    }

    private void saveServerError(Object error) {
        singleResultListener.onFailure(error);
    }
}
