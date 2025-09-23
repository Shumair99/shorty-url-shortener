package com.shumair99.shorty.service;

import com.shumair99.shorty.dto.CreateLinkRequest;
import com.shumair99.shorty.dto.CreateLinkResponse;
import com.shumair99.shorty.dto.ResolvedLink;

public interface LinkService {
    CreateLinkResponse create(CreateLinkRequest req);
    ResolvedLink resolve(String slug); 
}
