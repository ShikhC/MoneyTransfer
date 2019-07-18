package com.moneytransfer.demo.service;

import com.moneytransfer.demo.data.models.transaction.TransactionDetails;
import com.moneytransfer.demo.data.models.user.UserAccountsInfo;
import com.moneytransfer.demo.data.models.user.UserDetails;

import java.util.List;
import java.util.Map;

public interface IUserService {

    UserDetails getUserDetails(String accountHolderName);

    UserDetails createUser(String userName);

    void deleteUser(String userName);

    Map<String, List<TransactionDetails>> getUserTransactions(String userId , int limit);

    UserAccountsInfo getUserAccountInfo(String userId);
}
