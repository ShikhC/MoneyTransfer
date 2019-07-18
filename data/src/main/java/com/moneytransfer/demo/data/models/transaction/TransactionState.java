package com.moneytransfer.demo.data.models.transaction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum TransactionState {
    NEW,
    INSUFFICIENT_FUNDS,
    COMPLETED,
    CONCURRENCY_ERROR,
    RESTARTED;

    private Set<TransactionState> nextAllowedStatusSet;

    static {
        COMPLETED.nextAllowedStatusSet = new HashSet<>();
        NEW.nextAllowedStatusSet = new HashSet<>(Arrays.asList(COMPLETED, INSUFFICIENT_FUNDS, CONCURRENCY_ERROR));
        INSUFFICIENT_FUNDS.nextAllowedStatusSet = new HashSet<>(Arrays.asList(RESTARTED));
        RESTARTED.nextAllowedStatusSet = new HashSet<>(Arrays.asList(COMPLETED, INSUFFICIENT_FUNDS, CONCURRENCY_ERROR));
        CONCURRENCY_ERROR.nextAllowedStatusSet = new HashSet<>(Arrays.asList(RESTARTED));

    }

    public Set<TransactionState> getNextAllowedStatusSet() {
        return nextAllowedStatusSet;
    }
}