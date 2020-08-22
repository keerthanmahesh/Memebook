package com.example.memegenerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.example.memegenerator.APIResponse.MainResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListItemDetail extends Activity {
    private static final String TAG = "ListItemDetail";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onCreate(Bundle savedInstanceState) {
        MainResponse mainResponse = new MainResponse();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitem);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);

        MainActivity mainActivity = new MainActivity();
        String response = mainActivity.getContents();

        String[] dataList = new String[6];

        try {
            JSONObject mainObject = new JSONObject(response);
            JSONArray dataArray = mainObject.getJSONArray("data");

            for (int i = 0; i < 6; i++){
                dataList[i] = dataArray.getString(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, String.valueOf(dataList));

        TextView myTextView = findViewById(R.id.my_textview);
        myTextView.setText(dataList[position]);
    }
}
