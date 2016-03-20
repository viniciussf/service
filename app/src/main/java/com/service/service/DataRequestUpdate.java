package com.service.service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinicius on 19/03/16.
 */
public class DataRequestUpdate  {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    @SerializedName("ids")
    private List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
