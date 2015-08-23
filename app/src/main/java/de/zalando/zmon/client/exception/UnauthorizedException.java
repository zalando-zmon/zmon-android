package de.zalando.zmon.client.exception;

public class UnauthorizedException extends Exception {

    public UnauthorizedException(Throwable throwable) {
        super(throwable);
    }
}
