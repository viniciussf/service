package com.service.service;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by vinicius on 10/01/16.
 */
public interface InterfaceService {

    // Request method and URL specified in the annotation
    // CallbackVinicius for the parsed response is the last parameter
/*
        @GET("/boox/public/getListaProfissionais")
        Call<DataResponseListaProfissionais> getProfissionais();
        @POST("/users/new")
        Call<User> createUser(@Body User user);*/

    @Headers("Content-Type: application/json")
    @POST("/contacts.json")
    Call<Contact[]> contatos(@Body DataResquestContatos user);
    @Headers("Content-Type: application/json")
    @POST("/update_stored_contacts")
    Call<DataResponseContatos> updateContatos(@Body DataRequestUpdate user);
}
