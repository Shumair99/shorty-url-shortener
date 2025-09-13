package com.shumair99.shorty.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shumair99.shorty.dto.ResolvedLink;

@Controller
public class RedirectController {
    
    private final com.shumair99.shorty.service.LinkService linkService;

    public RedirectController(com.shumair99.shorty.service.LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/r/{slug}")
    public ResponseEntity<Void> redirect(@PathVariable String slug) {
        // Here you would typically look up the slug in your database to find the corresponding target URL.
        // For demonstration purposes, we'll just redirect to a fixed URL.
        ResolvedLink resolvedLink = linkService.resolve(slug);
        if (resolvedLink == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(resolvedLink.getTargetUrl())).build();
    }
}
