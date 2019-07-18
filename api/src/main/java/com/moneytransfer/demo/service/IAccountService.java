package com.moneytransfer.demo.service;

import com.moneytransfer.demo.data.models.account.AccountDetail;
import com.moneytransfer.demo.data.models.transaction.TransactionDetails;

import java.util.Currency;

public interface IAccountService {

    AccountDetail createAccount(String userName);

    AccountDetail getAccount(String accountId);

    void closeAccount(String bankAccountId);

    AccountDetail addMoneyToAccount(String bankAccountId, Double amount, Currency currency);

    AccountDetail withdrawMoneyToAccount(String bankAccountId, Double amount, Currency currency);

    TransactionDetails transferMoney(String fromBankAccountId, String toBankAccountId, Double amount, Currency currency);
}
