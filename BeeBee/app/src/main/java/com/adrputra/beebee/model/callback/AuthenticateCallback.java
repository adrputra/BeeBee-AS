package com.adrputra.beebee.model.callback;

import com.google.gson.annotations.SerializedName;

public class AuthenticateCallback {
    @SerializedName("access_token")
    private String access_token;

    @SerializedName("scope")
    private  String scope;

    @SerializedName("token_type")
    private String token_type;

    @SerializedName("expires_in")
    private Integer expires_in;

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return token_type;
    }

    public void setTokenType(String token_type) {
        this.token_type = token_type;
    }

    public Integer getExpiresIn() {
        return expires_in;
    }

    public void setExpiresIn(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
