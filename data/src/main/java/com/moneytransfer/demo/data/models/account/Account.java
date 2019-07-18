package com.moneytransfer.demo.data.models.account;

import com.google.common.util.concurrent.AtomicDouble;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}