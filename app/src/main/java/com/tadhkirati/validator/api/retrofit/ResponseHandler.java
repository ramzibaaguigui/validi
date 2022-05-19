package com.tadhkirati.validator.api.retrofit;

import com.tadhkirati.validator.api.payload.ApiResponse;

public interface ResponseHandler<T> {
    void handleSuccess(ApiResponse<T> response);
    void handleError();

}
