package com.tadhkirati.validator.api.retrofit;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.payload.LoginRequest;
import com.tadhkirati.validator.api.payload.LoginResponse;
import com.tadhkirati.validator.api.payload.TicketValidationPayload;
import com.tadhkirati.validator.models.Ticket;
import com.tadhkirati.validator.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/login/")
    Call<ApiResponse<LoginResponse>> login(@Body LoginRequest loginRequest);


    @POST("/api/user/update_info")
    Call<ApiResponse<User>> updateUserInfo(User user,@Header("Authorization ") String token);

    @POST("/api/validator/validate_ticket")
    Call<ApiResponse<Ticket>> validateTicket(@Body TicketValidationPayload validationPayload, @Header("Authorization") String authHeader);


}
