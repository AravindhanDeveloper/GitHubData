package com.git.github.Repository;

import android.content.Context;

import com.git.github.Activity.AddedRepositoryActivity;
import com.git.github.Api.WebServiceAPI;
import com.git.github.Common.Constants;
import com.git.github.Common.RetrofitAPIBuilder;
import com.git.github.Model.BaseListResponse;
import com.git.github.Model.ErrorResponse;
import com.git.github.Model.RespModel;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateRepository {

    private GetCommonAPIDataSuccessCallBack getCommonAPIDataSuccessCallBack;
    Context context;

    public CreateRepository(AddedRepositoryActivity addedRepositoryActivity) {
    }

    public void getResp(String token, RespModel n) {

        Retrofit retrofit = RetrofitAPIBuilder.getInstance();
        WebServiceAPI authAPIServices = retrofit.create(WebServiceAPI.class);
        Call call = authAPIServices.getRepos(token,n);
        call.enqueue(new Callback<BaseListResponse<RespModel>>() {
            @Override
            public void onResponse(Call<BaseListResponse<RespModel>> call, Response<BaseListResponse<RespModel>> response) {
                BaseListResponse<RespModel> apiArrayResponse = response.body();
                if (apiArrayResponse != null) {
                    getCommonAPIDataSuccessCallBack.getCommonAPIDataSuccess( apiArrayResponse);

                } else {
                    String message = "";

                    try {
                        Gson gson = new Gson();

                        ErrorResponse errorResponse = gson.fromJson(
                                response.errorBody().string(),
                                ErrorResponse.class);
                        message = errorResponse.getMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    getCommonAPIDataSuccessCallBack.getCommonAPIDataFailure(message);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                getCommonAPIDataSuccessCallBack.getCommonAPIDataFailure(Constants.API_FAILURE+" "+t.toString());
            }
        });
    }



    public interface GetCommonAPIDataSuccessCallBack {
        void getCommonAPIDataSuccess(BaseListResponse<RespModel> models);

        void getCommonAPIDataFailure(String message);
    }

    public void setGetCommonAPIDetails(GetCommonAPIDataSuccessCallBack getCommonAPIDetails) {
        getCommonAPIDataSuccessCallBack = getCommonAPIDetails;

    }
}
