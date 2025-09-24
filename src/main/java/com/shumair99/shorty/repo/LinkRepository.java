package com.shumair99.shorty.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shumair99.shorty.jpa.LinkEntity;

public interface LinkRepository extends JpaRepository<LinkEntity, String> {
    Optional<LinkEntity> findBySlug(String slug);
    boolean existsBySlug(String slug);
}


