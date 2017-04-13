package vn.danhtran.baseproject.serverAPI.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tamhuynh on 2/21/16.
 */
public class LoginModel extends ErrorModel{

    @SerializedName("data")
    private Login data;


    public Login getData ()
    {
        return data;
    }

    public void setData (Login data)
    {
        this.data = data;
    }

}
