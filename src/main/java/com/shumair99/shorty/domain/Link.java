package com.shumair99.shorty.domain;

import java.time.Instant;

public class Link {
    private final String slug;
    private final String targetUrl;
    private final Instant createdAt;
    private final Instant expirationDate; // can be null

    public Link(String slug, String targetUrl, Instant createdAt, Instant expirationDate) {
        this.slug = slug;
        this.targetUrl = targetUrl;
        this.createdAt = createdAt;
        this.expirationDate = expirationDate;
    }

    public String getSlug() {
        return slug;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }
}
