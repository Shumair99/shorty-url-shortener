package com.shumair99.shorty.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkJpaRepository extends JpaRepository<LinkEntity, String> {
  boolean existsBySlug(String slug);
}
