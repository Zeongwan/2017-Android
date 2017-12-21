package com.example.myapplication9;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by D_ on 2017/12/19.
 */

public interface githubInterface {
    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String user);
    @GET("{repos}")
    Observable<List<Repos>> getRepos(@Path("repos") String repos);
}
