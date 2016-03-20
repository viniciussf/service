package com.service.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private EditText etSegundos;
    private EditText etToken;
    private ArrayList<Integer> ids;
    private int tempo = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etSegundos = (EditText) findViewById(R.id.etSegundos);
        etToken = (EditText) findViewById(R.id.etToken);
        //starting service
        findViewById(R.id.start_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ServiceAndroid.class);
                startService(intent);
                if (!TextUtils.isEmpty(etSegundos.getText().toString())) {
                    ServiceAndroid.TEMPO = Integer.parseInt(etSegundos.getText().toString()) * tempo;
                }
                if (!TextUtils.isEmpty(etToken.getText().toString())) {
                    ServiceAndroid.token = etToken.getText().toString();
                }


            }
        });


        //service onDestroy callback method will be called
        findViewById(R.id.stop_Service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ServiceAndroid.class);
                stopService(intent);
            }
        });
        //retrieveContactName("Mirelle");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
