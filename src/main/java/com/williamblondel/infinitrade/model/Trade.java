package com.williamblondel.infinitrade.model;

import com.williamblondel.infinitrade.enumeration.TradeTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "pair_id", nullable = false)
    private Pair pair;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TradeTypeEnum tradeType;

    @NonNull
    @Column(nullable = false)
    private Double amount;

    @NonNull
    @Column(nullable = false)
    private Double price;

//    @Column(nullable = false)
//    private Double fee;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
