package com.tadhkirati.validator.api.retrofit;

import com.tadhkirati.validator.api.payload.ApiResponse;
import com.tadhkirati.validator.api.payload.TicketValidationPayload;
import com.tadhkirati.validator.models.Ticket;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketApiRepository {

    public static void validateTicket(Integer travelId,
                                      String ticketToken,
                                      String authHeader,
                                      ResponseHandler<Ticket> ticketResponseHandler) {
        TicketValidationPayload payload = TicketValidationPayload
                .createTicketValidationRequest()
                .withTravelId(travelId)
                .withQrCode(ticketToken);

        RetrofitClient.apiService.validateTicket(payload, authHeader)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Ticket>> call, Response<ApiResponse<Ticket>> response) {
                        ticketResponseHandler.handleSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Ticket>> call, Throwable t) {
                        ticketResponseHandler.handleError();
                    }
                });
    }
}
