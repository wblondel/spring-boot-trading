package com.williamblondel.infinitrade.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, length = 10)
    private String transactionType;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Transaction() {}

    public Transaction(Wallet wallet, Double amount, String transactionType, String status) {
        this.wallet = wallet;
        this.amount = amount;
        this.transactionType = transactionType; // 'deposit' or 'withdrawal'
        this.status = status; // 'pending', 'completed', or 'failed'
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(o instanceof Transaction)) return false;

        Transaction transaction = (Transaction) o;

        return Objects.equals(this.id, transaction.id) &&
                Objects.equals(this.wallet, transaction.wallet) &&
                Objects.equals(this.amount, transaction.amount) &&
                Objects.equals(this.transactionType, transaction.transactionType) &&
                Objects.equals(this.status, transaction.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.wallet, this.amount, this.transactionType, this.status);
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + this.id +
                ", wallet='" + this.wallet + '\'' +
                ", amount='" + this.amount + '\'' +
                ", transactionType='" + this.transactionType + '\'' +
                ", status='" + this.status + '\'' +
                '}';
    }
}
