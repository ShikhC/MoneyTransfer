package com.moneytransfer.demo.service;

import com.google.common.util.concurrent.AtomicDouble;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.moneytransfer.demo.data.dao.AccountDao;
import com.moneytransfer.demo.data.exception.ApiException;
import com.moneytransfer.demo.data.mapper.AccountMapper;
import com.moneytransfer.demo.data.mapper.TransactionMapper;
import com.moneytransfer.demo.data.models.account.Account;
import com.moneytransfer.demo.data.models.account.AccountDetail;
import com.moneytransfer.demo.data.models.account.AccountStatus;
import com.moneytransfer.demo.data.models.account.exception.AccountInactiveException;
import com.moneytransfer.demo.data.models.transaction.Transaction;
import com.moneytransfer.demo.data.models.transaction.TransactionDetails;
import com.moneytransfer.demo.data.models.transaction.TransactionState;
import com.moneytransfer.demo.data.repository.LockEntityService;
import com.moneytransfer.demo.utils.IdGenerator;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Currency;

@Singleton
public class AccountService implements IAccountService {

    private AccountDao accountDao;
    private AccountMapper accountMapper;
    private TransactionService transactionService;
    private TransactionMapper transactionMapper;

    @Inject
    public AccountService(AccountDao accountDao,
                          AccountMapper accountMapper,
                          TransactionService transactionService,
                          TransactionMapper transactionMapper) {
        this.accountDao = accountDao;
        this.accountMapper = accountMapper;
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public AccountDetail createAccount(String accountHolderName) {
        Account account = Account.builder()
                .id(IdGenerator.getNewAccountId())
                .accountHolderName(accountHolderName)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .amount(new AtomicDouble(0.0))
                .accountStatus(AccountStatus.CREATED)
                .isActive(true)
                .build();
        accountDao.save(account);
        return accountMapper.mapResponse(account).orElseThrow(() -> new AccountInactiveException(Response.Status.BAD_REQUEST, account.getId()));
    }

    @Override
    public AccountDetail getAccount(String accountId) {
        return accountMapper.mapResponse(accountDao.get(accountId)).orElseThrow(() -> new AccountInactiveException(Response.Status.BAD_REQUEST, accountId));
    }

    @Override
    public void closeAccount(String accountId) {
        accountDao.delete(accountId);
    }

    @Override
    public AccountDetail addMoneyToAccount(String bankAccountId, Double amount, Currency currency) {
        Account account = accountDao.get(bankAccountId);
        LockEntityService.getAccountForWrite(account, () -> {
            account.setAmount(account.getAmount() + amount);
        });
        return accountMapper.mapResponse(account).orElseThrow(() -> new AccountInactiveException(Response.Status.BAD_REQUEST, account.getId()));
    }

    @Override
    public AccountDetail withdrawMoneyToAccount(String bankAccountId, Double amount, Currency currency) {
        Account account = accountDao.get(bankAccountId);
        LockEntityService.getAccountForWrite(account, () -> {
            account.setAmount(account.getAmount() - amount);
        });
        return accountMapper.mapResponse(account).orElseThrow(() -> new AccountInactiveException(Response.Status.BAD_REQUEST, account.getId()));
    }

    @Override
    public TransactionDetails transferMoney(String fromBankAccountId, String toBankAccountId, Double amount, Currency currency) throws ApiException {
        Transaction transaction = new Transaction(IdGenerator.getNewTransactionId(), fromBankAccountId, toBankAccountId, currency, amount, TransactionState.NEW);
        transactionService.execute(transaction);
        return transactionMapper.mapResponse(transaction).get();
    }

}
