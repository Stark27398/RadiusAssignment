package com.assignment.radius.radiusassignment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    List<User> userList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    Snackbar snackbar;
    String url="https://raw.githubusercontent.com/iranjith4/radius-intern-mobile/master/users.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler);

        recyclerViewAdapter=new RecyclerViewAdapter(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
        
                if(isConnectingToInternet()) {
                    new JsonTask().execute(url);
                }else{
                    showSnackBar("please check your internet connection",(RelativeLayout) findViewById(R.id.main));
                }


    }
//
    public void showSnackBar(String string, RelativeLayout linearLayout)
    {
        snackbar = Snackbar
                .make(linearLayout, string, Snackbar.LENGTH_INDEFINITE).
                        setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(!isConnectingToInternet()){
                                    snackbar.show();
//                                    showSnackBar("please check your internet connection",(RelativeLayout) findViewById(R.id.main));
                                }else {
                                    snackbar.dismiss();
                                    new JsonTask().execute(url);
                                }
                            }
                        });
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");

                }

                return buffer.toString();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            parseJSON(result);
        }
    }
    public void parseJSON(String json){
        if(isConnectingToInternet()) {
            try {
//            JSONArray j=new JSONArray(json);
                JSONObject j1 = new JSONObject(json);
                JSONArray j = j1.getJSONArray("results");
                for (int i = 0; i < j.length(); i++) {
                    JSONObject jsonObject = j.getJSONObject(i);

                    JSONObject name = jsonObject.getJSONObject("name");
                    String t = name.getString("title");
                    String fn = name.getString("first");
                    String ln = name.getString("last");
                    String n = t + " " + fn + " " + ln;
                    JSONObject age = jsonObject.getJSONObject("dob");
                    String a = age.getString("age");
                    JSONObject img = jsonObject.getJSONObject("picture");
                    String image = img.getString("thumbnail");
                    String large = img.getString("large");
                    String g = jsonObject.getString("gender");
                    String e = jsonObject.getString("email");
                    String p = jsonObject.getString("phone");
                    JSONObject us = jsonObject.getJSONObject("login");
                    String u = us.getString("username");
//            Toast.makeText(this, n+", "+a, Toast.LENGTH_SHORT).show();
                    User user = new User(n, a, image, large, u, g, e, p);
                    userList.add(user);
                    recyclerViewAdapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "Please check the internet connection", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
