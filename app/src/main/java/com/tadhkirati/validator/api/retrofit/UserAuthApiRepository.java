package com.tadhkirati.validator.api.retrofit;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.payload.LoginRequest;
import com.tadhkirati.validator.api.payload.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAuthApiRepository {

    public static void login(LoginRequest loginRequest,
                             ResponseHandler<LoginResponse> handler) {
        RetrofitClient.apiService.login(loginRequest)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<ApiResponse<LoginResponse>> call, Response<ApiResponse<LoginResponse>> response) {
                        handler.handleSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<LoginResponse>> call, Throwable t) {
                        handler.handleError();
                    }
                });
    }




}
