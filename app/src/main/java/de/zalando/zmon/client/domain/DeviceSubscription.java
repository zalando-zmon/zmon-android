package de.zalando.zmon.client.domain;

public class DeviceSubscription {

    private String registrationToken;

    public DeviceSubscription() {
    }

    public DeviceSubscription(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }
}
