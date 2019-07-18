package com.moneytransfer.demo.service;

import com.moneytransfer.demo.data.dao.AccountDao;
import com.moneytransfer.demo.data.dao.TransactionDao;
import com.moneytransfer.demo.data.dao.UserDao;
import com.moneytransfer.demo.data.mapper.AccountMapper;
import com.moneytransfer.demo.data.mapper.TransactionMapper;
import com.moneytransfer.demo.data.mapper.UserMapper;
import com.moneytransfer.demo.data.models.account.AccountDetail;
import com.moneytransfer.demo.data.models.transaction.TransactionDetails;
import com.moneytransfer.demo.data.models.user.UserAccountsInfo;
import com.moneytransfer.demo.data.models.user.UserDetails;
import com.moneytransfer.demo.data.repository.RepositoryService;
import com.moneytransfer.demo.data.repository.exception.UserAlreadyExistInDatabaseException;
import com.moneytransfer.demo.data.repository.exception.UserNotFoundInDatabaseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private IUserService userService;
    private IAccountService accountService;

    @Before
    public void setUp() {
        RepositoryService repositoryService = new RepositoryService();
        UserDao userDao = new UserDao(repositoryService);
        AccountDao accountDao = new AccountDao(repositoryService);
        AccountMapper accountMapper = new AccountMapper();
        TransactionDao transactionDao = new TransactionDao(repositoryService);
        TransactionService transactionService = new TransactionService(accountDao, transactionDao);
        TransactionMapper transactionMapper = new TransactionMapper();
        UserMapper userMapper = new UserMapper();
        accountService = new AccountService(accountDao, accountMapper, transactionService, transactionMapper);
        userService = new UserService(userDao, userMapper, transactionMapper, accountService);
    }

    @Test
    public void testGetUserDetails() {
        UserDetails user1 = userService.createUser("User1");
        assertEquals("User1 should exist", user1, userService.getUserDetails("User1"));
    }

    @Test
    public void testCreateUser_Null() {
        thrown.expect(UserNotFoundInDatabaseException.class);
        thrown.expectMessage("User Not Found In Database : null");
        userService.createUser(null);
    }

    @Test
    public void testCreateUser_Duplicate() {
        userService.createUser("User1");
        thrown.expect(UserAlreadyExistInDatabaseException.class);
        thrown.expectMessage("User Already Exist In Database : User1");
        userService.createUser("User1");
    }

    @Test
    public void testCreateUser() {
        thrown.expect(UserNotFoundInDatabaseException.class);
        thrown.expectMessage("User Not Found In Database : User1");
        assertNull("User1 should not exist yet", userService.getUserDetails("User1"));
        UserDetails userDetails = userService.createUser("User1");
        assertEquals("User1 should exist now", userDetails, userService.getUserDetails("User1"));
    }

    @Test
    public void testGetUserTransactions() {
        userService.createUser("User1");
        userService.createUser("User2");
        AccountDetail user1Account = accountService.createAccount("User1");
        AccountDetail user2Account = accountService.createAccount("User2");
        accountService.addMoneyToAccount(user1Account.getId(), 100.0, null);
        accountService.transferMoney(user1Account.getId(), user2Account.getId(), 50.0, null);
        accountService.transferMoney(user1Account.getId(), user2Account.getId(), 20.0, null);
        Map<String, List<TransactionDetails>> user1 = userService.getUserTransactions("User1", 5);
        assertEquals("Transaction should be 2", user1.get(user1Account.getId()).size(), 2);
    }

    @Test
    public void testGetUserAccountInfo() {
        userService.createUser("User1");
        accountService.createAccount("User1");
        accountService.createAccount("User1");
        accountService.createAccount("User1");
        UserAccountsInfo user1 = userService.getUserAccountInfo("User1");
        assertEquals("No of accounts for User1 should be 3", user1.getAccountDetailList().size(), 3);
    }
}