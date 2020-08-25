package com.example.memegenerator;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.util.Objects;

public class MemesEditActivity extends AppCompatActivity {
    private static final String TAG = "MemesEditActivity";
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memes_edit);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            Log.i(TAG, Objects.requireNonNull(intent.getStringExtra(Intent.EXTRA_TEXT)));
            new LoadImage(imageView).execute(intent.getStringExtra(Intent.EXTRA_TEXT));
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/impact.ttf");
        final TextView imageTopTextView = (TextView) findViewById(R.id.image_top_textView);
        final TextView imageBottomTextView = (TextView) findViewById(R.id.image_bottom_textView);
        imageTopTextView.setTypeface(typeface);
        imageBottomTextView.setTypeface(typeface);

        final EditText editTopText = (EditText) findViewById(R.id.top_editText);
        final EditText editBottomText = (EditText) findViewById(R.id.bottom_editText);
        Button renderButton = (Button) findViewById(R.id.render_button);
        renderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageTopTextView.setText(editTopText.getText());
                imageBottomTextView.setText(editBottomText.getText());
            }
        });

        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, "Saved to Camera Roll", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }
}
