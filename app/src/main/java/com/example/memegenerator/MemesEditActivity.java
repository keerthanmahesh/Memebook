package com.example.memegenerator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class MemesEditActivity extends AppCompatActivity {
    private static final String TAG = "MemesEditActivity";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memes_edit);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final ImageView imageView = findViewById(R.id.imageView);
        final Intent intent = getIntent();

        if (intent.hasExtra("meme_image_url")) {
            Log.i(TAG, Objects.requireNonNull(intent.getStringExtra("meme_image_url")));
            new LoadImage(imageView).execute(intent.getStringExtra("meme_image_url"));
        }

        final Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/impact.ttf");
        final TextView imageTopTextView = findViewById(R.id.image_top_textView);
        final TextView imageBottomTextView = findViewById(R.id.image_bottom_textView);
        imageTopTextView.setTypeface(typeface);
        imageBottomTextView.setTypeface(typeface);

        final EditText editTopText = findViewById(R.id.top_editText);
        final EditText editBottomText = findViewById(R.id.bottom_editText);
        Button renderButton = findViewById(R.id.render_button);
        renderButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(final View view) {
                imageTopTextView.setText(editTopText.getText());
                imageBottomTextView.setText(editBottomText.getText());
            }
        });

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(final View view) {
                Picasso.get().load(intent.getStringExtra("meme_image_url")).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Paint paint = new Paint();
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.WHITE);
                        paint.setTypeface(typeface);
                        paint.setTextAlign(Paint.Align.CENTER);
                        paint.setTextSize(convertToPixels(view.getContext(), 11));

                        Rect textRect = new Rect();
                        paint.getTextBounds(editTopText.getText(), 0, editTopText.getText().length(), textRect);

                        Canvas canvas = new Canvas(bitmap);

                        // If the text is bigger than the canvas , reduce the font size
                        if(textRect.width() >= (canvas.getWidth() - 4))     // the padding on either sides is considered as 4, so as to appropriately fit in the text
                            paint.setTextSize(convertToPixels(view.getContext(), 7));        // Scaling needs to be used for different dpi's

                        // Calculate the positions
                        int xPos = (canvas.getWidth()/2) - 2;     // -2 is for regulating the x position offset

                        // "- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
                        int yPos = (int) ((canvas.getHeight()/2) - ((paint.descent() + paint.ascent())/2)) ;

                        canvas.drawText(editTopText.getText().toString(), xPos, yPos, paint);

                        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, intent.getStringExtra("meme_name") , intent.getStringExtra("meme_tags"));
                        Snackbar snackbar = Snackbar.make(view, "Saved to Camera Roll.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Snackbar snackbar = Snackbar.make(view, "Unable to save to Camera Roll. Please check your connection.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {}
                });
            }
        });

        Button shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Share button clicked");
            }
        });
    }

    public static int convertToPixels(Context context, int nDP) {
        final float conversionScale = context.getResources().getDisplayMetrics().density;
        return (int) ((nDP*conversionScale) + 0.5f) ;
    }
}
