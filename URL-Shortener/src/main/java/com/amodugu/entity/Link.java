package com.amodugu.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB auto-increments this — this is what gets Base62-encoded
    private Long id;

    @Column(name = "short_code", unique = true, nullable = false, length = 10)
    private String shortCode;

    @Column(name = "long_url", nullable = false, length = 2048)
    private String longUrl;

    @Column(name = "user_id")
    private Long userId; // who owns this link — needed for the paginated "list my links" endpoint

    @Column(name = "custom_alias")
    private Boolean customAlias; // was the code user-chosen or auto-generated?

    @Column(name = "expires_at")
    private LocalDateTime expiresAt; // nullable — not every link has a TTL

    @Builder.Default
    @Column(name = "click_count")
    private Long clickCount = 0L;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}