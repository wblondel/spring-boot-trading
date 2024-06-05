package com.williamblondel.infinitrade.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Cryptocurrency {
    private @Id @GeneratedValue Long id;
    private String ticker;
    private String name;

    @Column(length = 65535)
    private String description;
    private String website;

    public Cryptocurrency() {

    }

    public Cryptocurrency(String ticker, String name, String description, String website) {
        this.ticker = ticker;
        this.name = name;
        this.description = description;
        this.website = website;
    }

    public Long getId() {
        return this.id;
    }

    public String getTicker() {
        return this.ticker;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cryptocurrency)) return false;

        Cryptocurrency cryptocurrency = (Cryptocurrency) o;

        return Objects.equals(this.id, cryptocurrency.id) && Objects.equals(this.ticker, cryptocurrency.ticker)
                && Objects.equals(this.name, cryptocurrency.name)
                && Objects.equals(this.description, cryptocurrency.description)
                && Objects.equals(this.website, cryptocurrency.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.ticker, this.name, this.description, this.website);
    }

    @Override
    public String toString() {
        return "Cryptocurrency{" + "id=" + this.id +
                ", ticker='" + this.ticker + '\'' +
                ", name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                ", website='" + this.website + '\'' +
                '}';
    }
}
