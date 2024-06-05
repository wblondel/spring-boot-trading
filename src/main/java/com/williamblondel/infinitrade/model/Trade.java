package com.williamblondel.infinitrade.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order;

    @ManyToOne
    @JoinColumn(name = "pair_id", nullable = false)
    private Pair pair;

    @Column(nullable = false, length = 10)
    private String tradeType;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double fee;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Trade() {}

    public Trade(User user, /*Order order,*/ Pair pair, String tradeType, Double amount, Double price, Double fee) {
        this.user = user;
        //this.order = order;
        this.pair = pair;
        this.tradeType = tradeType; // 'buy' or 'sell'
        this.amount = amount; // amount of crypto
        this.price = price; // price per unit of crypto
        this.fee = fee;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Order getOrder() {
//        return this.order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }

    public Pair getPair() {
        return this.pair;
    }

    public void setPair(Pair pair) {
        this.pair = pair;
    }

    public String getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getFee() {
        return this.fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade)) return false;

        Trade trade = (Trade) o;

        return Objects.equals(this.id, trade.id) &&
                Objects.equals(this.user, trade.user) &&
                Objects.equals(this.pair, trade.pair) &&
                Objects.equals(this.tradeType, trade.tradeType) &&
                Objects.equals(this.amount, trade.amount) &&
                Objects.equals(this.price, trade.price) &&
                Objects.equals(this.fee, trade.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.user, this.pair, this.tradeType, this.amount, this.price, this.fee);
    }

    @Override
    public String toString() {
        return "Trade{" + "id=" + this.id +
                ", user='" + this.user + '\'' +
                ", pair='" + this.pair + '\'' +
                ", tradeType='" + this.tradeType + '\'' +
                ", amount='" + this.amount + '\'' +
                ", price='" + this.price + '\'' +
                ", fee='" + this.fee + '\'' +
                '}';
    }
}
