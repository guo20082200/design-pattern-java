package com.zishi.junit.ch08;

public interface AccountManager {
    // 根据用户id查询到账户
    Account findAccountForUser(String userId);

    // 更新账户信息
    void updateAccount(Account account);
}