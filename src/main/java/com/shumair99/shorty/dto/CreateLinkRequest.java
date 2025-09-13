package com.shumair99.shorty.dto;

public class CreateLinkRequest {
    private String targetURL;
    private String customSlug;
    private String expirationDate;

    public CreateLinkRequest() {
    }

    public CreateLinkRequest(String targetURL, String customSlug, String expirationDate) {
        this.targetURL = targetURL;
        this.customSlug = customSlug;
        this.expirationDate = expirationDate;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public String getCustomSlug() {
        return customSlug;
    }

    public void setCustomSlug(String customSlug) {
        this.customSlug = customSlug;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    
}
