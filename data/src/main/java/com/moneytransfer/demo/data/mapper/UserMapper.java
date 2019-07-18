package com.moneytransfer.demo.data.mapper;

import com.moneytransfer.demo.data.models.user.User;
import com.moneytransfer.demo.data.models.user.UserDetails;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserMapper implements ObjectMapper<UserDetails, User> {

    @Override
    public User mapRequest(UserDetails userDetail) {
        if (userDetail == null) {
            return null;
        }
        final User user = new User();
        user.setUpdatedAt(LocalDateTime.now());
        user.setName(userDetail.getName());
        return mapRequest(userDetail, user);
    }

    @Override
    public User mapRequest(final UserDetails userDetails, @Nonnull final User user) {
        if (userDetails == null) {
            return user;
        }
        user.setName(userDetails.getName());
        user.setAccountIds(userDetails.getAccountIds());
        user.setAccountIds(Optional.ofNullable(userDetails.getAccountIds()).orElse(Arrays.asList()));
        return user;
    }

    @Override
    public Optional<UserDetails> mapResponse(User user) {
        if (user == null || !user.isActive())
            return Optional.empty();
        UserDetails userDetails = new UserDetails();
        userDetails.setName(user.getName());
        userDetails.setAccountIds(Optional.ofNullable(user.getAccountIds()).orElse(Arrays.asList()));

        return Optional.of(userDetails);
    }

    @Override
    public List<User> mapRequestList(List<UserDetails> userDetails) {
        if (userDetails == null || userDetails.isEmpty())
            return null;
        List<User> users = new ArrayList<>();
        for (UserDetails userDetail : userDetails) {
            User account = mapRequest(userDetail);
            users.add(account);
        }
        return users;
    }

    @Override
    public List<UserDetails> mapResponseList(List<User> users) {
        if (users == null || users.isEmpty())
            return null;
        List<UserDetails> userDetails = new ArrayList<>();
        for (User user : users) {
            Optional<UserDetails> userDetail = mapResponse(user);
            if (userDetail.isPresent())
                userDetails.add(userDetail.get());
        }
        return userDetails;
    }
}