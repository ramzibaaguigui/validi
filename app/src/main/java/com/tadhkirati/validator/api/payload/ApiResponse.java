package com.tadhkirati.validator.api.payload;


public class ApiResponse<T> {
    Boolean success;
    String message;
    T data;

    public T getData() {
        return this.data;
    }

    public boolean isSuccessful() {
        return this.success;
    }
}
