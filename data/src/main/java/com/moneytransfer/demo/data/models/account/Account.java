package com.moneytransfer.demo.data.models.account;

import com.google.common.util.concurrent.AtomicDouble;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account {

    private String id;
    private String accountHolderName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private AtomicDouble amount;
    private AccountStatus accountStatus;
    private boolean isActive;

    public void setAmount(Double amount) {
        this.amount.set(amount);
    }

    public Double getAmount() {
        return amount.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
        return id.equals(that.id) &&
                accountHolderName.equals(that.accountHolderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountHolderName);
    }
}