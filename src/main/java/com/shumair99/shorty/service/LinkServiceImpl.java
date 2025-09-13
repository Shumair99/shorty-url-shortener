package com.shumair99.shorty.service;

import org.springframework.stereotype.Service;
import com.shumair99.shorty.dto.CreateLinkRequest;
import com.shumair99.shorty.dto.CreateLinkResponse;

@Service
public class LinkServiceImpl implements LinkService {
    @Override
    public CreateLinkResponse create(CreateLinkRequest req) {
    
        

        return new CreateLinkResponse(req.getCustomSlug(), req.getCustomSlug(), req.getTargetURL(), req.getExpirationDate());
    }

    @Override
    public com.shumair99.shorty.dto.ResolvedLink resolve(String slug) {
        // TODO
        return null;
    }
}
