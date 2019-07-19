package com.moneytransfer.demo.data.dao;


import com.moneytransfer.demo.data.models.transaction.Transaction;
import com.moneytransfer.demo.data.repository.IRepositoryService;
import com.moneytransfer.demo.data.repository.exception.TransactionNotFoundInDatabaseException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;


public class TransactionDao extends GenericDao<Transaction> {

    @Inject
    public TransactionDao(IRepositoryService IRepositoryService) {
        super(IRepositoryService);
    }

    public Transaction save(Transaction account) {
        return repositoryService.save(account);
    }

    @Override
    public Transaction get(String id) {
        return repositoryService.getTransactionViaTransactionId(id).orElseThrow(() -> new TransactionNotFoundInDatabaseException(Response.Status.BAD_REQUEST, "Transaction Not Found In Database : " + id));
    }
}
