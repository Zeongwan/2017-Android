package com.example.myapplication5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class GoodsActivity extends AppCompatActivity {
    private String name;
    private String price;
    private String type;
    private String info;
    private int picId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        Bundle bundle = this.getIntent().getExtras();
        name = bundle.getString("name");
        price = bundle.getString("price");
        type = bundle.getString("type");
        info = bundle.getString("info");
        picId = bundle.getInt("picId");
        TextView goodName = (TextView) findViewById(R.id.goodName);
        TextView goodPrice = (TextView) findViewById(R.id.goodPrice);
        TextView goodType = (TextView) findViewById(R.id.goodType);
        TextView goodInfo = (TextView) findViewById(R.id.goodInfo);
        ImageView goodPic = (ImageView) findViewById(R.id.goodPic);
        ImageView star = (ImageView) findViewById(R.id.star);
        ImageView goBack = (ImageView) findViewById(R.id.goBack);
        ImageView shop = (ImageView) findViewById(R.id.shop);
        goodPic.setImageResource(picId);
        goBack.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 退出当前的功能而不是重新开始一个activity
                finish();
                //startActivity(new Intent(ContactActivity.this, MainActivity.class));
            }
        }));
        star.setClickable(true);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTag() == "0") {
                    view.setTag("1");
                    view.setBackgroundResource(R.mipmap.empty_star);
                } else {
                    view.setTag("0");
                    view.setBackgroundResource(R.mipmap.full_star);
                }

            }
        });
        goodName.setText(name);
        goodPrice.setText(price);
        goodType.setText(type);
        goodInfo.setText(info);
        final ListView operate = (ListView) findViewById(R.id.moreInfo);
        String[] operation ={"一键下单","分享商品","不感兴趣","查看更多商品促销信息"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.operate_item, operation);
        operate.setAdapter(adapter);
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GoodsActivity.this, "商品已加到购物车", Toast.LENGTH_SHORT).show();
                Good good = new Good();
                good.name = name;
                good.price = price;
                good.type = type;
                good.info = info;
                good.picId = picId;
                EventBus.getDefault().post(good);
                Intent intentBroadcast = new Intent("DYNAMICTION");
                Bundle broadCastBundle = new Bundle();
                broadCastBundle.putString("name", name);
                broadCastBundle.putString("price", price);
                broadCastBundle.putString("type", type);
                broadCastBundle.putString("info", info);
                broadCastBundle.putInt("picId", picId);
                intentBroadcast.putExtras(broadCastBundle);
                sendBroadcast(intentBroadcast);
            }
        });
    }
}

