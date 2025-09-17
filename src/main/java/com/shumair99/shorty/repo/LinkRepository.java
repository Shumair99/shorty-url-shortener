package com.shumair99.shorty.repo;

import com.shumair99.shorty.domain.Link;

import java.util.Optional;

public interface LinkRepository {

    void save(Link link);

    boolean existsBySlug(String slug);

    Optional<Link> findBySlug(String slug);
}

