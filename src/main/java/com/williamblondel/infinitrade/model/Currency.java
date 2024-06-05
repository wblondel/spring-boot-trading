package com.williamblondel.infinitrade.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

// All currencies available on the platform
@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 15)
    private String ticker;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String website;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Currency() {}

    public Currency(String ticker, String name, String description, String website) {
        this.ticker = ticker;
        this.name = name;
        this.description = description;
        this.website = website;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
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
        if (!(o instanceof Currency)) return false;

        Currency currency = (Currency) o;

        return Objects.equals(this.id, currency.id) &&
                Objects.equals(this.ticker, currency.ticker) &&
                Objects.equals(this.name, currency.name) &&
                Objects.equals(this.description, currency.description) &&
                Objects.equals(this.website, currency.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.ticker, this.name, this.description, this.website);
    }

    @Override
    public String toString() {
        return "Currency{" + "id=" + this.id +
                ", ticker='" + this.ticker + '\'' +
                ", name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                ", website='" + this.website + '\'' +
                '}';
    }
}
