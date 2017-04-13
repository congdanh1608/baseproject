package vn.danhtran.baseproject.serverAPI.models;

import com.google.gson.annotations.SerializedName;

import vn.danhtran.baseproject.appmodel.Profile;


/**
 * Created by tamhuynh on 2/21/16.
 */
public class Login {
    @SerializedName("token")
    private String token;
    @SerializedName("type")
    private String type;
    @SerializedName("profile")
    private Profile profile;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}

