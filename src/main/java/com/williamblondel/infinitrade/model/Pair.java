package com.williamblondel.infinitrade.model;

import jakarta.annotation.Resource;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

// All currency pairs available for trading on the platform
@Entity
@Table(name = "pairs")
public class Pair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "base_currency_id", nullable = false)
    private Currency baseCurrency;

    @ManyToOne
    @JoinColumn(name = "quote_currency_id", nullable = false)
    private Currency quoteCurrency;

    @Column(unique = true, nullable = false, length = 20)
    private String pairCode;

    @Column(nullable = true)
    private Double bidPrice;

    @Column(nullable = true)
    private Double askPrice;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Pair() {}

    public Pair(Currency baseCurrency, Currency quoteCurrency) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.pairCode = baseCurrency.getTicker() + quoteCurrency.getTicker();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getBaseCurrency() {
        return this.baseCurrency;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Currency getQuoteCurrency() {
        return this.quoteCurrency;
    }

    public void setQuoteCurrency(Currency quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public String getPairCode() {
        return this.pairCode;
    }

    public void setPairCode(String pairCode) {
        this.pairCode = pairCode;
    }

    public Double getBidPrice() {
        return this.bidPrice;
    }

    public void setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Double getAskPrice() {
        return this.askPrice;
    }

    public void setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;

        Pair pair = (Pair) o;

        return Objects.equals(this.id, pair.id) &&
                Objects.equals(this.baseCurrency, pair.baseCurrency) &&
                Objects.equals(this.quoteCurrency, pair.quoteCurrency) &&
                Objects.equals(this.pairCode, pair.pairCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.baseCurrency, this.quoteCurrency, this.pairCode);
    }

    @Override
    public String toString() {
        return "Pair{" + "id=" + this.id +
                ", baseCurrency='" + this.baseCurrency + '\'' +
                ", quoteCurrency='" + this.quoteCurrency + '\'' +
                ", pairCode='" + this.pairCode + '\'' +
                '}';
    }
}
