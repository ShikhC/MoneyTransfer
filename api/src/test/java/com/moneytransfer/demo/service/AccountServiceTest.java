package com.moneytransfer.demo.service;

import com.moneytransfer.demo.data.dao.AccountDao;
import com.moneytransfer.demo.data.dao.TransactionDao;
import com.moneytransfer.demo.data.dao.UserDao;
import com.moneytransfer.demo.data.mapper.AccountMapper;
import com.moneytransfer.demo.data.mapper.TransactionMapper;
import com.moneytransfer.demo.data.mapper.UserMapper;
import com.moneytransfer.demo.data.models.account.AccountDetail;
import com.moneytransfer.demo.data.models.account.exception.AccountInactiveException;
import com.moneytransfer.demo.data.models.transaction.exception.TransactionFailedException;
import com.moneytransfer.demo.data.repository.IRepositoryService;
import com.moneytransfer.demo.data.repository.RepositoryService;
import com.moneytransfer.demo.data.repository.exception.UserNotFoundInDatabaseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class AccountServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private IUserService userService;
    private IAccountService accountService;

    @Before
    public void setUp() throws Exception {
        IRepositoryService IRepositoryService = new RepositoryService();
        UserDao userDao = new UserDao(IRepositoryService);
        AccountDao accountDao = new AccountDao(IRepositoryService);
        AccountMapper accountMapper = new AccountMapper();
        TransactionDao transactionDao = new TransactionDao(IRepositoryService);
        TransactionService transactionService = new TransactionService(accountDao, transactionDao);
        TransactionMapper transactionMapper = new TransactionMapper();
        UserMapper userMapper = new UserMapper();
        accountService = new AccountService(accountDao, accountMapper, transactionService, transactionMapper);
        userService = new UserService(userDao, userMapper, transactionMapper, accountService);
    }

    @Test
    public void createAccount() {
        userService.createUser("User1");
        AccountDetail account = accountService.createAccount("User1");
        assertEquals("account should exist", account, accountService.getAccount(account.getId()));
    }

    @Test
    public void createAccountWithoutUser() {
        thrown.expect(UserNotFoundInDatabaseException.class);
        accountService.createAccount("User1");
    }

    @Test
    public void getAccount() {
        userService.createUser("User1");
        AccountDetail account = accountService.createAccount("User1");
        assertEquals("account should exist", account, accountService.getAccount(account.getId()));
    }

    @Test
    public void addMoneyToAccount() {
        userService.createUser("User1");
        AccountDetail account = accountService.createAccount("User1");
        accountService.addMoneyToAccount(account.getId(), 50.0, null);
        assertEquals("amount in account should be 50 ", accountService.getAccount(account.getId()).getAmount(), Double.valueOf(50.0));
    }

    @Test
    public void withdrawMoneyToAccount() {
        userService.createUser("User1");
        AccountDetail account = accountService.createAccount("User1");
        accountService.addMoneyToAccount(account.getId(), 50.0, null);
        accountService.withdrawMoneyToAccount(account.getId(), 30.0, null);
        assertEquals("amount in account should be 20 ", accountService.getAccount(account.getId()).getAmount(), Double.valueOf(20.0));
    }

    @Test
    public void transferMoney() {
        userService.createUser("User1");
        AccountDetail account1 = accountService.createAccount("User1");
        accountService.addMoneyToAccount(account1.getId(), 50.0, null);

        userService.createUser("User2");
        AccountDetail account2 = accountService.createAccount("User2");
        accountService.addMoneyToAccount(account2.getId(), 100.0, null);

        accountService.transferMoney(account1.getId(), account2.getId(), 20.0, null);
        assertEquals("amount in account1 should be 30 ", accountService.getAccount(account1.getId()).getAmount(), Double.valueOf(30.0));
        assertEquals("amount in account2 should be 120 ", accountService.getAccount(account2.getId()).getAmount(), Double.valueOf(120.0));
    }

    @Test
    public void transferMoneyOnePartyDoesNotExist() {
        userService.createUser("User1");
        AccountDetail account1 = accountService.createAccount("User1");
        accountService.addMoneyToAccount(account1.getId(), 50.0, null);

        userService.createUser("User2");
        AccountDetail account2 = accountService.createAccount("User2");
        accountService.addMoneyToAccount(account2.getId(), 50.0, null);

        userService.deleteUser("User2");
        thrown.expect(AccountInactiveException.class);
        accountService.transferMoney(account1.getId(), account2.getId(), 20.0, null);

    }

    @Test
    public void transferMoneyOnePartyAccountDoesNotExist() {
        userService.createUser("User1");
        AccountDetail account1 = accountService.createAccount("User1");
        accountService.addMoneyToAccount(account1.getId(), 50.0, null);

        userService.createUser("User2");
        AccountDetail account2 = accountService.createAccount("User2");
        accountService.addMoneyToAccount(account2.getId(), 50.0, null);

        accountService.closeAccount(account2.getId());
        thrown.expect(AccountInactiveException.class);
        accountService.transferMoney(account1.getId(), account2.getId(), 20.0, null);
    }

    @Test
    public void transferMoneyInsufficientBalance() {
        userService.createUser("User1");
        AccountDetail account1 = accountService.createAccount("User1");
        accountService.addMoneyToAccount(account1.getId(), 50.0, null);

        userService.createUser("User2");
        AccountDetail account2 = accountService.createAccount("User2");
        accountService.addMoneyToAccount(account2.getId(), 50.0, null);

        thrown.expect(TransactionFailedException.class);
        thrown.expectMessage("Transaction status : INSUFFICIENT_FUNDS");
        accountService.transferMoney(account1.getId(), account2.getId(), 100.0, null);
    }
}