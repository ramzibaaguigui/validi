package com.tadhkirati.validator.api.payload;


import java.io.Serializable;

public class ApiResponse<T> implements Serializable {
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
