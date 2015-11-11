package com.example.anudeepmarella.customlistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by anudeepmarella on 9/15/15.
 */
public class BitmapLoader extends AsyncTask<Integer,Void, Bitmap> {

    private final ImageView imageView;
    private BitmapFactory.Options options;
    private Bitmap bitmap;
    private Context context;

    BitmapLoader(ImageView imageView, Context context) {
        this.imageView = imageView;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        try {

            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(), params[0], options);
            Bitmap.createBitmap(options.outWidth, options.outHeight, Bitmap.Config.RGB_565);
            options.inSampleSize = subSamplingImage(options.outWidth, options.outHeight);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeResource(context.getResources(), params[0], options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            Log.d("anudeep", "bitmap is null");
        }
    }

    private int subSamplingImage(int originalWidth, int originalHeight) {
        int inSampleSize = 1;
        while (originalWidth / inSampleSize >= 400 && originalHeight / inSampleSize >= 200) {
            inSampleSize = inSampleSize * 2;
        }
        return inSampleSize;
    }
}