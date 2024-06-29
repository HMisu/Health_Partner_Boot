package com.bit.healthpartnerboot.oauth2;

public interface OAuth2Response {
    String getProvider();

    String getProviderId();

    String getEmail();

    String getName();

    String getImg();
}