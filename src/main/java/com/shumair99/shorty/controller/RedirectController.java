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

    @GetMapping("/{slug:^(?!api$)(?!r$)(?!error$)(?!assets$)(?!index\\.html$)(?!styles\\.css$)(?!script\\.js$)[A-Za-z0-9_-]{3,64}$}") //exceptions for reserved paths and static files
    public ResponseEntity<Void> redirect(@PathVariable String slug) {

        ResolvedLink resolvedLink = linkService.resolve(slug);
        if (resolvedLink == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(resolvedLink.getTargetUrl())).build();
    }
}