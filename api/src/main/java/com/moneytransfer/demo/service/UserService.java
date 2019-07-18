package com.moneytransfer.demo.service;

import com.google.inject.Singleton;
import com.moneytransfer.demo.data.dao.UserDao;
import com.moneytransfer.demo.data.mapper.TransactionMapper;
import com.moneytransfer.demo.data.mapper.UserMapper;
import com.moneytransfer.demo.data.models.account.AccountDetail;
import com.moneytransfer.demo.data.models.transaction.Transaction;
import com.moneytransfer.demo.data.models.transaction.TransactionDetails;
import com.moneytransfer.demo.data.models.user.User;
import com.moneytransfer.demo.data.models.user.UserAccountsInfo;
import com.moneytransfer.demo.data.models.user.UserDetails;
import com.moneytransfer.demo.data.models.user.exception.UserDoesNotExistsException;
import com.moneytransfer.demo.data.models.user.exception.UserInActiveException;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@Slf4j
public class UserService implements IUserService {

    private UserDao userDao;
    private UserMapper userMapper;
    private TransactionMapper transactionMapper;
    private IAccountService accountService;

    @Inject
    public UserService(UserDao userDao, UserMapper userMapper, TransactionMapper transactionMapper, IAccountService accountService) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.transactionMapper = transactionMapper;
        this.accountService = accountService;
    }

    @Override
    public UserDetails getUserDetails(String accountHolderName) {
        return userMapper.mapResponse(userDao.get(accountHolderName)).orElseThrow(() -> new UserInActiveException(Response.Status.BAD_REQUEST, accountHolderName));
    }

    @Override
    public UserDetails createUser(String userName) {
        User user = User.builder()
                .name(userName)
                .accountIds(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isActive(true)
                .build();
        userDao.save(user);
        return userMapper.mapResponse(user).orElseThrow(() -> new UserInActiveException(Response.Status.BAD_REQUEST, userName));
    }

    @Override
    public void deleteUser(String userName) {
        userDao.delete(userName);
    }

    @Override
    public Map<String, List<TransactionDetails>> getUserTransactions(String userId, int limit) {
        Map<String, List<TransactionDetails>> result = new HashMap<>();
        Map<String, List<Transaction>> userTransactions = userDao.getUserTransactions(userId, limit);
        for (Map.Entry<String, List<Transaction>> entry : userTransactions.entrySet()) {
            result.put(entry.getKey(), transactionMapper.mapResponseList(entry.getValue()));
        }
        return result;
    }

    @Override
    public UserAccountsInfo getUserAccountInfo(String userId) {
        List<AccountDetail> accountDetails = new ArrayList<>();
        try {
            UserDetails userDetails = getUserDetails(userId);
            userDetails.getAccountIds().forEach(acc -> {
                AccountDetail account = accountService.getAccount(acc);
                accountDetails.add(account);
            });
            return UserAccountsInfo.builder().userId(userId).accountDetailList(accountDetails).build();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        throw new UserDoesNotExistsException(Response.Status.BAD_REQUEST, "User Does Not Exists");
    }
}
