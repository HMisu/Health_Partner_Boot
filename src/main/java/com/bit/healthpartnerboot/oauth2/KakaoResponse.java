package com.bit.healthpartnerboot.oauth2;


import java.util.Map;

public class KakaoResponse implements OAuth2Response {
    Map<String, Object> attributes;

    Map<String, Object> properties;

    public KakaoResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.properties = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getProviderId() {
        return this.attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return this.properties.get("email").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> profile = (Map<String, Object>) this.properties.get("profile");
        return profile.get("nickname").toString();
    }

    @Override
    public String getImg() {
        Map<String, Object> profile = (Map<String, Object>) this.properties.get("profile");
        if (profile.get("avatar_url") != null) {
            return profile.get("image").toString();
        }
        return "";
    }
}
