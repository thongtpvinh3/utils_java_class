package exception;

public class NetworkApiException extends BusinessException {

    public NetworkApiException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
