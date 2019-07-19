package com.moneytransfer.demo.data.dao;


import com.moneytransfer.demo.data.models.transaction.Transaction;
import com.moneytransfer.demo.data.models.user.User;
import com.moneytransfer.demo.data.repository.IRepositoryService;
import com.moneytransfer.demo.data.repository.exception.AccountNotFoundInDatabaseException;
import com.moneytransfer.demo.data.repository.exception.UserNotFoundInDatabaseException;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;


public class UserDao extends GenericDao<User> {

    @Inject
    public UserDao(IRepositoryService IRepositoryService) {
        super(IRepositoryService);
    }

    @Override
    public User save(User user) {
        return repositoryService.save(user);
    }

    @Override
    public User get(String id) {
        return repositoryService.getUserViaUserId(id).orElseThrow(() -> new UserNotFoundInDatabaseException(id));
    }

    public Map<String, List<Transaction>> getUserTransactions(String userId, int limit) {
        return repositoryService.getUserTransactions(userId, limit);
    }

    public User delete(String userId) {
        return repositoryService.deleteUser(userId).orElseThrow(() -> new AccountNotFoundInDatabaseException(userId));
    }
}
