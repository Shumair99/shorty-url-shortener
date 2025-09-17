package com.shumair99.shorty.core;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class SlugGenerator {

    private final SecureRandom random = new SecureRandom();
    private final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String generate(int length) {
        StringBuilder slug = new StringBuilder(length);
        if (length < 1) {
            throw new IllegalArgumentException("Length must be at least 1");
        }
        for (int i = 0; i < length; i++) {
            slug.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return slug.toString();
    }
}
