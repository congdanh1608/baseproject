package vn.danhtran.baseproject.appmodel;

import java.io.Serializable;

/**
 * Created by danhtran on 07/03/2017.
 */
public class Session implements Serializable{
    private String token;
    private Profile profile;
    private int typeLogin;

    public Session(String token, Profile profile, int typeLogin) {
        this.token = token;
        this.profile = profile;
        this.typeLogin = typeLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getTypeLogin() {
        return typeLogin;
    }

    public void setTypeLogin(int typeLogin) {
        this.typeLogin = typeLogin;
    }
}
