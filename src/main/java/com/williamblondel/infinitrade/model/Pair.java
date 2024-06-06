package com.williamblondel.infinitrade.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

// All currency pairs available for trading on the platform

@Entity
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pairs")
public class Pair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "base_currency_id", nullable = false)
    private Currency baseCurrency;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "quote_currency_id", nullable = false)
    private Currency quoteCurrency;

    @NonNull
    @Column(unique = true, nullable = false, length = 20)
    private String pairCode;

    @NonNull
    @Column(nullable = false)
    private Double bidPrice;

    @NonNull
    @Column(nullable = false)
    private Double askPrice;

    @OneToMany(mappedBy = "pair")
    @JsonIgnore
    private List<Trade> trades;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
