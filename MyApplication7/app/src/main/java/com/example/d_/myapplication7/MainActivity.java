package com.example.d_.myapplication7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText pswFirstInput;
    EditText pswFirstConfirm;
    Button pswFirstOk;
    Button pswFirstClear;
    EditText pswSecondInput;
    Button pswSecondOk;
    Button pswSecondClear;
    String psw;
    private void findId() {
        pswFirstInput = (EditText) findViewById(R.id.newPsw);
        pswFirstConfirm = (EditText) findViewById(R.id.confirmPsw);
        pswFirstOk = (Button) findViewById(R.id.pswOk);
        pswFirstClear = (Button) findViewById(R.id.pswClear);
        pswSecondInput = (EditText) findViewById(R.id.pswInput);
        pswSecondOk = (Button) findViewById(R.id.pswInputOk);
        pswSecondClear = (Button) findViewById(R.id.pswInputClear);

    }
    private void changeVisable() {
        pswSecondInput.setVisibility(View.VISIBLE);
        pswSecondOk.setVisibility(View.VISIBLE);
        pswSecondClear.setVisibility(View.VISIBLE);
        pswFirstConfirm.setVisibility(View.INVISIBLE);
        pswFirstInput.setVisibility(View.INVISIBLE);
        pswFirstOk.setVisibility(View.INVISIBLE);
        pswFirstClear.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findId();
        SharedPreferences sharedPreferences = this.getSharedPreferences("MY_PREFERENCE", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        psw = sharedPreferences.getString("psw", "");
        // 没输入密码情况
        if (psw.equals("")) {
            pswFirstOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pswFirstInput.getText().toString().equals(pswFirstConfirm.getText().toString())) {
                        editor.putString("psw", pswFirstInput.getText().toString());
                        editor.commit();
                        changeVisable();
                        psw = pswFirstInput.getText().toString();
                    } else {
                        Toast.makeText(MainActivity.this, "两次密码输入不正确", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            pswFirstClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pswFirstInput.setText("");
                    pswFirstConfirm.setText("");
                }
            });
        } else {
            changeVisable();
        }
        pswSecondOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (psw.equals(pswSecondInput.getText().toString())) {
                    startActivity(new Intent(MainActivity.this, FileEdit.class));
                }
            }
        });
        pswSecondClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pswSecondInput.setText("");
            }
        });
    }
}
