package com.example.myapplication2;

import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.dialog, (LinearLayout) findViewById(R.id.dialog));
        final ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        final ImageView picButton = (ImageView) findViewById(R.id.pic);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio);
        final RadioButton student = (RadioButton) findViewById(R.id.student);
        final RadioButton staff = (RadioButton) findViewById(R.id.staff);
        final Button shoot = (Button) dialogLayout.findViewById(R.id.shoot);
        final Button select = (Button) dialogLayout.findViewById(R.id.select);
        final Button login = (Button) findViewById(R.id.login);
        final Button logup = (Button) findViewById(R.id.logup);
        final TextInputLayout numberInputLayout = (TextInputLayout) findViewById(R.id.numberInputLayout);
        final TextInputLayout pswInputLayout = (TextInputLayout) findViewById(R.id.pswInputLayout);
        final EditText numberInput = (EditText) findViewById(R.id.numberInput);
        final EditText pswInput = (EditText) findViewById(R.id.pswInput);

        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "您选择了[拍摄]", Toast.LENGTH_SHORT).show();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "您选择了[从相册选择]", Toast.LENGTH_SHORT).show();
            }
        });

        picButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // 一定要实例化啊！！！！！
                builder.setTitle("上传头像").setView(dialogLayout).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "取消按钮被点击", Toast.LENGTH_SHORT).show();
                    }
                });
                final AlertDialog dialog = builder.show();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (student.isChecked()) {
                    Snackbar.make(constraintLayout, "您选择了学生", Snackbar.LENGTH_SHORT)
                    .setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "确定按钮被点击", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                } else if (staff.isChecked()) {
                    Snackbar.make(constraintLayout, "您选择了教职工", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(MainActivity.this, "确定按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "123456";
                String psw = "6666";
                if (TextUtils.isEmpty(numberInput.getText().toString())) {
                    numberInputLayout.setEnabled(true);
                    numberInputLayout.setError("学号不能为空");
                    numberInputLayout.setEnabled(false);
                } else if (TextUtils.isEmpty(pswInput.getText().toString())) {
                    pswInputLayout.setEnabled(true);
                    pswInputLayout.setError("密码不能为空");
                    pswInputLayout.setEnabled(false);
                } else if (numberInput.getText().toString().equals(number) && pswInput.getText().toString().equals(psw)) {
                    Snackbar.make(constraintLayout, "登陆成功", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(MainActivity.this, "确定按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                } else {
                    Snackbar.make(constraintLayout, "登陆失败", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(MainActivity.this, "确定按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                }
            }
        });
        logup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (student.isChecked()) {
                    Snackbar.make(constraintLayout, "学生注册功能未开启", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(MainActivity.this, "确定按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                } else if (staff.isChecked()) {
                    Snackbar.make(constraintLayout, "教职工注册功能尚未开启", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(MainActivity.this, "确定按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                }
            }
        });
    }
}
