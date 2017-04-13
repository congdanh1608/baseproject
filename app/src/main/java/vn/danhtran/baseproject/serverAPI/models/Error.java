package vn.danhtran.baseproject.serverAPI.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tamhuynh on 3/6/16.
 */
public class Error {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private Integer status;
    @SerializedName("name")
    private String name;
    @SerializedName("code")
    private Integer code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", status = " + status + ", name = " + name + ", code = " + code + "]";
    }
}
