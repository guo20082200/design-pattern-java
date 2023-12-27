package com.zishi.junit.ch08;

public class Account {
    private String accountId; // 账号id
    private long balance;// 余额

    public Account(String accountId, long initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    // 扣除
    public void debit(long amount) {
        this.balance -= amount;
    }

    // 增加
    public void credit(long amount) {
        this.balance += amount;
    }

    public long getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", balance=" + balance +
                '}';
    }
}