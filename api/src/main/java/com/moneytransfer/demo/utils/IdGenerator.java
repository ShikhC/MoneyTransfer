package com.moneytransfer.demo.utils;

import java.util.UUID;

public class IdGenerator {

    public static String getNewAccountId() {
        return new StringBuilder("AC_").append(UUID.randomUUID()).toString();
    }

    public static String getNewTransactionId() {
        return new StringBuilder("TR_").append(UUID.randomUUID()).toString();
    }
}
