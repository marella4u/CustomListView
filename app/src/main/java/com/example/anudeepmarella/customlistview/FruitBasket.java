package com.example.anudeepmarella.customlistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by anudeepmarella on 7/8/15.
 */
@SuppressWarnings("ALL")
class FruitBasket extends BaseAdapter {
    private Context context;
    private String[] fruits;
    private int[] drawableReference;
    private LayoutInflater inflater;
    private LruCache<String, Bitmap> lruCache;
    private int lastPosition=-1;


    FruitBasket(Context context, String[] fruits, int[] drawableReference){
        this.context=context;
        this.fruits=fruits;
        this.drawableReference=drawableReference;
    }


    public class Fruits{
        private TextView fruitName;
        private ImageView fruitImage;
        Fruits(View v){
            fruitName= (TextView) v.findViewById(R.id.textView);
            fruitImage= (ImageView) v.findViewById(R.id.imageView);
        }
    }
    @Override
    public int getCount() {
        return fruits.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Fruits fruitObject;
        final int maxMemoryCache = (int) (Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize=maxMemoryCache/8;
        Log.d("anudeep","cache="+maxMemoryCache);
        lruCache=new LruCache<String,Bitmap>(cacheSize){

            @Override
            protected int sizeOf(String key, Bitmap value){
                Log.d("anudeep", "value.getByteCount()/1024=" + value.getByteCount() / 1024);
                return  value.getByteCount()/1024;
            }
        };
        if(convertView==null){
            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fruit_list_item,parent,false);
            fruitObject=new Fruits(convertView);
            convertView.setTag(fruitObject);
        }
        else{
            fruitObject= (Fruits) convertView.getTag();
        }

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.push_up : R.anim.push_down);
        convertView.startAnimation(animation);
        lastPosition = position;


        fruitObject.fruitName.setText(fruits[position]);

        final String imageKey = String.valueOf(drawableReference[position]);

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);

        if (bitmap != null) {
            Log.d("anudeep","setting bitmap from lrucache");
                fruitObject.fruitImage.setImageBitmap(bitmap);
        } else {
//            if(fruitObject.fruitImage!=null)
//            ((BitmapDrawable)fruitObject.fruitImage.getDrawable()).getBitmap().recycle();
            BitmapLoader bitmapLoader = new BitmapLoader(fruitObject.fruitImage,context);
            bitmapLoader.execute(drawableReference[position]);
        }
        return convertView;
    }


    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            lruCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return lruCache.get(key);
    }

}
