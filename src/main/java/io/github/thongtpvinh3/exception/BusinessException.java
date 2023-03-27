package io.github.thongtpvinh3.exception;


public class BusinessException extends RuntimeException {
    protected int statusCode;
    protected String message;
}
