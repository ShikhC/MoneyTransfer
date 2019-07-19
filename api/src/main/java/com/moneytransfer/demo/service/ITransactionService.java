package com.moneytransfer.demo.service;

import com.moneytransfer.demo.data.models.transaction.Transaction;

public interface ITransactionService {

    public void execute(Transaction transaction);
}
