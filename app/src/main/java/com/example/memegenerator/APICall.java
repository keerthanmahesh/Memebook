package com.example.memegenerator;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.io.IOException;
import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APICall {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String GET(OkHttpClient client, String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return Objects.requireNonNull(response.body()).string();
    }
}
