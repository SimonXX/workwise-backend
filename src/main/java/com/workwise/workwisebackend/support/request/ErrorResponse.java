package com.workwise.workwisebackend.support.request;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(int status) {
        this.status = status;
        this.message = "An error occurred";
    }

    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(status, message != null ? message : "An error occurred");
    }
}
