package com.example.myapplication8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final EditText nameInput = (EditText) findViewById(R.id.nameInput);
        final EditText birthDay = (EditText) findViewById(R.id.birthdayInput);
        final EditText gift = (EditText) findViewById(R.id.birthdayInput);
        final Button addButton = (Button) findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameInput.getText().toString().equals("")) {
                    Toast.makeText(AddActivity.this, "名字不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", nameInput.getText().toString());
                    bundle.putString("birthday", birthDay.getText().toString());
                    bundle.putString("gift", gift.getText().toString());
                    intent.putExtras(bundle);
                    setResult(0, intent);
                    finish();
                }
            }
        });
    }
}
