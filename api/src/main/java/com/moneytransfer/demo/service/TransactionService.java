package com.moneytransfer.demo.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.moneytransfer.demo.data.dao.AccountDao;
import com.moneytransfer.demo.data.dao.TransactionDao;
import com.moneytransfer.demo.data.models.account.Account;
import com.moneytransfer.demo.data.models.transaction.Transaction;
import com.moneytransfer.demo.data.models.transaction.TransactionState;
import com.moneytransfer.demo.data.models.transaction.exception.TransactionFailedException;
import com.moneytransfer.demo.data.repository.LockEntityService;

import javax.ws.rs.core.Response;

@Singleton
public class TransactionService implements ITransactionService {

    private AccountDao accountDao;
    private TransactionDao transactionDao;

    @Inject
    public TransactionService(AccountDao accountDao, TransactionDao transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public void execute(Transaction transaction) {
        Account fromAccount = accountDao.get(transaction.getFromAccountId());
        Account toAccount = accountDao.get(transaction.getToAccountId());
        transactionDao.save(transaction);
        LockEntityService.getAccountForWrite(fromAccount, () -> {
            LockEntityService.getAccountForWrite(toAccount, () -> {
                double finalAmountFromWithdrawalAccount = fromAccount.getAmount() - transaction.getAmount();
                if (finalAmountFromWithdrawalAccount < 0) {
                    transaction.setTransactionState(TransactionState.INSUFFICIENT_FUNDS);
                } else {
                    fromAccount.setAmount(finalAmountFromWithdrawalAccount);
                    toAccount.setAmount(toAccount.getAmount() + transaction.getAmount());
                    transaction.setTransactionState(TransactionState.COMPLETED);
                }
                transactionDao.save(transaction);
            });
        });
        if (!TransactionState.COMPLETED.equals(transaction.getTransactionState())) {
            throw new TransactionFailedException(Response.Status.INTERNAL_SERVER_ERROR, "Transaction status : " + transaction.getTransactionState());
        }
    }
}
