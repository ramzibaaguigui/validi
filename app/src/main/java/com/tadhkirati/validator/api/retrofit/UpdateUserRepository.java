package com.tadhkirati.validator.api.retrofit;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserRepository {

    public static void updateUser(User user, String accessToken, ResponseHandler<User> handler) {
        RetrofitClient.apiService.updateUserInfo(user, accessToken)
                .enqueue(new Callback<ApiResponse<User>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                        handler.handleSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                        handler.handleError();
                    }
                });
    }
}
