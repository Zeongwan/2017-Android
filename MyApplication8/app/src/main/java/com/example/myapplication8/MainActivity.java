package com.example.myapplication8;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    final private myDB myDatabase = new myDB(this, "app8_db", null, 1);
    private List<Map<String, Object>> list;
    final private int MAKEADD = 1;
    final private String TABLENAME = "app8Table";
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addButton = (Button) findViewById(R.id.addButton);
        list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = myDatabase.getReadableDatabase();
        myDatabase.onCreate(sqLiteDatabase);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddActivity.class), 0);
            }
        });
        Cursor cursor = sqLiteDatabase.query(TABLENAME, new String[]{"_id", "name", "birthday", "gift", "phone"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", cursor.getType(cursor.getColumnIndex("id")));
            map.put("name", cursor.getString(cursor.getColumnIndex("name")));
            map.put("birthday", cursor.getString(cursor.getColumnIndex("birthday")));
            map.put("gift", cursor.getString(cursor.getColumnIndex("gift")));
            map.put("phone", cursor.getString(cursor.getColumnIndex("phone")));
            list.add(map);
        }
        cursor.close();
        sqLiteDatabase.close();
        simpleAdapter = new SimpleAdapter(this, list, R.layout.item, new String[]{"name", "birthday", "gift"}, new int[] {R.id.name, R.id.birthday, R.id.gift});
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View dialog = layoutInflater.inflate(R.layout.dialog, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final TextView dialogName = (TextView) dialog.findViewById(R.id.dialogName);
                final EditText dialogBirthday = (EditText) dialog.findViewById(R.id.dialogBirthday);
                final EditText dialogGift = (EditText) dialog.findViewById(R.id.dialogGift);
                final TextView dialogPhone = (TextView) dialog.findViewById(R.id.dialogPhone);
                final Map<String, Object> map = list.get(i);
                dialogName.setText(map.get("name").toString());
                dialogBirthday.setText(map.get("birthday").toString());
                dialogGift.setText(map.get("gift").toString());
                dialogPhone.setText(map.get("phone").toString());
                final int pos = i;
                builder.setView(dialog).setPositiveButton("保存修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("birthday",dialogBirthday.getText().toString());
                        map.put("birthday", dialogBirthday.getText().toString());
                        map.put("gift", dialogGift.getText().toString());
                        contentValues.put("gift", dialogGift.getText().toString());
                        sqLiteDatabase.update(TABLENAME, contentValues, "name = ?", new String[]{dialogName.getText().toString()});
                        list.set(pos, map);
                        simpleAdapter.notifyDataSetChanged();
                        listView.invalidateViews();
                        sqLiteDatabase.close();
                    }
                }).setNegativeButton("放弃修改", null).show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = i;
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("确定删除吗？").setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
                        sqLiteDatabase.delete(TABLENAME, "name = ?", new String[]{list.get(pos).get("name").toString()});
                        list.remove(pos);
                        simpleAdapter.notifyDataSetChanged();
                        listView.invalidateViews();
                        sqLiteDatabase.close();
                    }
                }).setNegativeButton("取消", null).show();
                return true;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        switch (resultCode) {
            case (MAKEADD) :
                String name = bundle.getString("name");
                String birthday = bundle.getString("birthday");
                String gift = bundle.getString("gift");
                String phone = bundle.getString("phone");
                SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", name);
                contentValues.put("birthday", birthday);
                contentValues.put("gift", gift);
                if (sqLiteDatabase.insert(TABLENAME, null, contentValues) == -1) {
                    Toast.makeText(MainActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                sqLiteDatabase.close();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", name);
                map.put("birthday", birthday);
                map.put("gift", gift);
                map.put("phone", phone);
                list.add(map);
                simpleAdapter.notifyDataSetChanged();
                listView.invalidateViews();
                break;
            default:
                break;
        }

    }
}
