package com.tadhkirati.validator.api.retrofit;

import android.util.Log;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.payload.UpdateValidatorInfoPayload;
import com.tadhkirati.validator.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserApiRepository {

    public static void updateUser(UpdateValidatorInfoPayload payload, String accessToken, ResponseHandler<User> handler) {
        Log.i("USER_UPDATE_REQUEST", accessToken);
        Log.i("USER_UPDATE_REQUEST", payload.toString());
        RetrofitClient.apiService.updateUserInfo(payload, accessToken)
                .enqueue(new Callback<ApiResponse<User>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                        handler.handleSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<User>> call, Throwable t) {

                        Log.i("USER_UPDATE_ERROR", t.getCause().toString());
                        Log.i("USER_UPDATE_ERROR", t.getStackTrace().toString());
                        handler.handleError();
                    }
                });
    }
}
