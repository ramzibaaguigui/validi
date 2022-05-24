package com.tadhkirati.validator.api.retrofit;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.payload.LoginRequest;
import com.tadhkirati.validator.api.payload.LoginResponse;
import com.tadhkirati.validator.api.payload.TicketValidationPayload;
import com.tadhkirati.validator.api.payload.TodayTravelRequestPayload;
import com.tadhkirati.validator.models.Ticket;
import com.tadhkirati.validator.models.Travel;
import com.tadhkirati.validator.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/validator/login/")
    Call<ApiResponse<LoginResponse>> login(@Body LoginRequest loginRequest);


    @POST("/api/user/update_info")
    Call<ApiResponse<User>> updateUserInfo(User user,@Header("Authorization ") String token);

    @POST("/api/validator/validate_ticket")
    Call<ApiResponse<Ticket>> validateTicket(@Body TicketValidationPayload validationPayload, @Header("Authorization") String authHeader);


    @GET("/api/validator/todayTravels")
    Call<ApiResponse<List<Travel>>> getTravels(@Header("Authorization") String accessToken);
    // TODO: WE STILL NEED TO ADD METHOD PARAMETERS HERE




}
