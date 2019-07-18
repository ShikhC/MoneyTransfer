package com.moneytransfer.demo.data.models.account;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum AccountStatus {
    NOT_CREATED,
    CREATED,
    VERIFICATION_INITIATED,
    VERIFIED,
    VERIFICATION_FAILED;

    private Set<AccountStatus> nextAllowedStatusSet;

    static {
        VERIFIED.nextAllowedStatusSet = new HashSet<>();
        NOT_CREATED.nextAllowedStatusSet = new HashSet<>(Arrays.asList(CREATED));
        CREATED.nextAllowedStatusSet = new HashSet<>(Arrays.asList(VERIFICATION_INITIATED, VERIFIED));
        VERIFICATION_INITIATED.nextAllowedStatusSet = new HashSet<>(Arrays.asList(VERIFICATION_FAILED, VERIFIED));
        VERIFICATION_FAILED.nextAllowedStatusSet = new HashSet<>(Arrays.asList(VERIFIED));

    }

    public Set<AccountStatus> getNextAllowedStatusSet() {
        return nextAllowedStatusSet;
    }
}