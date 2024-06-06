package com.williamblondel.infinitrade.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

// All the user's cryptocurrency wallets (one wallet per crypto per user)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @NonNull
    @Column(nullable = false)
    private Double balance;

    @OneToMany(mappedBy = "wallet")
    @JsonIgnore
    private List<Transaction> transactions;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
