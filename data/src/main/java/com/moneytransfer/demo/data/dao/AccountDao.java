package com.moneytransfer.demo.data.dao;


import com.moneytransfer.demo.data.models.account.Account;
import com.moneytransfer.demo.data.repository.IRepositoryService;
import com.moneytransfer.demo.data.repository.exception.AccountNotFoundInDatabaseException;

import javax.inject.Inject;


public class AccountDao extends GenericDao<Account> {

    @Inject
    public AccountDao(IRepositoryService IRepositoryService) {
        super(IRepositoryService);
    }

    public Account save(Account account) {
        return repositoryService.save(account);
    }

    @Override
    public Account get(String id) {
        return repositoryService.getAccountViaAccountId(id).orElseThrow(() -> new AccountNotFoundInDatabaseException(id));
    }

    public Account delete(String accountId) {
        return repositoryService.deleteAccount(accountId).orElseThrow(() -> new AccountNotFoundInDatabaseException(accountId));
    }
}
