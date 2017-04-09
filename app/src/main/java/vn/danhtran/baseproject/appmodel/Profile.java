
package vn.danhtran.baseproject.appmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Profile implements Serializable {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("createdTime")
    @Expose
    private Long createdTime;
    @SerializedName("avatarId")
    @Expose
    private String avatarId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("oldPassword")
    @Expose
    private String oldPassword;
    @SerializedName("email")
    @Expose
    private String email;

    public Profile() {
    }

    public Profile(String avatarId) {
        this.avatarId = avatarId;
    }

    public Profile(String phone, String password, String firstName, String lastName) {
        this.phone = phone;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
    }

    public Profile(String avatarId, String firstName, String lastName, String phone, String password, String oldPassword, String email) {
        this.avatarId = avatarId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.password = password;
        this.oldPassword = oldPassword;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}