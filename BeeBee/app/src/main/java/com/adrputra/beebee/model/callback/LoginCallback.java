package com.adrputra.beebee.model.callback;

import com.google.gson.annotations.SerializedName;

public class LoginCallback {
    @SerializedName("X-IoT-JWT")
    private String jwt;

    @SerializedName("auth")
    private Boolean auth;

    @SerializedName("data")
    private LoginDataCallback data;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public LoginDataCallback getData() {
        return data;
    }

    public void setData(LoginDataCallback data) {
        this.data = data;
    }
}
