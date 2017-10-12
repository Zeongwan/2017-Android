package com.example.myapplication2;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        final RadioButton student = (RadioButton) findViewById(R.id.student);
        final RadioButton teacher = (RadioButton) findViewById(R.id.teacher);
        final RadioButton community = (RadioButton) findViewById(R.id.community);
        final TextInputLayout usernameInput = (TextInputLayout) findViewById(R.id.usenameInput);
        final TextInputLayout passwordInput = (TextInputLayout) findViewById(R.id.passwordInput);
        RadioButton admin = (RadioButton) findViewById(R.id.admin);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = new String("Android");
                String Password = new String("123456");
                if (TextUtils.isEmpty(username.getText().toString())) {
                    usernameInput.setErrorEnabled(true);
                    usernameInput.setError("用户名不能为空");
                    passwordInput.setErrorEnabled(false);
                }
                else if (TextUtils.isEmpty(password.getText().toString())) {
                    passwordInput.setErrorEnabled(true);
                    passwordInput.setError("密码不能为空");
                    usernameInput.setErrorEnabled(false);
                }
                else if (username.getText().toString().equals(Username) && password.getText().toString().equals(Password)) {
                    Snackbar.make(view, "登陆成功", Snackbar.LENGTH_LONG)
                            .setAction("按钮", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                    usernameInput.setErrorEnabled(false);
                    passwordInput.setErrorEnabled(false);
                }
                else {
                    Snackbar.make(view, "登陆失败", Snackbar.LENGTH_LONG)
                            .setAction("按钮", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                    usernameInput.setErrorEnabled(false);
                    passwordInput.setErrorEnabled(false);
                }
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "学生身份被选中", Snackbar.LENGTH_LONG)
                        .setAction("按钮", new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "教师身份被选中", Snackbar.LENGTH_LONG)
                        .setAction("按钮", new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "社团身份被选中", Snackbar.LENGTH_LONG)
                        .setAction("按钮", new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "管理者身份被选中", Snackbar.LENGTH_LONG)
                        .setAction("按钮", new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (student.isChecked()) {
                    Snackbar.make(view, "学生注册功能尚未开启", Snackbar.LENGTH_LONG)
                            .setAction("按钮", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                } else if (teacher.isChecked()) {
                    Snackbar.make(view, "教师注册功能尚未开启", Snackbar.LENGTH_LONG)
                            .setAction("按钮", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                } else if (community.isChecked()) {
                    Snackbar.make(view, "社团注册功能尚未开启", Snackbar.LENGTH_LONG)
                            .setAction("按钮", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                } else {
                    Snackbar.make(view, "管理者注册功能尚未开启", Snackbar.LENGTH_LONG)
                            .setAction("按钮", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(MainActivity.this, "按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
            }
        });
    }
}

