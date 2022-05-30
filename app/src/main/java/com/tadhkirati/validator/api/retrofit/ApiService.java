package com.tadhkirati.validator.api.retrofit;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.payload.LoginRequest;
import com.tadhkirati.validator.api.payload.LoginResponse;
import com.tadhkirati.validator.api.payload.TicketValidationPayload;
import com.tadhkirati.validator.api.payload.UpdatePasswordPayload;
import com.tadhkirati.validator.models.Ticket;
import com.tadhkirati.validator.models.Travel;
import com.tadhkirati.validator.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    String AUTH_HEADER = "Authorization";

    @POST("/api/validator/login/")
    Call<ApiResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

    @POST("/api/user/update_infos")
    Call<ApiResponse<User>> updateUserInfo(@Body User user, @Header(AUTH_HEADER) String token);

    @POST("/api/validator/validate_ticket")
    Call<ApiResponse<Ticket>> validateTicket(@Body TicketValidationPayload validationPayload,
                                             @Header(AUTH_HEADER) String authHeader);

    @GET("/api/validator/todayTravels")
    Call<ApiResponse<List<Travel>>> getTodayTravels(@Header(AUTH_HEADER) String accessToken);
    // TODO: WE STILL NEED TO ADD METHOD PARAMETERS HERE

    @GET("/api/validator/tickets/{travelId}")
    Call<ApiResponse<List<Ticket>>> getTravelTickets(@Path("travelId") Long travelId,
                                                     @Header(AUTH_HEADER) String accessToken);

    @POST("/api/tickets/validate")
    Call<ApiResponse<Ticket>> validateTicket(@Header(AUTH_HEADER) String accessToken, @Body TicketValidationPayload payload);

    @POST("/api/user/update_password")
    Call<ApiResponse<User>> updatePassword(@Header(AUTH_HEADER) String accessToken, @Body UpdatePasswordPayload payload);


}
