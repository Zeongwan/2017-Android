package com.example.myapplication4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GoodsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        Bundle bundle = this.getIntent().getExtras();
        final String name = bundle.getString("name");
        final String price = bundle.getString("price");
        final String type = bundle.getString("type");
        final String info = bundle.getString("info");
        final int picId = bundle.getInt("picId");
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
                Intent intent = new Intent(GoodsActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("price", price);
                bundle.putString("type", type);
                bundle.putString("info", info);
                bundle.putInt("picId", picId);
                intent.putExtras(bundle);
                setResult(100, intent);
                Toast.makeText(GoodsActivity.this, "商品已加到购物车", Toast.LENGTH_SHORT).show();
//                finish();
            }
        });
    }
}
