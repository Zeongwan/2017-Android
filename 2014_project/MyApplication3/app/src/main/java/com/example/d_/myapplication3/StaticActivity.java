package com.example.d_.myapplication3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class StaticActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);
        ListView listView = (ListView) findViewById(R.id.listView);
        final int[] picture = {R.drawable.apple, R.drawable.banana, R.drawable.cherry, R.drawable.coco,
        R.drawable.kiwi, R.drawable.orange, R.drawable.pear, R.drawable.strawberry, R.drawable.watermelon};
        final String[] pictureName = {"apple", "banana", "cherry", "coco", "kiwi", "orange", "pear", "strawberry"
        , "watermelon"};
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < picture.length; i++) {
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("pic", picture[i]);
            temp.put("name", pictureName[i]);
            data.add(temp);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item, new String[] {"pic", "name"},
                new int[] {R.id.pic, R.id.picName});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("name", pictureName[i]);
                bundle.putInt("pic", picture[i]);
                Intent intent = new Intent("STATICTION");
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        });
    }
}
