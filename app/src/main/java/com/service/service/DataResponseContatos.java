package com.service.service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vinicius on 19/03/16.
 */
public class DataResponseContatos {
    @SerializedName("error")
    public boolean error;
   /* @SerializedName("contacts")
    public List<Contact> contacts;*/
    @SerializedName("")
    public List<Contact> contacts;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
