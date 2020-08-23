package com.example.memegenerator;

import android.util.Log;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APICall {
    private static final String TAG = "APICall";

    public static String GET(OkHttpClient okHttpClient, String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        Log.i(TAG, response.body().toString());

        return response.body().string();
    }
}
