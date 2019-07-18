package com.moneytransfer.demo.data.mapper;

import com.moneytransfer.demo.data.models.account.Account;
import com.moneytransfer.demo.data.models.account.AccountDetail;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountMapper implements ObjectMapper<AccountDetail, Account> {

    @Override
    public Account mapRequest(AccountDetail accountDetail) {
        if (accountDetail == null) {
            return null;
        }
        final Account account = new Account();
        account.setId(accountDetail.getId());
        account.setUpdatedAt(LocalDateTime.now());
        return mapRequest(accountDetail, account);
    }

    @Override
    public Account mapRequest(final AccountDetail accountDetail, @Nonnull final Account account) {
        if (accountDetail == null) {
            return account;
        }
        account.setAccountHolderName(accountDetail.getAccountHolderName());
        account.setAmount(accountDetail.getAmount());
        account.setAccountStatus(Optional.ofNullable(accountDetail.getAccountStatus()).orElse(account.getAccountStatus()));
        return account;
    }

    @Override
    public Optional<AccountDetail> mapResponse(Account account) {
        if (account == null || !account.isActive())
            return Optional.empty();
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setId(account.getId());
        accountDetail.setAccountHolderName(account.getAccountHolderName());
        accountDetail.setAmount(account.getAmount());
        accountDetail.setAccountStatus(account.getAccountStatus());

        return Optional.of(accountDetail);
    }

    @Override
    public List<Account> mapRequestList(List<AccountDetail> accountDetails) {
        if (accountDetails == null || accountDetails.isEmpty())
            return null;
        List<Account> accounts = new ArrayList<>();
        for (AccountDetail accountDetail : accountDetails) {
            Account account = mapRequest(accountDetail);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public List<AccountDetail> mapResponseList(List<Account> accounts) {
        if (accounts == null || accounts.isEmpty())
            return null;
        List<AccountDetail> accountDetails = new ArrayList<>();
        for (Account account : accounts) {
            Optional<AccountDetail> accountDetail = mapResponse(account);
            if (accountDetail.isPresent())
                accountDetails.add(accountDetail.get());
        }
        return accountDetails;
    }
}