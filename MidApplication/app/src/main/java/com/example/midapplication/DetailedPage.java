package com.example.midapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class DetailedPage extends AppCompatActivity {
    private TextView name;
    private ImageView pic;
    private TextView bdDate;
    private Spinner nation;
    private TextView home;
    private TextView intro;
    private EditText editText;
    private AlertDialog.Builder dialog;

    private void findId() {
        name = (TextView) findViewById(R.id.name);
        pic = (ImageView) findViewById(R.id.pic);
        bdDate = (TextView) findViewById(R.id.bdDate);
        nation = (Spinner) findViewById(R.id.nation);
        home = (TextView) findViewById(R.id.home);
        intro = (TextView) findViewById(R.id.intro);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_page);
        findId();
        Bundle bundle = this.getIntent().getExtras();
        name.setText(bundle.getString("name"));
        pic.setImageResource(bundle.getInt("pic"));
        bdDate.setText(bundle.getString("bdDate"));
        String nationString = bundle.getString("nation");
        if (nationString.equals("蜀")) {
            nation.setSelection(2, true);
        } else if (nationString.equals("吴")) {
            nation.setSelection(0, true);
        } else if (nationString.equals("魏")) {
            nation.setSelection(1, true);
        } else if (nationString.equals("东汉")) {
            nation.setSelection(3, true);
        } else {
            nation.setSelection(4, true);
        }
        home.setText(bundle.getString("home"));
        intro.setText(bundle.getString("intro"));
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewData(name);
            }
        });
        bdDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewData(bdDate);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewData(home);
            }
        });
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewData(intro);
            }
        });

    }
    private void setNewData(final TextView textView) {
        editText = new EditText(this);
        dialog = new AlertDialog.Builder(this);
        dialog.setView(editText);
        dialog.setPositiveButton("确定修改", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(editText.getText());
            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }
}
