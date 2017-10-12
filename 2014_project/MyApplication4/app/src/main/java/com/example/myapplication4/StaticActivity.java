package com.example.myapplication4;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DrawableUtils;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StaticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);
        ImageView pic = (ImageView) findViewById(R.id.pic);
        int[] draw = R.drawable;
        pic.setBackgroundResource(R.drawable.apple);
    }
}
