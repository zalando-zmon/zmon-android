package de.zalando.zmon.client.exception;

import java.io.IOException;

public class HttpException extends IOException {

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
