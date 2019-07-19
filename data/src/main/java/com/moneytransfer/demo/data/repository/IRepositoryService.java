package com.moneytransfer.demo.data.repository;

import com.moneytransfer.demo.data.models.account.Account;
import com.moneytransfer.demo.data.models.transaction.Transaction;
import com.moneytransfer.demo.data.models.user.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Repository service with is acting like database.
 */
public interface IRepositoryService {

    /**
     * Gets account via account id.
     *
     * @param accountId the account id
     * @return the account via account id
     */
    Optional<Account> getAccountViaAccountId(String accountId);

    /**
     * Gets user via user id.
     *
     * @param userId the user id
     * @return the user via user id
     */
    Optional<User> getUserViaUserId(String userId);

    /**
     * Save user.
     *
     * @param user the user
     * @return the user
     */
    User save(User user);

    /**
     * Delete user optional.
     *
     * @param userId the user id
     * @return the optional
     */
    Optional<User> deleteUser(String userId);

    /**
     * Delete account optional.
     *
     * @param accountId the account id
     * @return the optional
     */
    Optional<Account> deleteAccount(String accountId);

    /**
     * Save account.
     *
     * @param account the account
     * @return the account
     */
    Account save(Account account);

    /**
     * Save transaction.
     *
     * @param transaction the transaction
     * @return the transaction
     */
    Transaction save(Transaction transaction);

    /**
     * Gets transaction via transaction id.
     *
     * @param transactionId the transaction id
     * @return the transaction via transaction id
     */
    Optional<Transaction> getTransactionViaTransactionId(String transactionId);

    /**
     * Gets user transactions.
     *
     * @param userId the user id
     * @param limit  the limit
     * @return the user transactions
     */
    Map<String, List<Transaction>> getUserTransactions(String userId, int limit);
}
