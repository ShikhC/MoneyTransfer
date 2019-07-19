package com.moneytransfer.demo.data.repository;

import com.moneytransfer.demo.data.models.account.Account;
import com.moneytransfer.demo.data.models.user.User;


/**
 * The type Lock entity service.
 */
public class LockEntityService {
    /**
     * Gets user for write.
     * Single point to contact if anyone want to update on information about an account.
     *
     * @param user    the user
     * @param handler the handler
     */
    public static void getUserForWrite(User user, Handler handler) {
        synchronized (user) {
            handler.execute();
        }
    }

    /**
     * Gets account for write.
     * Single point to contact if anyone want to update on an account
     *
     * @param account the account
     * @param handler the handler
     */
    public static void getAccountForWrite(Account account, Handler handler) {
        synchronized (account) {
            handler.execute();
        }
    }

    /**
     * The interface Handler which is used to give a hook for the caller
     * to do his task between the processing of the function it is passed to.
     */
    public interface Handler {
        /**
         * Execute.
         */
        void execute();
    }
}
