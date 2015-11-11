package com.example.anudeepmarella.customlistview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by anudeepmarella on 9/19/15.
 */
public class LoginPage extends Activity{

    private TextView username, password;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (TextView) findViewById(R.id.userName);
        password = (TextView) findViewById(R.id.password);
        logo = (ImageView) findViewById(R.id.logo);
        SharedPreferences saveCredentials = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = saveCredentials.edit();
        editor.putString("username",username.getText().toString());
        editor.putString("password",password.getText().toString());
        editor.commit();

    }
}
