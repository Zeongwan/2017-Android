package com.example.myapplication8;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private myDB myDatabase;
    private List<Map<String, Object>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addButton = (Button) findViewById(R.id.addButton);
        list = new ArrayList<>();
        myDatabase = new myDB(this, "app8_db", null, 1);
        SQLiteDatabase sqLiteDatabase = myDatabase.getReadableDatabase();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddActivity.class), 0);
            }
        });
        Cursor cursor = sqLiteDatabase.query("app8Table", new String[]{"id", "name", "birthday", "gift", "phone"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", cursor.getColumnIndex("id"));
            map.put("name", cursor.getColumnIndex("name"));
            map.put("birthday", cursor.getColumnIndex("birthday"));
            map.put("gift", cursor.getColumnIndex("gift"));
            map.put("phone", cursor.getColumnIndex("phone"));
            list.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.item, new String[]{"name", "birthday", "gift"}, new int[] {R.id.name, R.id.birthday, R.id.gift});
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });
    }
}
