package vn.danhtran.baseproject.serverAPI.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tamhuynh on 2/15/16.
 */

public class ErrorModel {
    @SerializedName("error")
    private Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}

