package com.example.anudeepmarella.customlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Field;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView= (ListView) findViewById(R.id.listView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_up);
        listView.startAnimation(animation);
        String[] fruits=getResources().getStringArray(R.array.fruitsName);
        Field[] drawables = R.mipmap.class.getFields();
        final int[] drawableReference=new int[drawables.length];
        for (int i=0;i<drawables.length;i++){
            try {
                drawableReference[i]= drawables[i].getInt(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        FruitBasket fruitBasket=new FruitBasket(this,fruits,drawableReference);
        listView.setAdapter(fruitBasket);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,FruitInformation.class);
                intent.putExtra("IMAGEID",drawableReference[position]);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
