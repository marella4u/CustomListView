package com.example.anudeepmarella.customlistview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by anudeepmarella on 8/4/15.
 */
public class FruitInformation extends Activity {

    private int imageId;

    FruitInformation(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_information);
        imageId=Integer.parseInt(getIntent().getStringExtra("IMAGEID"));
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        BitmapLoader bitmapLoader = new BitmapLoader(imageView,this);
        bitmapLoader.execute(imageId);
    }


}
