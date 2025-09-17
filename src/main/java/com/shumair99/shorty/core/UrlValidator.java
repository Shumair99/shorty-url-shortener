package com.shumair99.shorty.core;

import org.springframework.stereotype.Component;

@Component
public class UrlValidator {
    
    public boolean isHttpOrHttps(String url) {
            return url.startsWith("http://") || url.startsWith("https://");
        }

    public boolean isPrivateOrLocal(String url) {
        return url.contains("localhost") || url.contains("127.0.0.1");
    }

}