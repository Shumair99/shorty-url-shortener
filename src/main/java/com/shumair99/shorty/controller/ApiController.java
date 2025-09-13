package com.shumair99.shorty.controller;

import com.shumair99.shorty.dto.CreateLinkRequest;
import com.shumair99.shorty.dto.CreateLinkResponse;

import java.net.URI;

//import com.shumair99.shorty.service.LinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ApiController {

    private final com.shumair99.shorty.service.LinkService linkService;

    public ApiController(com.shumair99.shorty.service.LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/links")
    public ResponseEntity<CreateLinkResponse> create(@RequestBody CreateLinkRequest req) {

        CreateLinkResponse res = linkService.create(req);
        return ResponseEntity.created(URI.create(res.getCustomURL())).body(res);
    }

}