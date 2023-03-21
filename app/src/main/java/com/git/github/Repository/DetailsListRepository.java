package com.git.github.Repository;

import android.content.Context;
import android.util.Log;

import com.git.github.Api.WebServiceAPI;
import com.git.github.Common.Constants;
import com.git.github.Common.RetrofitAPIBuilder;
import com.git.github.Model.BaseListResponse;
import com.git.github.Model.DetailsModel;
import com.git.github.Model.ErrorResponse;
import com.git.github.Model.RespModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsListRepository {
  private GetCommonAPIDataSuccessCallBack getCommonAPIDataSuccessCallBack;
  Context context;

  public DetailsListRepository(Context context) {
    this.context = context;
  }
  public void getResp(String token,RespModel n) {

    Retrofit retrofit = RetrofitAPIBuilder.getInstance();
    WebServiceAPI authAPIServices = retrofit.create(WebServiceAPI.class);
    Call call = authAPIServices.getRepos(token,n);
    call.enqueue(new Callback<BaseListResponse<RespModel>>() {
      @Override
      public void onResponse(Call<BaseListResponse<RespModel>> call, Response<BaseListResponse<RespModel>> response) {
        BaseListResponse<RespModel> apiArrayResponse = response.body();
        if (apiArrayResponse != null) {
          getCommonAPIDataSuccessCallBack.getCommonAPIDataSuccess((List<DetailsModel>) apiArrayResponse);

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
  public void getDeatils(){
    Retrofit retrofit = RetrofitAPIBuilder.getInstance();
    WebServiceAPI authAPIServices = retrofit.create(WebServiceAPI.class);
    Call call =authAPIServices.getDetails();

    call.enqueue(new Callback<List<DetailsModel>>() {
      @Override
      public void onResponse(Call<List<DetailsModel>> call,
                             Response<List<DetailsModel>> response) {
        List<DetailsModel> apiArrayResponse = response.body();
        Log.i("TAG","response "+response.toString());
        Log.i("TAG","response "+response);

        if (apiArrayResponse != null) {
          getCommonAPIDataSuccessCallBack.getCommonAPIDataSuccess((List<DetailsModel>) apiArrayResponse);

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
        t.printStackTrace();
        getCommonAPIDataSuccessCallBack.getCommonAPIDataFailure(Constants.API_FAILURE+" "+t.toString());
      }
    });
  }



  public interface GetCommonAPIDataSuccessCallBack {
    void getCommonAPIDataSuccess(List<DetailsModel> models);

    void getCommonAPIDataFailure(String message);
  }

  public void setGetCommonAPIDetails(GetCommonAPIDataSuccessCallBack getCommonAPIDetails) {
    getCommonAPIDataSuccessCallBack = getCommonAPIDetails;

  }
}
