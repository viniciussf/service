package com.service.service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinicius on 19/03/16.
 */
public class DataResquestContatos {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
