package com.moneytransfer.demo.data.mapper;

import com.moneytransfer.demo.data.models.transaction.Transaction;
import com.moneytransfer.demo.data.models.transaction.TransactionDetails;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionMapper implements ObjectMapper<TransactionDetails, Transaction> {

    @Override
    public Transaction mapRequest(TransactionDetails transactionDetail) {
        if (transactionDetail == null) {
            return null;
        }
        final Transaction transaction = new Transaction();
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setId(transactionDetail.getId());
        return mapRequest(transactionDetail, transaction);
    }

    @Override
    public Transaction mapRequest(final TransactionDetails transactionDetail, @Nonnull final Transaction transaction) {
        if (transactionDetail == null) {
            return transaction;
        }
        transaction.setAmount(transactionDetail.getAmount());
        transaction.setFromAccountId(transactionDetail.getFromAccountId());
        transaction.setToAccountId(transactionDetail.getToAccountId());
        transaction.setCurrency(transactionDetail.getCurrency());
        transaction.setId(transactionDetail.getId());
        transaction.setTransactionState(transactionDetail.getTransactionState());

        return transaction;
    }

    @Override
    public Optional<TransactionDetails> mapResponse(Transaction transaction) {
        if (transaction == null)
            return Optional.empty();
        TransactionDetails transactionDetail = new TransactionDetails();
        transactionDetail.setId(transaction.getId());
        transactionDetail.setAmount(transaction.getAmount());
        transactionDetail.setCurrency(transaction.getCurrency());
        transactionDetail.setFromAccountId(transaction.getFromAccountId());
        transactionDetail.setToAccountId(transaction.getToAccountId());
        transactionDetail.setTransactionState(transaction.getTransactionState());
        return Optional.of(transactionDetail);
    }

    @Override
    public List<Transaction> mapRequestList(List<TransactionDetails> transactionDetails) {
        if (transactionDetails == null || transactionDetails.isEmpty())
            return null;
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionDetails transactionDetail : transactionDetails) {
            Transaction transaction = mapRequest(transactionDetail);
            transactions.add(transaction);
        }
        return transactions;
    }

    @Override
    public List<TransactionDetails> mapResponseList(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty())
            return null;
        List<TransactionDetails> transactionDetails = new ArrayList<>();
        for (Transaction transaction : transactions) {
            Optional<TransactionDetails> transactionDetail = mapResponse(transaction);
            if (transactionDetail.isPresent())
                transactionDetails.add(transactionDetail.get());
        }
        return transactionDetails;
    }
}