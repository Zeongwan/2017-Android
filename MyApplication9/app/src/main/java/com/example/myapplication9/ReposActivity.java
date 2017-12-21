package com.example.myapplication9;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

public class ReposActivity extends AppCompatActivity {
    private String reposUrl;
    private List<Repos> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        final RecyclerView reposRecyclerView = (RecyclerView) findViewById(R.id.reposRecyclerView);
        reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.reposProgressBar);
        final CommonAdapter commonAdapter = new CommonAdapter<Repos>(this, R.layout.repos, list) {
            @Override
            public void convert(MyViewholder myViewholder,Repos r) {
                TextView name = myViewholder.getView(R.id.reposName);
                name.setText(r.getName());
                TextView language = myViewholder.getView(R.id.reposLang);
                language.setText(r.getLanguage());
                TextView description = myViewholder.getView(R.id.reposDescrip);
                description.setText(r.getDescr());
            }
        };
        reposRecyclerView.setAdapter(commonAdapter);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        reposUrl = bundle.getString("url");
        final Retrofit retrofit = createRetrofit(reposUrl + "/");
        githubInterface githubinterface = retrofit.create(githubInterface.class);
        githubinterface.getRepos("repos")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Repos>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("repos", "一直在找");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("repos", "找不到仓库");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Repos> reposes) {
                        progressBar.setVisibility(View.INVISIBLE);
                        reposRecyclerView.setVisibility(View.VISIBLE);
                        for (int i = 0; i < reposes.size(); i++) {
                            commonAdapter.addData(reposes.get(i));
                        }
                    }
                });
    }
}
