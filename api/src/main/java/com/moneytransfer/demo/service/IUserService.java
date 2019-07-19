package com.moneytransfer.demo.service;

import com.moneytransfer.demo.data.models.transaction.TransactionDetails;
import com.moneytransfer.demo.data.models.user.UserAccountsInfo;
import com.moneytransfer.demo.data.models.user.UserDetails;

import java.util.List;
import java.util.Map;

/**
 * The interface User service.
 */
public interface IUserService {

    /**
     * Gets user details.
     *
     * @param accountHolderName the account holder name
     * @return the user details
     */
    UserDetails getUserDetails(String accountHolderName);

    /**
     * Create user user details.
     *
     * @param userName the user name
     * @return the user details
     */
    UserDetails createUser(String userName);

    /**
     * Delete user.
     *
     * @param userName the user name
     */
    void deleteUser(String userName);

    /**
     * Gets user transactions.
     *
     * @param userId the user id
     * @param limit  the limit
     * @return the user transactions
     */
    Map<String, List<TransactionDetails>> getUserTransactions(String userId , int limit);

    /**
     * Gets user account info with detailed Account de.
     *
     * @param userId the user id
     * @return the user account info
     */
    UserAccountsInfo getUserAccountInfo(String userId);
}
