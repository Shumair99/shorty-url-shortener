package com.shumair99.shorty.dto;

import io.micrometer.common.lang.Nullable;

public class ResolvedLink {
    String targetUrl;
    @Nullable String expirationDate;

    public ResolvedLink() {
    }

    public ResolvedLink(String targetUrl, @Nullable String expirationDate) {
        this.targetUrl = targetUrl;
        this.expirationDate = expirationDate;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    @Nullable
    public String getExpirationDate() {
        return expirationDate;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public void setExpirationDate(@Nullable String expirationDate) {
        this.expirationDate = expirationDate;
    }
}

