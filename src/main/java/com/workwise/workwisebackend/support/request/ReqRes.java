package com.workwise.workwisebackend.support.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRes {

    private int status;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;

    public ReqRes(int status, String message, String token, String refreshToken) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public ReqRes() {}

}
