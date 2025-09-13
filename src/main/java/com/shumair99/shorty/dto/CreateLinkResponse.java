package com.shumair99.shorty.dto;

public class CreateLinkResponse {
    private String slug;
    private String customURL;
    private String targetURL;
    private String expirationDate;

    public CreateLinkResponse() {
    }

    public CreateLinkResponse(String slug, String customURL, String targetURL, String expirationDate) {
        this.slug = slug;
        this.customURL = customURL;
        this.targetURL = targetURL;
        this.expirationDate = expirationDate;
    }


    public String getSlug() {
        return slug;
    }

    public String getCustomURL() {
        return customURL;
    }

    public String getTargetURL() {
        return targetURL;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setCustomURL(String customURL) {
        this.customURL = customURL;
    }

    public void setTargetURL(String targetURL) {
        this.targetURL = targetURL;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    
}