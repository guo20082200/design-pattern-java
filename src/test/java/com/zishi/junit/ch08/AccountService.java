package com.zishi.junit.ch08;

public class AccountService {
    private AccountManager accountManager;

    // 设置 AccountManager
    public void setAccountManager(AccountManager manager) {
        this.accountManager = manager;
    }

    /**
     * 转账，
     * @param senderId 发送者
     * @param beneficiaryId 接收者
     * @param amount 转账金额
     */
    public void transfer(String senderId, String beneficiaryId, long amount) {
        Account sender = accountManager.findAccountForUser(senderId);
        Account beneficiary = accountManager.findAccountForUser(beneficiaryId);
        sender.debit(amount);
        beneficiary.credit(amount);
        this.accountManager.updateAccount(sender);
        this.accountManager.updateAccount(beneficiary);
    }
}