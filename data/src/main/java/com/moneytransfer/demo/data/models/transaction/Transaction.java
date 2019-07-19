package com.moneytransfer.demo.data.models.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Transaction {

    private String id;
    private String fromAccountId;
    private String toAccountId;
    private Currency currency;
    private Double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TransactionState transactionState;

    public Transaction(String id, String fromAccountId, String toAccountId, Currency currency, Double amount, TransactionState transactionState) {
        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.currency = currency;
        this.amount = amount;
        this.transactionState = transactionState;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id) &&
                fromAccountId.equals(that.fromAccountId) &&
                toAccountId.equals(that.toAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromAccountId, toAccountId);
    }
}
