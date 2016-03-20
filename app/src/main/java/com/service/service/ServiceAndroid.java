package com.service.service;

import android.app.Service;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by vinicius on 18/03/2016.
 */
public class ServiceAndroid extends Service {
    public static int TEMPO;

    private static final String TAG = "Serviceeeeeeeeeeeee";
    private ArrayList<Integer> ids;
    private boolean isRunning = false;
    public static  String token="56ec5ef110f0ed283c8b4567";
    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {


                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                for (; ; ) {
                    try {
                        service();
                        if (TEMPO > 0) {
                            Thread.sleep(TEMPO);

                        } else {
                            Thread.sleep(60000);
                        }


                    } catch (Exception e) {
                    }

                    if (isRunning) {
                        Log.i(TAG, "Service running");
                    }
                    // stopSelf();
                }


                //Stop service once it finishes its task

            }
        }).start();

        return Service.START_STICKY;
    }


    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;

        Log.i(TAG, "Service onDestroy");
    }

    public void service() {
        DataResquestContatos db = new DataResquestContatos();
        db.setToken(token);
        Call<Contact[]> call = Rest.getRestVinicius().getInterfaceService().contatos(db);

        call.enqueue(new Callback<Contact[]>() {

            @Override
            public void onResponse(Response<Contact[]> response) {
                Contact[] db;
                if (response.isSuccess()) {
                    db = response.body();

                    ids = new ArrayList<Integer>();

                    if (db != null) {
                        for (int i = 0; i < db.length; i++) {
                            //id+nomeEmpresa+
                            writeContact(db[i].getName(), db[i].getPhone(), db[i].getId());
                        }
                        serviceUpdate();
                    }

                }
            }

            @Override
            public void onFailure(Throwable t) {
                String a = "a";
                a = a;
                Toast.makeText(ServiceAndroid.this, "requisição nao realizada get contatos", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void serviceUpdate() {
        DataRequestUpdate db = new DataRequestUpdate();
        db.setToken(token);
        db.setIds(ids);
        Call<DataResponseContatos> call = Rest.getRestVinicius().getInterfaceService().updateContatos(db);

        call.enqueue(new Callback<DataResponseContatos>() {

            @Override
            public void onResponse(Response<DataResponseContatos> response) {
                DataResponseContatos db;
                if (response.isSuccess()) {
                    db = response.body();
                    if (db != null && !db.isError()) {
                        Toast.makeText(ServiceAndroid.this, "sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ServiceAndroid.this, "erro veio true", Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Throwable t) {
                String a = "a";
                a = a;
            }
        });

    }

    private void writeContact(String displayName, String number, String id) {


        ArrayList contentProviderOperations = new ArrayList();
        //insert raw contact using RawContacts.CONTENT_URI
        contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
        //insert contact display name using Data.CONTENT_URI
        contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName).build());
        //insert mobile number using Data.CONTENT_URI
        contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0).withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number).withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());
        try {
            getApplicationContext().getContentResolver().
                    applyBatch(ContactsContract.AUTHORITY, contentProviderOperations);
            ids.add(Integer.valueOf(id));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }
}