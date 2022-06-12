package com.tadhkirati.validator.api.retrofit;

import android.util.Log;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.payload.TicketValidationPayload;
import com.tadhkirati.validator.models.Ticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketApiRepository {

    public static void getTravelTickets(Long travelId, String accessToken, ResponseHandler<List<Ticket>> handler) {
        RetrofitClient.apiService.getTravelTickets(travelId, accessToken)
                .enqueue(new Callback<ApiResponse<List<Ticket>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<Ticket>>> call, Response<ApiResponse<List<Ticket>>> response) {
                        handler.handleSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<List<Ticket>>> call, Throwable t) {
                        Log.i("TICKET_ERROR", String.valueOf(t.getMessage()));
                        Log.i("TICKET_ERROR", String.valueOf(t.getStackTrace()));
                        handler.handleError();
                    }
                });
    }

    public static void validateTicket(String accessToken, Long travelId, String qrCode,
                                      ResponseHandler<Ticket> handler) {
        var payload = TicketValidationPayload.createPayload()
                .withQrCode(qrCode)
                .withTravelId(travelId);
        RetrofitClient.apiService.validateTicket(accessToken, payload)
                .enqueue(new Callback<ApiResponse<Ticket>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Ticket>> call, Response<ApiResponse<Ticket>> response) {
                        handler.handleSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Ticket>> call, Throwable t) {
                        Log.e("TICKET_ERROR", t.getMessage());
                        Log.i("TICKET_ERROR", t.getStackTrace().toString());
                        handler.handleError();
                    }
                });
    }

    public static void validateTicket2(String accessToken, Long travelId,
                                       String qrCodeToken, ResponseHandler<Ticket> handler) {
        var payload = TicketValidationPayload.createPayload()
                .withTravelId(travelId)
                .withQrCode(qrCodeToken);
        RetrofitClient.apiService.validateTicket(accessToken, payload)
                .enqueue(new Callback<ApiResponse<Ticket>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Ticket>> call, Response<ApiResponse<Ticket>> response) {
                        Log.i("TICKET_VALIDATION", String.valueOf(response));
                        handler.handleSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Ticket>> call, Throwable t) {
                        Log.i("ERROR_VALIDATION", t.toString());
                        Log.i("ERROR_VALIDATION", t.getLocalizedMessage());
                        Log.i("ERROR_VALIDATION", t.getCause().toString());
                        handler.handleError();
                    }
                });

    }
}