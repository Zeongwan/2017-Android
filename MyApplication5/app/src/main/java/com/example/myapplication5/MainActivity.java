package com.example.myapplication5;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

import static com.example.myapplication5.R.raw.data;

public class MainActivity extends AppCompatActivity {
    protected DynamicReceiver dynamicReceiver;
    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> price = new ArrayList<String>();
    private ArrayList<String> type = new ArrayList<String>();
    private ArrayList<String> info = new ArrayList<String>();
    private String[] nameArray;
    private String[] priceArray;
    private String[] typeArray;
    private String[] infoArray;
    private int[] picIdArray = {R.drawable.enchatedforest, R.drawable.arla, R.drawable.devondale, R.drawable.kindle,
            R.drawable.waitrose, R.drawable.mcvitie, R.drawable.ferrero, R.drawable.maltesers, R.drawable.lindt, R.drawable.borggreve};
    private List<Map<String, Object>> list = new ArrayList<>();
    private List<Map<String, Object>> listViewList = new ArrayList<>();
    private SimpleAdapter simpleadapter;
    private final int REQUEST_CODE = 100;
    private ScaleInAnimationAdapter animationAdapter;
    static private RecyclerView mRecyclerView;
    static private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 读取文本
        BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(data)));
        String line = "";
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
                        name.add(line.substring(j, i).replace(" ", "").toLowerCase());
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
        nameArray = name.toArray(new String[name.size()]);

        priceArray = price.toArray(new String[price.size()]);
        typeArray = type.toArray(new String[type.size()]);
        infoArray = info.toArray(new String[info.size()]);
        for (int i = 0; i < nameArray.length; i++) {
            Map<String, Object> listItem = new LinkedHashMap<>();
            listItem.put("firstLetter", nameArray[i].charAt(0));
            listItem.put("name", nameArray[i]);
            list.add(listItem);
        }

        //产生广播
        Random random = new Random();
        int randomNum = random.nextInt(10);
        Intent intentBroadcast = new Intent("STATICTION");
        final Bundle broadCastBundle = new Bundle();
        broadCastBundle.putString("name", nameArray[randomNum]);
        broadCastBundle.putString("price", priceArray[randomNum]);
        broadCastBundle.putString("type", typeArray[randomNum]);
        broadCastBundle.putString("info", infoArray[randomNum]);
        broadCastBundle.putInt("picId", picIdArray[randomNum]);
        intentBroadcast.putExtras(broadCastBundle);
        sendBroadcast(intentBroadcast);
        // 动态
        EventBus.getDefault().register(this);
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction("DYNAMICTION");
        dynamicReceiver = new DynamicReceiver();
        registerReceiver(dynamicReceiver, dynamic_filter);

        // 设置Adapter部分
        final CommonAdapter myAdapter;
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new CommonAdapter<Map<String, Object>>(this, R.layout.item ,list) {
            @Override
            public void convert(MyViewholder myViewholder, Map<String, Object> s) {
                TextView firstLetter = myViewholder.getView(R.id.firstLetter);
                firstLetter.setText(s.get("firstLetter").toString());
                TextView name = myViewholder.getView(R.id.name);
                name.setText(s.get("name").toString());
            }
        };
        // 设置第一个item
        Map<String, Object> tempListItem = new LinkedHashMap<>();
        tempListItem.put("firstLetter", "*");
        tempListItem.put("name", "购物车");
        tempListItem.put("price", "价格");
        listViewList.add(0, tempListItem);
        // 设置动画
        animationAdapter = new ScaleInAnimationAdapter(myAdapter);
        animationAdapter.setDuration(1000);
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());

        // 设置购物车
        simpleadapter = new SimpleAdapter(this, listViewList, R.layout.shop_list, new String[] {"firstLetter", "name", "price"}, new int[] {R.id.shoplistFirstLetter, R.id.shoplistName, R.id.shoplistPrice});
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(simpleadapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("从购物车删除" +  listViewList.get(pos).get("name").toString() + "?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listViewList.remove(pos);
                        simpleadapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).show();
                return true;
            }
        });
        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        Bundle getBundle = this.getIntent().getExtras();
        if (getBundle != null && getBundle.getInt("isCart") == 1) {
            listView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            listView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecyclerView.getVisibility() == View.VISIBLE) {
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    floatingActionButton.setImageResource(R.mipmap.mainpage);
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    floatingActionButton.setImageResource(R.mipmap.shoplist);
                }
            }
        });
        myAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, GoodsActivity.class);
                intent.setAction("STATICTION");
                Bundle bundle = new Bundle();
                bundle.putString("name", nameArray[position]);
                bundle.putString("price", priceArray[position]);
                bundle.putString("type", typeArray[position]);
                bundle.putString("info", infoArray[position]);
                bundle.putInt("picId", picIdArray[position]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {
                Toast.makeText(MainActivity.this,"移除第" + position + "个商品",Toast.LENGTH_SHORT).show();
                myAdapter.removeItem(position);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, GoodsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", listViewList.get(i).get("name").toString());
                bundle.putString("price", listViewList.get(i).get("price").toString());
                bundle.putString("type", listViewList.get(i).get("type").toString());
                bundle.putString("info", listViewList.get(i).get("info").toString());
                bundle.putInt("picId", (int)listViewList.get(i).get("picId"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    @Subscribe
    public void onEventMainThread(Good good) {
        Map<String, Object> listItem = new LinkedHashMap<>();
        listItem.put("firstLetter", good.name.charAt(0));
        listItem.put("name", good.name);
        listItem.put("price", good.price);
        listItem.put("type", good.type);
        listItem.put("info", good.info);
        listItem.put("picId", good.picId);
        listViewList.add(listItem);
        simpleadapter.notifyDataSetChanged();
        mRecyclerView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dynamicReceiver != null)
            unregisterReceiver(dynamicReceiver);
        EventBus.getDefault().unregister(this);
    }
}
