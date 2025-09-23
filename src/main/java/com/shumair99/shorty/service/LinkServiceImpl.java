package com.shumair99.shorty.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.shumair99.shorty.dto.CreateLinkRequest;
import com.shumair99.shorty.dto.CreateLinkResponse;
import com.shumair99.shorty.dto.ResolvedLink;
import com.shumair99.shorty.domain.Link;
import com.shumair99.shorty.repo.LinkRepository;
import com.shumair99.shorty.core.BadRequestException;
import com.shumair99.shorty.core.ConflictException;
import com.shumair99.shorty.core.GoneException;
import com.shumair99.shorty.core.NotFoundException;
import com.shumair99.shorty.core.SlugGenerator;
import com.shumair99.shorty.core.UrlValidator;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LinkServiceImpl implements LinkService {

    private final SlugGenerator slugGenerator;
    private final int slugLength;
    private final Set<String> reserved;

    private final UrlValidator urlValidator;
    private final LinkRepository repo;
    private final String baseUrl;

    public LinkServiceImpl(
            SlugGenerator slugGenerator,
            UrlValidator urlValidator,
            LinkRepository repo,
            @Value("${app.base-url:http://localhost:8080}") String baseUrl,
            @Value("${app.slug-length:7}") int slugLength,
            @Value("${app.reserved-slugs:api,about,admin,health,static,assets,r}") String reservedCsv
    ){
        this.baseUrl = Objects.requireNonNull(baseUrl);
        this.urlValidator = urlValidator;
        this.repo = repo;
        this.slugGenerator = slugGenerator;
        this.slugLength = slugLength;
        this.reserved = Stream.of(reservedCsv.split(","))
                .map(s -> s.trim().toLowerCase(Locale.ROOT))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }

    @Override
    public CreateLinkResponse create(CreateLinkRequest req) {

        String targetURL=trimToNull(req.getTargetURL());
        String customSlug=trimToNull(req.getCustomSlug());

        if (targetURL == null) {
            throw new BadRequestException("targetURL is required");
        }
        if (!urlValidator.isHttpOrHttps(targetURL)) {
            throw new BadRequestException("targetURL must start with http:// or https://");
        }
        if (urlValidator.isPrivateOrLocal(targetURL)) {
            throw new BadRequestException("targetURL must not point to private or local addresses");
        }

        String slug;
        if (customSlug != null) {
            //validate pattern
            if (!isValidSlug(customSlug)) {
                throw new BadRequestException("customSlug must match ^[A-Za-z0-9_-]{3,32}$");
            }
            //reserved words
            if (reserved.contains(customSlug.toLowerCase(Locale.ROOT))) {
                throw new BadRequestException("customSlug is reserved");
            }
            //availability
            if (repo.existsBySlug(customSlug)) {
                throw new ConflictException("Slug already in use");
            }
            slug = customSlug;
        } else {
            //generate with a few retries on rare collision
            slug = generateUniqueSlug();
        }

        String expirationStr = trimToNull(req.getExpirationDate());

        Instant expirationDate = null;
        if (expirationStr != null) {
            try {
                expirationDate = java.time.Instant.parse(expirationStr);
            } catch (java.time.format.DateTimeParseException e) {
                try {
                    java.time.LocalDate d = java.time.LocalDate.parse(expirationStr);
                    expirationDate = d.atStartOfDay(java.time.ZoneOffset.UTC).toInstant();
                } catch (java.time.format.DateTimeParseException e2) {
                    throw new com.shumair99.shorty.core.BadRequestException(
                        "expirationDate must be in the format 2026-01-01 00:00:00 or YYYY-MM-DD"
                    );
                }
            }

            if (!expirationDate.isAfter(java.time.Instant.now())) {
                throw new com.shumair99.shorty.core.BadRequestException("expirationDate must be in the future");
            }
        }

        Link link = new Link(slug, targetURL, java.time.Instant.now(), expirationDate);
        repo.save(link);

                String customURL = baseUrl + "/r/" + slug;

        return new CreateLinkResponse(slug, customURL, targetURL, expirationDate != null ? expirationDate.toString() : null);
    }

    @Override
    public ResolvedLink resolve(String slug) {
        if (slug == null || !isValidSlug(slug) || reserved.contains(slug.toLowerCase(Locale.ROOT))) {
            //invalid/taken slugs are treated as not found
            throw new NotFoundException("Unknown slug");
        }

        return repo.findBySlug(slug)
                .map(link -> {
                    if (link.getExpirationDate() != null && link.getExpirationDate().isBefore(Instant.now())) {
                        throw new GoneException("Link expired");
                    }
                    return new ResolvedLink(link.getTargetUrl(),
                            link.getExpirationDate() != null ? link.getExpirationDate().toString() : null);
                })
                .orElseThrow(() -> new NotFoundException("Unknown slug"));
    }

    private boolean isValidSlug(String s) {
        return s != null && s.matches("^[A-Za-z0-9_-]{3,32}$");
    }
    
    private String generateUniqueSlug() {
        //try a few times in case of rare collisions (not likely to happen)
            for (int i = 0; i < 5; i++) {
                String candidate = slugGenerator.generate(slugLength);
                    if (!repo.existsBySlug(candidate) && !reserved.contains(candidate.toLowerCase(Locale.ROOT))) {
                    return candidate;
               }
            }
            throw new IllegalStateException("Failed to generate a unique slug");
    }
}
