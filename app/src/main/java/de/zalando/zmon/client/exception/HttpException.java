package de.zalando.zmon.client.exception;

public class HttpException extends Exception {

    private int code;
    private String reason;

    public HttpException(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}
