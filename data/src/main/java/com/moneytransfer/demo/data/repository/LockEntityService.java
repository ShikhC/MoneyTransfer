package com.moneytransfer.demo.data.repository;

import com.moneytransfer.demo.data.models.account.Account;
import com.moneytransfer.demo.data.models.user.User;

public class LockEntityService {
    public static void getUserForWrite(User user, Handler handler) {
        synchronized (user) {
            handler.execute();
        }
    }

    public static void getAccountForWrite(Account account, Handler handler) {
        synchronized (account) {
            handler.execute();
        }
    }

    public interface Handler {
        void execute();
    }
}
