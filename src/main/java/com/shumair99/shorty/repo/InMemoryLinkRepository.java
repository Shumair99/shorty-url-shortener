/**package com.shumair99.shorty.repo;

import com.shumair99.shorty.domain.Link;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

//just for testing, will be replaced by jpa later
@Repository
public class InMemoryLinkRepository implements LinkRepository {

    //key = slug, value = Link
    private final ConcurrentHashMap<String, Link> store = new ConcurrentHashMap<>();

    @Override
    public void save(Link link) {
        //update or insert: create or replace by slug
        store.put(link.getSlug(), link);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return store.containsKey(slug);
    }

    @Override
    public Optional<Link> findBySlug(String slug) {
        return Optional.ofNullable(store.get(slug));
    }
}*/
