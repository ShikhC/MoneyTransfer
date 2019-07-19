package com.moneytransfer.demo.service;

import com.moneytransfer.demo.data.models.account.AccountDetail;
import com.moneytransfer.demo.data.models.transaction.TransactionDetails;

import java.util.Currency;


/**
 * The interface Account service.
 */
public interface IAccountService {

    /**
     * Create account given a userId.
     *
     * @param userId the user id
     * @return the account detail
     */
    AccountDetail createAccount(String userId);

    /**
     * Gets account info given an accountId.
     *
     * @param accountId the account id
     * @return the account
     */
    AccountDetail getAccount(String accountId);

    /**
     * Close account will make the account inactive.
     * As of now these is no functionality to re-activate an account.
     *
     * @param bankAccountId the bank account id
     */
    void closeAccount(String bankAccountId);

    /**
     * Add money to account.
     *
     * @param bankAccountId the bank account id
     * @param amount        the amount
     * @param currency      the currency
     * @return the account detail
     */
    AccountDetail addMoneyToAccount(String bankAccountId, Double amount, Currency currency);

    /**
     * Withdraw money to account.
     *
     * @param bankAccountId the bank account id
     * @param amount        the amount
     * @param currency      the currency
     * @return the account detail
     */
    AccountDetail withdrawMoneyToAccount(String bankAccountId, Double amount, Currency currency);

    /**
     * Transfer a specific amount from one account Id to another account Id.
     *
     * @param fromBankAccountId the from bank account id
     * @param toBankAccountId   the to bank account id
     * @param amount            the amount
     * @param currency          the currency is not being used yet.
     * @return the transaction details
     */
    TransactionDetails transferMoney(String fromBankAccountId, String toBankAccountId, Double amount, Currency currency);
}
