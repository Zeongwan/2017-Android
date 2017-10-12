package com.example.d_.myapplication3;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DynamicActivity extends AppCompatActivity {
    static int flag = 1;
    protected DynamicReceiver dynamicReceiver = new DynamicReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        final Button registerButton = (Button) findViewById(R.id.registerButton);
        final Button sendButton = (Button) findViewById(R.id.sendButton);
        final EditText editText = (EditText) findViewById(R.id.textInput);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerButton.getText().toString().equals("Register Broadcast")) {
                    registerButton.setText("Unregister Broadcast");
                    IntentFilter dynamic_filter = new IntentFilter();
                    dynamic_filter.addAction("DYNAMICACTION");
                    registerReceiver(dynamicReceiver, dynamic_filter);
                } else {
                    registerButton.setText("Register Broadcast");
                    unregisterReceiver(dynamicReceiver);
                }
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("text", editText.getText().toString());
                Intent intent = new Intent("DYNAMICACTION");
                intent.putExtras(bundle);
                sendBroadcast(intent);
            }
        });
    }
}
