package com.example.memegenerator;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.memegenerator.APIResponse.Data;
import com.example.memegenerator.APIResponse.MainResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MemesDisplayActivity extends AppCompatActivity {
    int pageNumber = 1;
    private static final String TAG = "MemesDisplayActivity";
    private MainResponse mainResponse = new MainResponse();
    private List<Data> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memes_display);

        String url = "https://alpha-meme-maker.herokuapp.com/1";
        getContents(url);
    }

    private void getContents(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);

                        try {
                            JSONObject mainObject = new JSONObject(response);
                            mainResponse.setCode(mainObject.getInt("code"));
                            mainResponse.setMessage(mainObject.getString("message"));
                            mainResponse.setNext(mainObject.getString("next"));
                            JSONArray dataArray = mainObject.getJSONArray("data");

                            JSONObject[] dataObjects = new JSONObject[dataArray.length()];

                            for (int i=0; i<dataArray.length(); i++) {
                                dataObjects[i] = dataArray.getJSONObject(i);
                                Data data = new Data();
                                data.setID(dataObjects[i].getInt("ID"));
                                data.setBottomText(dataObjects[i].getString("bottomText"));

                                URL url = new URL(dataObjects[i].getString("image"));
                                data.setImageURL(url);

                                data.setName(dataObjects[i].getString("name"));
                                data.setTags(dataObjects[i].getString("tags"));
                                data.setTopText(dataObjects[i].getString("topText"));
                                dataList.add(data);
                            }
                            mainResponse.setData(dataList);

                            RecyclerView recyclerView = findViewById(R.id.recycler_view);
                            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(dataList);
                            recyclerView.setHasFixedSize(true);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MemesDisplayActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(recyclerViewAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }
}
