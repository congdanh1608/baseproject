package vn.danhtran.baseproject.fragment.login.authentication;

/**
 * Created by danhtran on 13/02/2017.
 */
public interface MyAuthenticateListener {
    void isAuthenticateFail();

    void isAuthenticateSucc();

    void loginSucces(String serverToken);

    void loginFail();

    void logoutSuccess();

    void logoutFail();
}
