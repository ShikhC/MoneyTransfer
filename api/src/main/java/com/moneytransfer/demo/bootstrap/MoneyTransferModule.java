package com.moneytransfer.demo.bootstrap;

import com.google.inject.AbstractModule;
import com.moneytransfer.demo.service.AccountService;
import com.moneytransfer.demo.service.IAccountService;
import com.moneytransfer.demo.service.IUserService;
import com.moneytransfer.demo.service.UserService;

public class MoneyTransferModule extends AbstractModule {

    private MoneyTransferConfiguration configuration;

    public MoneyTransferModule(MoneyTransferConfiguration config) {
        this.configuration = config;
    }

    @Override
    protected void configure() {
        bind(IUserService.class).to(UserService.class);
        bind(IAccountService.class).to(AccountService.class);
    }
}
