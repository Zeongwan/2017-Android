package com.example.myapplication9;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.myapplication9.makeConnection.createRetrofit;

public class MainActivity extends AppCompatActivity {
    private EditText nameInput;
    private Button clearButton;
    private Button fetchButton;
    private RecyclerView recyclerView;
    private List<User> list = new ArrayList<>();
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameInput = (EditText) findViewById(R.id.nameInput);
        clearButton = (Button) findViewById(R.id.clearButton);
        fetchButton = (Button) findViewById(R.id.fetchButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 设置布局
        final CommonAdapter commonAdapter = new CommonAdapter<User>(this, R.layout.cardview, list) {
            @Override
            public void convert(MyViewholder myViewholder,User s) {
                TextView name = myViewholder.getView(R.id.cardName);
                name.setText(name.getText().toString() + s.getName());
                TextView id = myViewholder.getView(R.id.cardId);
                id.setText(id.getText().toString() + s.getId());
                TextView blog = myViewholder.getView(R.id.cardBlog);
                blog.setText(blog.getText().toString() + s.getBlog());
            }
        };
        recyclerView.setAdapter(commonAdapter);
        // 设置按钮
        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                final Retrofit retrofit = createRetrofit("https://api.github.com/");
                githubInterface githubinterface = retrofit.create(githubInterface.class);
                githubinterface.getUser(nameInput.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<User>() {
                            @Override
                            public void onCompleted() {
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this, "确认是否有该用户存在", Toast.LENGTH_SHORT).show();
                                Log.d("error", "出现错误");
                                progressBar.setVisibility(View.INVISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onNext(User user) {
                                commonAdapter.addData(user);
                            }
                        });
            }
        });
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                User clickUser = (User) commonAdapter.getItem(position);
                final String baseUrl = clickUser.getUrl();
                Bundle bundle = new Bundle();
                bundle.putString("url", baseUrl);
                Intent intent = new Intent(MainActivity.this, ReposActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
            @Override
            public void onLongClick(int position) {
                final int removePos = position;
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("确定删除吗？").setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        commonAdapter.removeItem(removePos);
                    }
                }).setNegativeButton("取消", null).show();
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameInput.setText("");
                commonAdapter.removeAll();
            }
        });
    }
}
