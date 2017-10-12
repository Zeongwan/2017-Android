package com.example.myapplication3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.data)));
        String line = "";
        Boolean flag = true;
        ArrayList<String> name = new ArrayList<String>();
        final ArrayList<String> phone = new ArrayList<String>();
        final ArrayList<String> type = new ArrayList<String>();
        final ArrayList<String> belong = new ArrayList<String>();
        final ArrayList<String> background = new ArrayList<String>();
        int count = 0;
        try {
            while ((line = reader.readLine()) != null) {
                if (!flag && !line.isEmpty()) {
                    int j = 0;
                    for (int i = 0; i < line.length(); i++) {
                        if (i == line.length() - 1) {
                            background.add(line.substring(j, i + 1));
                        }
                        if (line.charAt(i) != '\t')
                            continue;
                        else {
                            if (line.charAt(j) == '\t') {
                                j++;
                            } else if (count % 5 == 0) {
                                name.add(line.substring(j, i));
                            } else if (count % 5 == 1) {
                                phone.add(line.substring(j, i));
                            } else if (count % 5 == 2) {
                                type.add(line.substring(j, i));
                            } else if (count % 5 == 3) {
                                belong.add(line.substring(j, i));
                            }
                            count++;
                            j = i + 1;
                        }
                    }
                } else {
                    flag = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        final ListView listview = (ListView) findViewById(R.id.list);
        final String[] nameArray = name.toArray(new String[name.size()]);
        final String[] phoneArray = phone.toArray(new String[phone.size()]);
        final String[] typeArray = type.toArray(new String[type.size()]);
        final String[] belongArray = belong.toArray(new String[belong.size()]);
        final String[] backgroundArray = background.toArray(new String[background.size()]);
        //listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, nameArray));
        final List<Map<String, Object>> listitems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < nameArray.length; i++) {
            Map<String, Object> listitem = new LinkedHashMap<>();
            listitem.put("FirstLetter", nameArray[i].charAt(0));
            listitem.put("name", nameArray[i]);
            listitems.add(listitem);
        }
        final SimpleAdapter simpleadapter = new SimpleAdapter(this, listitems, R.layout.item, new String[] {"FirstLetter", "name"}, new int[] {R.id.first, R.id.name});
        listview.setAdapter(simpleadapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", nameArray[i]);
                bundle.putString("phone", phoneArray[i]);
                bundle.putString("type", typeArray[i]);
                bundle.putString("belong", belongArray[i]);
                bundle.putString("background", backgroundArray[i]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("删除联系人").setMessage("确定删除联系人" + nameArray[i] + "?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listitems.remove(position);
                        simpleadapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
//                listitems.remove(i);
//                simpleadapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}