package com.shumair99.shorty.jpa;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "links")
public class LinkEntity {
    @Id
    @Column(name = "slug", length = 64)
    private String slug;

    @Column(name = "target_url", nullable = false, columnDefinition = "text")
    private String targetUrl;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "expiration_date")
    private Instant expirationDate; // nullable

    protected LinkEntity() {} // JPA needs it

    public LinkEntity(String slug, String targetUrl, Instant createdAt, Instant expirationDate) {
    this.slug = slug;
    this.targetUrl = targetUrl;
    this.createdAt = createdAt;
    this.expirationDate = expirationDate;
    }

    // getters/setters
    public String getSlug() { 
        return slug; 
    }

    public void setSlug(String slug) { 
        this.slug = slug; 
    }

    public String getTargetUrl() {
         return targetUrl; 
    }

    public void setTargetUrl(String targetUrl) { 
        this.targetUrl = targetUrl; 
    }

    public Instant getCreatedAt() { 
        return createdAt; 
    }

    public void setCreatedAt(Instant createdAt) { 
        this.createdAt = createdAt; 
    }

    public Instant getExpirationDate() { 
        return expirationDate; 
    }

    public void setExpirationDate(Instant expirationDate) { 
        this.expirationDate = expirationDate; 
    }
    
}
