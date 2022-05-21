package com.tadhkirati.validator.api.retrofit;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.models.Travel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TravelApiRepository {

    public static void loadTravels(String accessToken,
                                   ResponseHandler<List<Travel>> responseHandler) {
        RetrofitClient.apiService.getTravels()
                .enqueue(new Callback<ApiResponse<List<Travel>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<Travel>>> call, Response<ApiResponse<List<Travel>>> response) {
                        responseHandler.handleSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<Travel>>> call, Throwable t) {
                        responseHandler.handleError();
                    }
                });
    }

}
