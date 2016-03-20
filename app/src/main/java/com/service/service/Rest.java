package com.service.service;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


/**
 * Created by vinicius on 19/03/16.
 */
public class Rest {
    private static final String BASE_URL = "http://wag.caixaalta.net/";
    public InterfaceService interfaceService;
    public static Rest restVinicius;

    public InterfaceService getInterfaceService() {
        if (interfaceService == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okClient = new OkHttpClient();
            okClient.interceptors().add(logging);
            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okClient)
                    .build();


            interfaceService = retrofit.create(InterfaceService.class);
        }
        return interfaceService;
    }

    public static Rest getRestVinicius() {
        if (restVinicius == null) {
            restVinicius = new Rest();
        }
        return restVinicius;
    }
}
   /*
    EXEMPLO DE COMO USAR

    final ProgressDialog dialog = ProgressDialog.show(this, "", "loading...");
    User user = new User(123, "John Doe");

    Call<GitResult> call =  RestVinicius.getRestVinicius().getInterfaceService().getUser("tom");

    call.enqueue(new CallbackVinicius<GitResult>() {
        @Override
        public void onResponse(Response<GitResult> response) {
            dialog.dismiss();

        }

        @Override
        public void onFailure(Throwable t) {
            String a ="oi";
            a =a;
        }
    });*/