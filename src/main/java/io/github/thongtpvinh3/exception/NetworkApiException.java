package io.github.thongtpvinh3.exception;

public class NetworkApiException extends BusinessException {

    public NetworkApiException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
