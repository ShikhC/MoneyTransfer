package com.moneytransfer.demo.data.repository;

import com.google.inject.Singleton;
import com.moneytransfer.demo.data.models.account.Account;
import com.moneytransfer.demo.data.models.account.exception.AccountInactiveException;
import com.moneytransfer.demo.data.models.transaction.Transaction;
import com.moneytransfer.demo.data.models.transaction.TransactionState;
import com.moneytransfer.demo.data.models.user.User;
import com.moneytransfer.demo.data.repository.exception.AccountAlreadyExistInDatabaseException;
import com.moneytransfer.demo.data.repository.exception.AccountNotFoundInDatabaseException;
import com.moneytransfer.demo.data.repository.exception.UserAlreadyExistInDatabaseException;
import com.moneytransfer.demo.data.repository.exception.UserNotFoundInDatabaseException;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Singleton
public class RepositoryService implements IRepositoryService {
    Map<String, User> userMap = new ConcurrentHashMap<>();
    Map<String, Account> accountMap = new ConcurrentHashMap<>();
    Map<String, Map<String, List<Transaction>>> userTransactions = new ConcurrentHashMap<>();
    Map<String, Transaction> transactions = new ConcurrentHashMap<>();

    @Override
    public Optional<Account> getAccountViaAccountId(String accountId) {
        return Optional.ofNullable(accountMap.get(accountId));
    }

    @Override
    public Optional<User> getUserViaUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new UserNotFoundInDatabaseException(userId);
        }
        return Optional.ofNullable(userMap.get(userId));
    }

    @Override
    public User save(User user) {
        if (getUserViaUserId(user.getName()).isPresent()) {
            throw new UserAlreadyExistInDatabaseException(user.getName());
        }
        return userMap.put(user.getName(), user);
    }

    @Override
    public Optional<User> deleteUser(String userId) {
        Optional<User> user = getUserViaUserId(userId);
        if (user.isPresent()) {
            LockEntityService.getUserForWrite(user.get(), () -> {
                user.get().setActive(false);
                user.get().getAccountIds().forEach(acc -> deleteAccount(acc));
            });
            return user;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Account> deleteAccount(String accountId) {
        Optional<Account> accountViaAccountId = getAccountViaAccountId(accountId);
        if (accountViaAccountId.isPresent()) {
            LockEntityService.getAccountForWrite(accountViaAccountId.get(), () -> {
                accountViaAccountId.get().setActive(false);
            });
            return accountViaAccountId;
        }
        return Optional.empty();
    }

    @Override
    public Account save(Account account) {
        Optional<User> user = getUserViaUserId(account.getAccountHolderName());
        if (user.isPresent()) {
            LockEntityService.getUserForWrite(user.get(), () -> {
                user.get().getAccountIds().add(account.getId());
                user.get().setUpdatedAt(LocalDateTime.now());
            });
        } else {
            throw new UserNotFoundInDatabaseException(account.getAccountHolderName());
        }
        if (getAccountViaAccountId(account.getId()).isPresent()) {
            throw new AccountAlreadyExistInDatabaseException(account.getId());
        }
        return accountMap.put(account.getId(), account);
    }

    @Override
    public Transaction save(Transaction transaction) {
        Optional<Account> fromAccount = getAccountViaAccountId(transaction.getFromAccountId());
        Optional<Account> toAccount = getAccountViaAccountId(transaction.getToAccountId());
        if (fromAccount.isPresent() && toAccount.isPresent()) {
            if (!fromAccount.get().isActive() || !toAccount.get().isActive()) {
                throw new AccountInactiveException(Response.Status.BAD_REQUEST, transaction.getFromAccountId() + " or " + transaction.getToAccountId());
            }
            LockEntityService.getAccountForWrite(fromAccount.get(), () -> {
                LockEntityService.getAccountForWrite(toAccount.get(), () -> {
                    Optional<Transaction> optionalTransaction = getTransactionViaTransactionId(transaction.getId());
                    if (optionalTransaction.isPresent()) {
                        Transaction tr = optionalTransaction.get();
                        tr.setTransactionState(transaction.getTransactionState());
                        if (TransactionState.COMPLETED.equals(transaction.getTransactionState())) {
                            updateTransactionForAccounts(transaction, toAccount);
                        }
                    } else {
                        updateTransactionForAccounts(transaction, fromAccount);
                    }
                    transactions.put(transaction.getId(), transaction);
                });
            });
        } else {
            throw new AccountNotFoundInDatabaseException(Response.Status.BAD_REQUEST, "at least one of the accounts not found in database.", "");
        }
        return transaction;
    }

    private void updateTransactionForAccounts(Transaction transaction, Optional<Account> account) {
        Map<String, List<Transaction>> toTransactionList = userTransactions.getOrDefault(account.get().getAccountHolderName(), new HashMap<>());
        List<Transaction> toTransactionListForAccount = toTransactionList.getOrDefault(account.get().getId(), new ArrayList<>());
        toTransactionListForAccount.add(transaction);
        toTransactionList.putIfAbsent(account.get().getId(), toTransactionListForAccount);
        userTransactions.putIfAbsent(account.get().getAccountHolderName(), toTransactionList);
    }

    @Override
    public Optional<Transaction> getTransactionViaTransactionId(String transactionId) {
        return Optional.ofNullable(transactions.get(transactionId));
    }

    @Override
    public Map<String, List<Transaction>> getUserTransactions(String userId, int limit) {
        Map<String, List<Transaction>> userTransactionList = userTransactions.getOrDefault(userId, new HashMap<>());
        Map<String, List<Transaction>> result = new HashMap<>();
        for (Map.Entry<String, List<Transaction>> entry : userTransactionList.entrySet()) {
            int size = entry.getValue().size();
            result.put(entry.getKey(), entry.getValue().subList((size - limit) > 0 ? size - limit : 0, size));
        }
        return result;
    }
}
