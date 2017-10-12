package com.example.myapplication3;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("name");
        String phone = bundle.getString("phone");
        String type = bundle.getString("type");
        String belong = bundle.getString("belong");
        String background = bundle.getString("background");
        TextView nameText = (TextView) findViewById(R.id.name);
        nameText.setText(name);
        TextView phoneText = (TextView) findViewById(R.id.phone);
        phoneText.setText(phone);
        TextView typeText = (TextView) findViewById(R.id.type);
        typeText.setText(type);
        TextView belongText = (TextView) findViewById(R.id.belong);
        belongText.setText(belong);
        RelativeLayout backGround = (RelativeLayout) findViewById(R.id.background);
        String color = "#" + background;
        backGround.setBackgroundColor(Color.parseColor(color));
        ImageView star = (ImageView) findViewById(R.id.star);
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
        ImageView goBack = (ImageView) findViewById(R.id.goBack);
        goBack.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 退出当前的功能而不是重新开始一个activity
                finish();
                //startActivity(new Intent(ContactActivity.this, MainActivity.class));
            }
        }));
        final ListView operate = (ListView) findViewById(R.id.operate);
        String[] operation ={"编辑联系人","分享联系人","加入黑名单","删除联系人"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.operate_item, operation);
        operate.setAdapter(adapter);
    }
}
