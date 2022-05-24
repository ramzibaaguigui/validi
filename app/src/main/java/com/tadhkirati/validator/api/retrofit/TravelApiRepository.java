package com.tadhkirati.validator.api.retrofit;

import android.util.Log;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.payload.TodayTravelRequestPayload;
import com.tadhkirati.validator.models.Travel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

public class TravelApiRepository {

    public static void loadTodayTravels(@Header("Authorization") String accessToken,
                                        ResponseHandler<List<Travel>> responseHandler) {

        RetrofitClient.apiService.getTravels(accessToken)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<Travel>>> call, Response<ApiResponse<List<Travel>>> response) {
                        responseHandler.handleSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<Travel>>> call, Throwable t) {
                        Log.e("throwable", t.toString());
                        responseHandler.handleError();
                    }
                });
    }

}
