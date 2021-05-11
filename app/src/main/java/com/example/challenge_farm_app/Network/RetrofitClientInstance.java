package com.example.challenge_farm_app.Network;

import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.WIFI_SERVICE;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
   // private static final String BASE_URL = "http://192.168.1.106:4000";


    public static Retrofit getRetrofitInstance(String ip) {

        final String BASE_URL = "http://"+ip+":4000";
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
