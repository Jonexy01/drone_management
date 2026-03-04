package com.example.blusalt.models.dtos;

public class ResponseBuilder {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", message, data, null);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>("failed", message, data, null);
    }
}
