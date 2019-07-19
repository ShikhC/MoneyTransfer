package com.moneytransfer.demo.utils;

import java.util.UUID;


/**
 * The type Id generator is generating unique Ids.
 */
public class IdGenerator {

    /**
     * Gets new account id.
     *
     * @return the new account id
     */
    public static String getNewAccountId() {
        return new StringBuilder("AC_").append(UUID.randomUUID()).toString();
    }

    /**
     * Gets new transaction id.
     *
     * @return the new transaction id
     */
    public static String getNewTransactionId() {
        return new StringBuilder("TR_").append(UUID.randomUUID()).toString();
    }
}
