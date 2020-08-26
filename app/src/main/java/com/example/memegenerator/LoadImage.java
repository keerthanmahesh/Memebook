package com.example.memegenerator;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

class LoadImage extends AsyncTask<String, Void, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    ImageView bmImage;
    Bitmap bitmap;

    public LoadImage(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    public LoadImage(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        this.bitmap = result;
        bmImage.setImageBitmap(result);
    }
}