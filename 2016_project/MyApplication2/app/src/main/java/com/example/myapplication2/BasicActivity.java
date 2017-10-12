package com.example.myapplication2;

/**
 * Created by 丁丁 on 2017/9/1 0001.
 */
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class BasicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final RadioButton student = (RadioButton) findViewById(R.id.student);
        final RadioButton teacher = (RadioButton) findViewById(R.id.teacher);
        final RadioButton community = (RadioButton) findViewById(R.id.community);
        RadioButton admin = (RadioButton) findViewById(R.id.admin);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = new String("Android");
                String Password = new String("123456");
                if (TextUtils.isEmpty(username.getText().toString())) {
                    Toast.makeText(BasicActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(BasicActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (username.getText().toString().equals(Username) && password.getText().toString().equals(Password)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BasicActivity.this);
                    builder.setTitle("提示").setMessage("登陆成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(BasicActivity.this, "确定按钮被点击", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(BasicActivity.this, "取消按钮被点击", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BasicActivity.this);
                    builder.setTitle("提示").setMessage("登陆失败").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(BasicActivity.this, "确定按钮被点击", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(BasicActivity.this, "取消按钮被点击", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BasicActivity.this, "学生身份被选中", Toast.LENGTH_SHORT).show();
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BasicActivity.this, "教师身份被选中", Toast.LENGTH_SHORT).show();
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BasicActivity.this, "社团身份被选中", Toast.LENGTH_SHORT).show();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BasicActivity.this, "管理者身份被选中", Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (student.isChecked()) {
                    Toast.makeText(BasicActivity.this, "学生注册功能尚未开启", Toast.LENGTH_SHORT).show();
                } else if (teacher.isChecked()) {
                    Toast.makeText(BasicActivity.this, "教师注册功能尚未开启", Toast.LENGTH_SHORT).show();
                } else if (community.isChecked()) {
                    Toast.makeText(BasicActivity.this, "社团注册功能尚未开启", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BasicActivity.this, "管理者注册功能尚未开启", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
