package com.moneytransfer.demo.data.models.user;

import com.moneytransfer.demo.data.models.account.AccountDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserAccountsInfo {
    private String userId;
    private List<AccountDetail> accountDetailList;
}
