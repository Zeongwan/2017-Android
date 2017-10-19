package com.example.myapplication3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 读取文本
        BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.data)));
        String line = "";
        final ArrayList<String> name = new ArrayList<String>();
        final ArrayList<String> price = new ArrayList<String>();
        final ArrayList<String> type = new ArrayList<String>();
        final ArrayList<String> info = new ArrayList<String>();
        boolean flag = true;
        try {
            // 表示能够一直读取到信息的时候
            while ((line = reader.readLine()) != null) {
                if (flag) {
                    flag = false;
                    continue;
                }
                int j = 0;
                int count = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (count == 0) {
                        // 该循环结束于i的下一个字符是￥，然后出去以后再+1，最后i的位置就是￥
                        while (line.charAt(i + 1) != '¥')
                            i++;
                        name.add(line.substring(j, i).replace(" ", ""));
                    } else if (count == 1) {
                        // 该循环结束于i是空格，并且i的下一个也是空格
                        while (line.charAt(i) != ' ' || line.charAt(i + 1) != ' ')
                            i++;
                        price.add(line.substring(j, i).replace(" ", ""));
                        // 该循环结束的条件是i的下一个不是空格，出去以后i+1，下一步i的位置不是空格
                        while (line.charAt(i + 1) == ' ')
                            i++;
                    } else if (count == 2) {
                        // 结束与i是空格，并且i的下一个还是空格
                        while (line.charAt(i) != ' ' || line.charAt(i + 1) != ' ')
                            i++;
                        type.add(line.substring(j, i).replace(" ", ""));
                        // 该循环结束的条件是i的下一个不是空格，出去以后i+1，下一步i的位置不是空格
                        while (line.charAt(i + 1) == ' ')
                            i++;
                    } else {
                        while (i != line.length() - 1)
                            i++;
                        info.add(line.substring(j, i + 1).replace(" ", ""));
                    }
                    count++;
                    j = i + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String[] nameArray = name.toArray(new String[name.size()]);
        final String[] priceArray = price.toArray(new String[price.size()]);
        final String[] typeArray = type.toArray(new String[type.size()]);
        final String[] infoArray = info.toArray(new String[info.size()]);
        final List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < nameArray.length; i++) {
            Map<String, Object> listItem = new LinkedHashMap<>();
            listItem.put("FirstLetter", nameArray[i].charAt(0));
            listItem.put("name", nameArray[i]);
            list.add(listItem);
        }
        final SimpleAdapter simpleadapter = new SimpleAdapter(this, list, R.layout.item, new String[] {"FirstLetter", "name"}, new int[] {R.id.goodLetter, R.id.goodName});
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setContentView(R.layout.activity_main);
    }
}
