package com.example.pegawaiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private String JSON_STRING = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);

        getJSON();
    }

    private void getJSON(){
        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    }

    public class GetJSON extends AsyncTask<Void,Void,String>{
        ProgressDialog loading;

        protected void onPreExecute(){
            super.onPreExecute();
            loading = ProgressDialog.show(MainActivity.this,"Mengambil Data",
                    "Mohon Tunggu...",false,false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler rh = new RequestHandler();
            String s = rh.senGetRequest(Konfigurasi.URL_GET_ALL);
            return s;
        }

        protected void onPostExecute(String s){
            super.onPostExecute(s);
            loading.dismiss();
            JSON_STRING = s;
            showEmployee();
        }

    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list
                = new ArrayList<>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            for (int i=0; i<result.length(); i++){

                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.TAG_ID);
                String name = jo.getString(Konfigurasi.TAG_NAME);
                HashMap<String,String> employess = new HashMap<>();
                employess.put(Konfigurasi.TAG_ID,id);
                employess.put(Konfigurasi.TAG_NAME,name);
                list.add(employess);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        SimpleAdapter adapter = new SimpleAdapter(this,list,R.layout.list_item,
                new String[]{Konfigurasi.TAG_ID,Konfigurasi.TAG_NAME},
                new int[]{R.id.id,R.id.name});
        listView.setAdapter(adapter);
    }
}