package com.git.github.Api;


import com.git.github.Model.BaseListResponse;
import com.git.github.Model.DetailsModel;
import com.git.github.Model.RespModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebServiceAPI {

    @GET("repos")
    Call<List<DetailsModel>> getDetails();
    @Headers("Content-Type: application/json")
    @POST("repos")
    Call<List<RespModel>> getRepos(@Query("access_token") String accessToken,@Body RespModel body);


}

